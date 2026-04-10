#!/usr/bin/env bash
# Optional: capture the macOS screen or a region to PNG (for DHU window documentation).
# Not reliable for unattended CI (window focus, multi-monitor). Prefer adb-screencap.sh for phone UI.
# Requires: macOS. Uses screencapture.
# Usage:
#   ./android-auto-agents/scripts/dhu-screencapture-macos.sh
#   ./android-auto-agents/scripts/dhu-screencapture-macos.sh -R 0,0,800,600
# Env: DHU_CAPTURE_OUT (optional output path)
set -euo pipefail
if [[ "$(uname -s)" != "Darwin" ]]; then
  echo "This script is for macOS only. On Linux use your compositor's screenshot tool." >&2
  exit 1
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
mkdir -p "$REPO_ROOT/artifacts/screenshots"

OUT="${DHU_CAPTURE_OUT:-$REPO_ROOT/artifacts/screenshots/dhu-macos-$(date +%Y%m%d-%H%M%S).png}"

# Pass -R x,y,w,h for region, or omit for interactive selection with -i (user-driven)
if [[ $# -gt 0 ]]; then
  screencapture -x -t png "$@" "$OUT"
else
  # Full main display non-interactive
  screencapture -x -t png "$OUT"
fi

echo "$OUT"
