package com.indeed.vw.wrapper.learner;

import com.indeed.vw.wrapper.jni.NativeUtils;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is the only entrance point to create a VWLearner.  It is the responsibility of the user to supply the type they want
 * given the VW command.  If that type is incorrect a {@link java.lang.ClassCastException} is thrown.  Refer to
 * {@link #create(String)} for more information.
 * @author jmorra
 */
public final class VWLearners {
    private static volatile boolean loadedNativeLibrary = false;
    private static final Lock STATIC_LOCK = new ReentrantLock();

    enum VWReturnType {
        Unknown, VWFloatType, VWIntType, VWIntArrayType, VWFloatArrayType
    }

    private VWLearners() {}

    /**
     * This is the only way to construct a VW Predictor.  The goal here is to provide a typesafe way of getting an predictor
     * which will return the correct output type given the command specified.
     * <pre>
     * {@code
     *     VWIntLearner vw = VWFactory.createVWLearner("--cb 4");
     * }
     * </pre>
     *
     * NOTE: It is very important to note that if this method results in a {@link java.lang.ClassCastException} then there
     * WILL be a memory leak as the exception occurs in the calling method not this method due to type erasures.  It is therefore
     * imperative that if the caller of this method is unsure of the type returned that it should specify <code>T</code>
     * as {@link VWLearner} and do the casting on it's side so that closing the method can be guaranteed.
     * @param command The VW initialization command.
     * @param <T> The type of learner expected.  Note that this type implicitly specifies the output type of the learner.
     * @return A VW Learner
     */
    @SuppressWarnings("unchecked")
    public static <T extends VWLearner> T create(final String command) {
        final long nativePointer = initializeVWJni(command);
        final VWReturnType returnType = getReturnType(nativePointer);

        switch (returnType) {
            case VWFloatType: return (T)new VWFloatLearner(nativePointer);
            case VWIntType: return (T)new VWIntLearner(nativePointer);
            case VWFloatArrayType: return (T)new VWFloatArrayLearner(nativePointer);
            case VWIntArrayType: return (T)new VWIntArrayLearner(nativePointer);
            case Unknown:
            default:
                // Doing this will allow for all cases when a C object is made to be closed.
                closeInstance(nativePointer);
                throw new IllegalArgumentException("Unknown VW return type using command: " + command);
        }
    }

    /**
     * @param command The same string that is passed to VW, see
     *                <a href="https://github.com/JohnLangford/vowpal_wabbit/wiki/Command-line-arguments">here
     *                for more information
     * @return The pointer to the native object created on the C side
     */
    private static long initializeVWJni(final String command) {
        long nativePointer;
        try {
            nativePointer = initialize(command);
            loadedNativeLibrary = true;
        }
        catch (final UnsatisfiedLinkError e) {
            loadNativeLibrary();
            nativePointer = initialize(command);
        }
        return nativePointer;
    }

    private static void loadNativeLibrary() {
        // By making use of a static lock here we make sure this code is only executed once globally.
        if (!loadedNativeLibrary) {
            STATIC_LOCK.lock();
            try {
                if (!loadedNativeLibrary) {
                    NativeUtils.loadOSDependentLibrary();
                    loadedNativeLibrary = true;
                }
            }
            catch (final Throwable e) {
                // unable to load library, send users to documentation
                throw new RuntimeException("Unable to load analytics native library. Please refer to http://www.stardog.com/docs/#_native_library_errors", e);
            }
            finally {
                STATIC_LOCK.unlock();
            }
        }
    }

    private static native long initialize(String command);
    private static native VWReturnType getReturnType(long nativePointer);

    // Closing needs to be done here when initialization fails and by VWBase
    static native void closeInstance(long nativePointer);

    static native float exampleNumber(long nativePointer);

    static native float sumLoss(long nativePointer);

    static native void saveModel(long nativePointer, String filename);
}
