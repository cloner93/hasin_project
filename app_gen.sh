#!/usr/bin/env bash

# Get the current directory
DIR="$(pwd)"
# Set the output directory for the app
APP_OUT="$DIR/app/build/outputs"

# app name contain git branch and current date and time
APP_NAME="$(git symbolic-ref --short HEAD)_$(date +%Y_%m_%d_%H_%M)"

# APK and log file names based on the APK
DEBUG_APK_NAME="app_hasin_project_debug_$APP_NAME.apk"
LOGS_FILE_NAME="build_log_$APP_NAME.txt"

# Build the debug variant and log the output
GRADLE_PARAMS="--stacktrace"

# Create the log directory if it doesn't exist
mkdir -p "$DIR/APK/log"

"$DIR/gradlew" :app:clean :app:assembleDebug ${GRADLE_PARAMS} > "$DIR/APK/log/$LOGS_FILE_NAME" 2>&1
BUILD_RESULT=$?

# Copy the debug APK to APK/debug folder if build is successful
if [ $BUILD_RESULT -eq 0 ]; then
    mkdir -p "$DIR/APK/debug"
    cp "$APP_OUT/apk/debug/app-debug.apk" "$DIR/APK/debug/$DEBUG_APK_NAME"
fi

# Exit with the build result
exit $BUILD_RESULT
