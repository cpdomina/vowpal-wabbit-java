#include <vw.h>
#include "jni_base_learner.h"
#include "com_indeed_vw_wrapper_learner_VWFloatLearner.h"

jfloat floatPredictor(example* vec, JNIEnv *env) { return vec->pred.scalar; }

JNIEXPORT jfloat JNICALL Java_com_indeed_vw_wrapper_learner_VWFloatLearner_predict(JNIEnv *env, jobject obj, jstring example_string, jboolean learn, jlong vwPtr)
{ return base_predict<jfloat>(env, obj, example_string, learn, vwPtr, floatPredictor);
}
