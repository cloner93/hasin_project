#!/usr/bin/env bash

 DIR="$( pwd )"
 APP_OUT=$DIR/app/build/outputs

 # Build the debug variant
 GRADLE_PARAMS=" --stacktrace"
 $DIR/gradlew :app:clean :app:assembleDebug ${GRADLE_PARAMS}
 BUILD_RESULT=$?

 # debug apk
 cp $APP_OUT/apk/debug/app-debug.apk $DIR/"apk_debug_$(date +%Y_%m_%d_%H_%M).apk"

 exit $BUILD_RESULT