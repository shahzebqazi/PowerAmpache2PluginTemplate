#!/usr/bin/env bash
# List adb devices (use ANDROID_SERIAL when multiple USB devices).
set -euo pipefail
exec adb devices -l
