#!/usr/bin/env bash
# Android Auto DHU via ADB tunnel (older but reliable when USB AOAP or SDL/Wayland misbehaves).
#
# On the phone FIRST:
#   Android Auto → tap version / About repeatedly until Developer mode appears
#   → ⋮ menu → Developer settings → enable "Start head unit server"
#   → Start the head unit server from the notification or the same menu (wording varies by AA version)
#
# Then connect USB (or use wireless debugging with adb connect), and:
#   adb forward tcp:5277 tcp:5277
#   ./scripts/run-dhu-adb-tunnel.sh
#
# Ref: https://developer.android.com/training/cars/testing/dhu

set -euo pipefail

if [[ -z "${ANDROID_HOME:-}" ]]; then
  echo "error: ANDROID_HOME is not set (e.g. export ANDROID_HOME=\$HOME/Android/Sdk)" >&2
  exit 1
fi

DHU="$ANDROID_HOME/extras/google/auto/desktop-head-unit"
if [[ ! -x "$DHU" ]]; then
  echo "error: DHU not found at $DHU — install with: sdkmanager \"extras;google;auto\"" >&2
  exit 1
fi

# Same SDL hint as run-dhu-usb.sh (DHU uses SDL + X11; Wayland often needs XWayland explicitly).
if [[ -n "${WAYLAND_DISPLAY:-}" && -z "${SDL_VIDEODRIVER:-}" ]]; then
  export SDL_VIDEODRIVER=x11
fi

if ! adb devices | grep -q 'device$'; then
  echo "error: no authorized adb device; connect phone and accept USB debugging" >&2
  exit 1
fi

echo "Forwarding tcp:5277 (DHU) → phone tcp:5277..."
adb forward tcp:5277 tcp:5277

echo "Starting DHU (ADB transport). If the window closes immediately, ensure Head Unit Server is running on the phone."
exec "$DHU" "$@"
