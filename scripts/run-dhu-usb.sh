#!/usr/bin/env bash
# Run Android Auto Desktop Head Unit 2.x over USB (Android Open Accessory / AOA).
# This is Google's recommended path for DHU 2.x and does NOT use:
#   - adb forward tcp:5277 tcp:5277
#   - Android Auto → "Start head unit server" (that flow is for ADB tunneling only)
#
# Prerequisites: phone USB-connected, screen unlocked, USB debugging on.
# If DHU cannot attach to USB, try: adb kill-server   (then run this script; restart adb later if needed)
#
# Ref: https://developer.android.com/training/cars/testing/dhu#connection-aoap

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

# libc++ must be on the loader path (pacman: libc++)
if ! ldd "$DHU" 2>/dev/null | grep -q 'libc++.so.1 => /usr/lib'; then
  if [[ -n "${LD_LIBRARY_PATH:-}" ]]; then
    echo "warning: LD_LIBRARY_PATH is set; if DHU fails to start, run: unset LD_LIBRARY_PATH" >&2
  fi
fi

# Single authorized adb device → pass explicit serial (helps when multiple USB gadgets exist)
mapfile -t _adb_devs < <(adb devices 2>/dev/null | awk '/\tdevice$/{print $1}' || true)
if [[ ${#_adb_devs[@]} -eq 1 ]]; then
  exec "$DHU" --usb="${_adb_devs[0]}" "$@"
fi

exec "$DHU" --usb "$@"
