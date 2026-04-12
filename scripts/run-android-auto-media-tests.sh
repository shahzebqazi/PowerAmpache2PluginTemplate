#!/usr/bin/env bash
# Automated Android Auto browse regression: Media3 MediaBrowser against Pa2MediaLibraryService
# (same API surface Android Auto uses). Requires a device or emulator with the app installed.
#
# Usage:
#   ./scripts/run-android-auto-media-tests.sh
#   ANDROID_SERIAL=<id> ./scripts/run-android-auto-media-tests.sh   # if multiple adb devices
#
# Installs debug + androidTest APKs, then runs Pa2MediaLibraryInstrumentedTest only.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

if ! adb devices | grep -q 'device$'; then
  echo "error: no adb device in 'device' state; connect USB or start an emulator" >&2
  exit 1
fi

./gradlew :app:assembleDebug :app:assembleDebugAndroidTest --no-daemon

SERIAL="${ANDROID_SERIAL:-}"
if [[ -n "$SERIAL" ]]; then
  adb -s "$SERIAL" install -r app/build/outputs/apk/debug/app-debug.apk
  adb -s "$SERIAL" install -r app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
  adb -s "$SERIAL" shell am instrument -w \
    -e class luci.sixsixsix.powerampache2.plugin.auto.Pa2MediaLibraryInstrumentedTest \
    luci.sixsixsix.powerampache2.plugin.test/androidx.test.runner.AndroidJUnitRunner
else
  ./gradlew :app:connectedDebugAndroidTest \
    --no-daemon \
    -Pandroid.testInstrumentationRunnerArguments.class=luci.sixsixsix.powerampache2.plugin.auto.Pa2MediaLibraryInstrumentedTest
fi
