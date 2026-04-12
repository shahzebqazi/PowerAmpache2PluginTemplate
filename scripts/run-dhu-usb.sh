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

# DHU is SDL-based; on Wayland, leaving SDL to pick a driver often causes an instant window close.
# Force X11 through XWayland unless the user already set SDL_VIDEODRIVER.
if [[ -n "${WAYLAND_DISPLAY:-}" && -z "${SDL_VIDEODRIVER:-}" ]]; then
  export SDL_VIDEODRIVER=x11
fi

# Resolve USB serial: never call desktop-head-unit with a bare "--usb" (invalid with multiple adb devices).
_usb_serial=""
if [[ -n "${ANDROID_SERIAL:-}" ]]; then
  _usb_serial="$ANDROID_SERIAL"
elif [[ $# -ge 1 && "$1" != -* ]]; then
  _usb_serial="$1"
  shift
fi

mapfile -t _adb_devs < <(adb devices 2>/dev/null | awk '/\tdevice$/{print $1}' || true)
if [[ -z "$_usb_serial" ]]; then
  if [[ ${#_adb_devs[@]} -eq 1 ]]; then
    _usb_serial="${_adb_devs[0]}"
  else
    echo "error: multiple adb devices (${#_adb_devs[@]}). Set ANDROID_SERIAL or pass the USB phone serial:" >&2
    echo "  ANDROID_SERIAL=<serial> $0" >&2
    echo "  $0 <serial>   # e.g. serial from: adb devices" >&2
    exit 1
  fi
fi

exec "$DHU" --usb="${_usb_serial}" "$@"
