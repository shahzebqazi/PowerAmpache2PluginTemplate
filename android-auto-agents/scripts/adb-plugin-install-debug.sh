#!/usr/bin/env bash
# Uninstall plugin package (ignore errors) then :app:installDebug in PA2_PLUGIN_GRADLE_ROOT.
# Matches MCP: adb_plugin_reinstall_debug.
# Usage:
#   ./android-auto-agents/scripts/adb-plugin-install-debug.sh
#   ./android-auto-agents/scripts/adb-plugin-install-debug.sh --skip-uninstall
# Env: ANDROID_SERIAL when multiple devices; PA2_PLUGIN_GRADLE_ROOT (required)
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck source=lib/pa2-plugin-gradle-root.sh
source "$SCRIPT_DIR/lib/pa2-plugin-gradle-root.sh"
ADB=(adb)
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  ADB=(adb -s "$ANDROID_SERIAL")
fi

SKIP_UNINSTALL=0
if [[ "${1:-}" == "--skip-uninstall" ]]; then
  SKIP_UNINSTALL=1
fi

STATE=$("${ADB[@]}" get-state 2>/dev/null || true)
if [[ "$STATE" != "device" ]]; then
  echo "Need one adb device in 'device' state. Got: ${STATE:-none}" >&2
  exit 1
fi

if [[ "$SKIP_UNINSTALL" -eq 0 ]]; then
  echo "Removing previous install (if any): luci.sixsixsix.powerampache2.plugin"
  "${ADB[@]}" uninstall luci.sixsixsix.powerampache2.plugin 2>/dev/null || true
fi

PLUGIN_ROOT="$(pa2_resolve_plugin_gradle_root)"
cd "$PLUGIN_ROOT"
exec ./gradlew :app:installDebug --no-daemon
