#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ANDROID_DIR="$SCRIPT_DIR/android"

echo "=== Building Android APK ==="
cd "$ANDROID_DIR"
gradle assembleDebug

APK_PATH="$ANDROID_DIR/app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK_PATH" ]; then
    echo ""
    echo "=== Done! ==="
    echo "APK: $APK_PATH"
else
    echo "Build failed - APK not found"
    exit 1
fi
