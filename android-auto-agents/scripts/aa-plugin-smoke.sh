#!/usr/bin/env bash
# Composite smoke: unit tests, uninstall old plugin, :app:installDebug, optional logcat clear, sleep, screencap + logcat snapshot.
# Install path matches adb-plugin-install-debug.sh (clean reinstall, not adb install -r only).
# Does NOT start DHU (long-lived GUI); use aa-plugin-device-cycle.sh or dhu-start.sh separately.
# Requires PA2_PLUGIN_GRADLE_ROOT (or PowerAmpache2PluginTemplate next to android-auto-agents/).
# Usage:
#   ./android-auto-agents/scripts/aa-plugin-smoke.sh
#   SKIP_TESTS=1 SLEEP_SECONDS=5 ./android-auto-agents/scripts/aa-plugin-smoke.sh
# Env: ANDROID_SERIAL, SKIP_TESTS, SKIP_SCREENCAP, SKIP_LOGCAT_SNAPSHOT, CLEAR_LOGCAT, SLEEP_SECONDS (default 2)
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/pa2-plugin-gradle-root.sh
source "$SCRIPT_DIR/lib/pa2-plugin-gradle-root.sh"

SKIP_TESTS="${SKIP_TESTS:-0}"
SKIP_SCREENCAP="${SKIP_SCREENCAP:-0}"
SKIP_LOGCAT_SNAPSHOT="${SKIP_LOGCAT_SNAPSHOT:-0}"
CLEAR_LOGCAT="${CLEAR_LOGCAT:-0}"
SLEEP_SECONDS="${SLEEP_SECONDS:-2}"

pa2_resolve_plugin_gradle_root >/dev/null

ADB=(adb)
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  ADB=(adb -s "$ANDROID_SERIAL")
fi
STATE=$("${ADB[@]}" get-state 2>/dev/null || true)
if [[ "$STATE" != "device" ]]; then
  echo "Need one adb device in 'device' state. Got: ${STATE:-none}" >&2
  exit 1
fi

if [[ "$SKIP_TESTS" != "1" ]]; then
  "$SCRIPT_DIR/gradle-plugin-template.sh" :app:testDebugUnitTest --no-daemon
fi

"$SCRIPT_DIR/adb-plugin-install-debug.sh"

if [[ "$CLEAR_LOGCAT" == "1" ]]; then
  "${ADB[@]}" logcat -c || true
fi

if [[ "$SLEEP_SECONDS" =~ ^[0-9]+$ ]] && [[ "$SLEEP_SECONDS" -gt 0 ]]; then
  sleep "$SLEEP_SECONDS"
fi

if [[ "$SKIP_SCREENCAP" != "1" ]]; then
  "$SCRIPT_DIR/adb-screencap.sh"
fi

if [[ "$SKIP_LOGCAT_SNAPSHOT" != "1" ]]; then
  "$SCRIPT_DIR/logcat-snapshot.sh"
fi

echo "aa-plugin-smoke: ok"
