//
// Created by hp on 18-4-20.
//
#include "com_example_hp_verifyimagination_verify_layout_funtion_NDKTestActivity.h"

JNIEXPORT jstring JNICALL Java_com_example_hp_verifyimagination_verify_layout_funtion_NDKTestActivity_sayHello
(JNIEnv *env, jobject jobject1){
    return env->NewStringUTF("Hello NDK");
}
