#!/usr/bin/env bash
# Autonomous verify → install → force-stop Android Auto + plugin → replug instructions → Desktop Head Unit.
# For agents: one entry point after plugin or media-session changes (USB DHU path).
#
# Run from the consumer repository root (parent of android-auto-agents/).
#
# Usage:
#   ./android-auto-agents/scripts/aa-plugin-dhu-refresh.sh
#   SKIP_TESTS=1 VERIFY_MOCKUP=1 ./android-auto-agents/scripts/aa-plugin-dhu-refresh.sh
#   DHU_FOREGROUND=1 ./android-auto-agents/scripts/aa-plugin-dhu-refresh.sh   # blocks; DHU in this terminal
#
# Env:
#   PA2_PLUGIN_GRADLE_ROOT — optional if PowerAmpache2PluginTemplate/ exists next to android-auto-agents/
#   ANDROID_SERIAL — if multiple adb devices
#   SKIP_TESTS=1 — skip :app:testDebugUnitTest
#   VERIFY_MOCKUP=1 — run mockup-verify.sh (npm build + check)
#   SKIP_INSTALL=1 — skip uninstall/installDebug (only verify + force-stop + DHU)
#   SKIP_DHU=1 — do not start desktop-head-unit
#   DHU_FOREGROUND=1 — exec dhu-start (blocks); default is background + log file
#   DHU_MODE — default auto; passed to dhu-start.sh (usb | auto | adb)
#   DHU_NOHUP_LOG — path for background DHU log (default REPO_ROOT/artifacts/dhu-nohup.log)
#   DHU_LAST_LOG — read for hints (default REPO_ROOT/artifacts/dhu-last.log); same as dhu-start.sh
#   REPLUG_BANNER=0 — suppress human USB/replug instructions
#   SKIP_FORCE_STOP_GEARHEAD=1 — do not force-stop Android Auto on device
#   SKIP_FORCE_STOP_PLUGIN=1 — do not force-stop plugin APK
#   DHU_SKIP_FORCE_STOP — forwarded to dhu-start.sh (see dhu-start.sh; usually unset)
#   POST_STOP_SLEEP_SEC — seconds after force-stop before DHU (default 2)
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
# shellcheck source=lib/pa2-plugin-gradle-root.sh
source "$SCRIPT_DIR/lib/pa2-plugin-gradle-root.sh"

ADB=(adb)
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  ADB=(adb -s "$ANDROID_SERIAL")
fi

STATE=$("${ADB[@]}" get-state 2>/dev/null || true)
if [[ "$STATE" != "device" ]]; then
  echo "aa-plugin-dhu-refresh: need one adb device in 'device' state. Got: ${STATE:-none}" >&2
  "${ADB[@]}" devices -l >&2 || true
  exit 1
fi

echo ""
echo "=== android-auto-agents: aa-plugin-dhu-refresh ==="
echo "Repo root: $REPO_ROOT"
pa2_resolve_plugin_gradle_root >/dev/null
echo "Plugin Gradle root: $(pa2_resolve_plugin_gradle_root)"
echo ""

if [[ "${VERIFY_MOCKUP:-0}" == "1" ]]; then
  echo "— mockup verify (npm run build + check) —"
  "$SCRIPT_DIR/mockup-verify.sh"
  echo ""
fi

if [[ "${SKIP_TESTS:-0}" != "1" ]]; then
  echo "— plugin unit tests —"
  "$SCRIPT_DIR/gradle-plugin-template.sh" :app:testDebugUnitTest --no-daemon
  echo ""
fi

if [[ "${SKIP_INSTALL:-0}" != "1" ]]; then
  echo "— uninstall + installDebug (plugin) —"
  "$SCRIPT_DIR/adb-plugin-install-debug.sh"
  echo ""
  echo "— installed package version —"
  "${ADB[@]}" shell dumpsys package luci.sixsixsix.powerampache2.plugin 2>/dev/null \
    | grep -E 'versionCode|versionName' | head -4 || echo "(dumpsys unavailable)"
  echo ""
