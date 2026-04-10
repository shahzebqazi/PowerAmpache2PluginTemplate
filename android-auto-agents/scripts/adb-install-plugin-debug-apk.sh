#!/usr/bin/env bash
# adb install -r the assembled plugin debug APK (no Gradle). MCP: adb_install_plugin_debug_apk.
# Default APK: $PA2_PLUGIN_GRADLE_ROOT/app/build/outputs/apk/debug/app-debug.apk
# Usage:
#   ./android-auto-agents/scripts/adb-install-plugin-debug-apk.sh
#   ./android-auto-agents/scripts/adb-install-plugin-debug-apk.sh /custom/path/app-debug.apk
# Env: ANDROID_SERIAL, PA2_PLUGIN_GRADLE_ROOT (required when using default APK path)
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/pa2-plugin-gradle-root.sh
source "$SCRIPT_DIR/lib/pa2-plugin-gradle-root.sh"
ADB=(adb)
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  ADB=(adb -s "$ANDROID_SERIAL")
fi

if [[ -n "${1:-}" ]]; then
  APK="$1"
else
  PLUGIN_ROOT="$(pa2_resolve_plugin_gradle_root)"
  APK="$PLUGIN_ROOT/app/build/outputs/apk/debug/app-debug.apk"
fi

if [[ ! -f "$APK" ]]; then
  echo "APK not found: $APK" >&2
  echo "Run: PA2_PLUGIN_GRADLE_ROOT=... ./android-auto-agents/scripts/gradle-plugin-template.sh :app:assembleDebug" >&2
  exit 1
fi

STATE=$("${ADB[@]}" get-state 2>/dev/null || true)
if [[ "$STATE" != "device" ]]; then
  echo "Need one adb device in 'device' state. Got: ${STATE:-none}" >&2
  exit 1
fi

exec "${ADB[@]}" install -r "$APK"
