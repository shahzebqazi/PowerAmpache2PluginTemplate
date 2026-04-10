#!/usr/bin/env bash
# Capture the phone screen to a PNG (adb screencap). MCP: adb_screencap_phone.
# Default: artifacts/screenshots/phone-<timestamp>.png under repo root.
# Usage:
#   ./android-auto-agents/scripts/adb-screencap.sh
#   ./android-auto-agents/scripts/adb-screencap.sh /path/to/out.png
# Env: ANDROID_SERIAL when multiple devices
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
ADB=(adb)
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  ADB=(adb -s "$ANDROID_SERIAL")
fi

OUT="${1:-}"
if [[ -z "$OUT" ]]; then
  mkdir -p "$REPO_ROOT/artifacts/screenshots"
  OUT="$REPO_ROOT/artifacts/screenshots/phone-$(date +%Y%m%d-%H%M%S).png"
else
  mkdir -p "$(dirname "$OUT")"
fi

STATE=$("${ADB[@]}" get-state 2>/dev/null || true)
if [[ "$STATE" != "device" ]]; then
  echo "Need one adb device in 'device' state (or set ANDROID_SERIAL). Got: ${STATE:-none}" >&2
  exit 1
fi

"${ADB[@]}" exec-out screencap -p >"$OUT"
echo "$OUT"