else
  echo "— SKIP_INSTALL=1: not installing APK —"
  echo ""
fi

if [[ "${SKIP_FORCE_STOP_GEARHEAD:-0}" != "1" ]]; then
  echo "— force-stop Android Auto (com.google.android.projection.gearhead) —"
  "${ADB[@]}" shell am force-stop com.google.android.projection.gearhead 2>/dev/null || true
else
  echo "— SKIP_FORCE_STOP_GEARHEAD: leaving Android Auto process as-is —"
fi

if [[ "${SKIP_FORCE_STOP_PLUGIN:-0}" != "1" ]]; then
  echo "— force-stop plugin (luci.sixsixsix.powerampache2.plugin) —"
  "${ADB[@]}" shell am force-stop luci.sixsixsix.powerampache2.plugin 2>/dev/null || true
else
  echo "— SKIP_FORCE_STOP_PLUGIN: leaving plugin process as-is —"
fi

POST_SLEEP="${POST_STOP_SLEEP_SEC:-2}"
if [[ "$POST_SLEEP" =~ ^[0-9]+$ ]] && [[ "$POST_SLEEP" -gt 0 ]]; then
  echo "— waiting ${POST_SLEEP}s after force-stop —"
  sleep "$POST_SLEEP"
fi

DHU_LAST_LOG="${DHU_LAST_LOG:-$REPO_ROOT/artifacts/dhu-last.log}"
if [[ -f "$DHU_LAST_LOG" ]] && grep -qE 'error -251|Out of sync with phone|Google Automotive Link error|Unrecoverable error' "$DHU_LAST_LOG" 2>/dev/null; then
  echo ""
  echo "Hint: previous DHU session in $DHU_LAST_LOG reported a USB/link error."
  echo "      Replug USB and unlock the phone before relying on the next DHU attach."
  echo ""
fi

if [[ "${REPLUG_BANNER:-1}" != "0" ]]; then
  cat <<'BANNER'

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Human steps (recommended for reliable USB Desktop Head Unit)
  1. Unlock the phone and dismiss any USB permission dialogs.
  2. Unplug and replug the USB cable (or switch USB mode to file transfer / accessory).
  3. Wait for adb to show device (adb devices).
  4. Optional: open Android Auto on the phone once; DHU may also launch it.
  5. If DHU still shows -251 / stream broken: try DHU_SKIP_FORCE_STOP=1 on dhu-start,
     or increase DHU_PHONE_SETTLE_SEC (see android-auto-agents/scripts/dhu-start.sh).
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BANNER
fi

if [[ "${SKIP_DHU:-0}" == "1" ]]; then
  echo "SKIP_DHU=1: not starting Desktop Head Unit."
  echo "aa-plugin-dhu-refresh: ok"
  exit 0
fi

mkdir -p "$REPO_ROOT/artifacts"
DHU_NOHUP_LOG="${DHU_NOHUP_LOG:-$REPO_ROOT/artifacts/dhu-nohup.log}"
DHU_MODE="${DHU_MODE:-auto}"

echo "— stopping any existing desktop-head-unit —"
pkill -x desktop-head-unit 2>/dev/null || true
sleep 1

if [[ "${DHU_FOREGROUND:-0}" == "1" ]]; then
  echo "— starting DHU in foreground (${DHU_MODE}) — log also: $DHU_LAST_LOG"
  cd "$REPO_ROOT"
  exec "$SCRIPT_DIR/dhu-start.sh" "$DHU_MODE"
fi

echo "— starting DHU in background (${DHU_MODE}) —"
echo "    append log: $DHU_NOHUP_LOG"
cd "$REPO_ROOT"
nohup "$SCRIPT_DIR/dhu-start.sh" "$DHU_MODE" >>"$DHU_NOHUP_LOG" 2>&1 &
echo "DHU pid=$!"
echo ""
echo "aa-plugin-dhu-refresh: ok (DHU running in background; tail -f $DHU_NOHUP_LOG)"
