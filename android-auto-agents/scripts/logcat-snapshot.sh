#!/usr/bin/env bash
# Dump a bounded logcat snapshot (non-streaming). MCP: logcat_car_media_snapshot.
# Writes to artifacts/logcat/ and prints last N lines to stdout.
# Usage:
#   ./android-auto-agents/scripts/logcat-snapshot.sh
#   MAX_LINES=800 TAGS="MediaLibrary:V Foo:V" ./android-auto-agents/scripts/logcat-snapshot.sh
# Env: ANDROID_SERIAL, MAX_LINES (default 500), TAGS (space-separated -s args), OUT_FILE (optional full path)
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
ADB=(adb)
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  ADB=(adb -s "$ANDROID_SERIAL")
fi

MAX_LINES="${MAX_LINES:-500}"
TAGS="${TAGS:-MediaLibrary:V MediaSession:V AndroidAuto:V ExoPlayer:V}"
read -r -a TAG_ARRAY <<<"$TAGS"

mkdir -p "$REPO_ROOT/artifacts/logcat"
OUT="${OUT_FILE:-$REPO_ROOT/artifacts/logcat/logcat-$(date +%Y%m%d-%H%M%S).txt}"

STATE=$("${ADB[@]}" get-state 2>/dev/null || true)
if [[ "$STATE" != "device" ]]; then
  echo "Need one adb device in 'device' state. Got: ${STATE:-none}" >&2
  exit 1
fi

"${ADB[@]}" logcat -d -s "${TAG_ARRAY[@]}" >"$OUT" || true
# Print tail for agents / LLM context
tail -n "$MAX_LINES" "$OUT"
