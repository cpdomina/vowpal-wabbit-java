/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_indeed_vw_wrapper_learner_VWLearners */

#ifndef _Included_com_indeed_vw_wrapper_learner_VWLearners
#define _Included_com_indeed_vw_wrapper_learner_VWLearners
#ifdef __cplusplus
extern "C"
{
#endif
/*
 * Class:     com_indeed_vw_wrapper_learner_VWLearners
 * Method:    initialize
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_indeed_vw_wrapper_learner_VWLearners_initialize
(JNIEnv *, jobject, jstring);

/*
 * Class:     com_indeed_vw_wrapper_learner_VWLearners
 * Method:    closeInstance
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_indeed_vw_wrapper_learner_VWLearners_closeInstance
(JNIEnv *, jobject, jlong);

/*
 * Class:     com_indeed_vw_wrapper_learner_VWLearners
 * Method:    getReturnType
 * Signature: (J)V
 */
JNIEXPORT jobject JNICALL Java_com_indeed_vw_wrapper_learner_VWLearners_getReturnType
(JNIEnv *, jobject, jlong);

/*
 * Class:     vowpalWabbit_learner_VWLearners
 * Method:    exampleNumber
 * Signature: (J)J
 */
JNIEXPORT jfloat JNICALL Java_com_indeed_vw_wrapper_learner_VWLearners_exampleNumber
(JNIEnv *, jclass, jlong);

/*
 * Class:     vowpalWabbit_learner_VWLearners
 * Method:    sumLoss
 * Signature: (J)J
 */
JNIEXPORT jfloat JNICALL Java_com_indeed_vw_wrapper_learner_VWLearners_sumLoss
(JNIEnv *, jclass, jlong);

/*
 * Class:     vowpalWabbit_learner_VWLearners
 * Method:    saveModel
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_indeed_vw_wrapper_learner_VWLearners_saveModel
(JNIEnv *, jclass, jlong, jstring);

#ifdef __cplusplus
}
#endif
#endif
