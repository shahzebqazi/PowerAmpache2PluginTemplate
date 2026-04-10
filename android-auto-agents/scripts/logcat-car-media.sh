#!/usr/bin/env bash
# Focused logcat for Media3 / session / common car-media tags (debugging DHU + Auto).
# Usage: ./android-auto-agents/scripts/logcat-car-media.sh
# Env: ANDROID_SERIAL
# Note: OEMs use different tags; for broad capture use: adb logcat -v time | grep -E 'Media|AndroidAuto'
set -euo pipefail
ADB=(adb)
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  ADB=(adb -s "$ANDROID_SERIAL")
fi
# Suppress noisy default streams; raise verbosity on selected tags.
exec "${ADB[@]}" logcat -v time \
  '*:S' \
  'MediaSession:V' \
  'MediaLibraryService:V' \
  'MediaBrowserService:V' \
  'MediaLibrarySessionImpl:V' \
  'ExoPlayerImpl:V' \
  'AndroidRuntime:E'
