#!/usr/bin/env bash
# Device cycle: unit tests (optional), uninstall old plugin, installDebug, optional phone screencap + logcat snapshot,
# then optionally start Desktop Head Unit in the background for UI inspection.
# For force-stop Android Auto + plugin, replug banner, and DHU in one flow, prefer aa-plugin-dhu-refresh.sh.
# Run from the consumer repository root (parent of android-auto-agents/).
#
# Usage:
#   ./android-auto-agents/scripts/aa-plugin-device-cycle.sh
#   SKIP_TESTS=1 START_DHU=0 ./android-auto-agents/scripts/aa-plugin-device-cycle.sh
# Env: PA2_PLUGIN_GRADLE_ROOT (optional if PowerAmpache2PluginTemplate/ exists), ANDROID_SERIAL,
#      SKIP_TESTS, SKIP_SCREENCAP, SKIP_LOGCAT_SNAPSHOT, CLEAR_LOGCAT, SLEEP_SECONDS,
#      START_DHU (default 1), SKIP_DHU=1 to disable DHU, DHU_MODE (default auto = SDK DHU + USB --usb retries)
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"

START_DHU="${START_DHU:-1}"
if [[ "${SKIP_DHU:-0}" == "1" ]]; then
  START_DHU=0
fi

"$SCRIPT_DIR/aa-plugin-smoke.sh"

if [[ "$START_DHU" == "1" ]]; then
  mkdir -p "$REPO_ROOT/artifacts"
  DHU_LOG="${DHU_NOHUP_LOG:-$REPO_ROOT/artifacts/dhu-nohup.log}"
  echo "Starting DHU in background (${DHU_MODE:-auto}) — log: $DHU_LOG"
  cd "$REPO_ROOT"
  nohup "$SCRIPT_DIR/dhu-start.sh" "${DHU_MODE:-auto}" >>"$DHU_LOG" 2>&1 &
  echo "DHU pid=$!"
fi

echo "aa-plugin-device-cycle: ok"
