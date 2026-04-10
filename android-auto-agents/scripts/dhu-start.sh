#!/usr/bin/env bash
# Reliable Desktop Head Unit launcher for this workspace.
# Primary testing path: SDK binary (extras/google/auto/desktop-head-unit) + USB accessory mode (AOA) + --usb=SERIAL.
# Optional: "adb" mode = adb forward + --adb (head unit server on phone) — alternate transport, not required for normal DHU.
#
# Shell trap: do not chain before this script as `adb … && pkill -x desktop-head-unit`
# — pkill returns 1 when nothing matched and `&&` will skip launching this script.
# Use `; pkill … || true; …/dhu-start.sh` instead.
set -euo pipefail

usage() {
  sed -n '2,8p' "$0" | sed 's/^# \{0,1\}//'
  echo ""
  echo "Usage: $(basename "$0") [usb|auto|adb]"
  echo "  usb   — default: SDK DHU + USB accessory (AOA) + --usb=SERIAL (matches adb device over USB)."
  echo "  auto  — same as usb with retries (DHU_RETRY) on flaky exits."
  echo "  adb   — optional alternate: adb forward + DHU --adb (requires head unit server on phone — not the usual SDK/USB test path)."
  echo ""
  echo "Env: ANDROID_SERIAL (required when multiple adb devices), ANDROID_HOME, DHU_RETRY=5"
  echo "     DHU_LAST_LOG, DHU_CAFFEINATE, DHU_ALWAYS_ON_TOP (1 = on-top window)"
  echo "     DHU_SKIP_FORCE_STOP — 1 = do not force-stop Gearhead before USB DHU"
  echo "     DHU_LAUNCH_ANDROID_AUTO — 1 = launch AA app after reconnect (USB; default 1)"
  echo "     DHU_PHONE_SETTLE_SEC — seconds to wait before starting DHU (default 3)"
  echo "     DHU_ADB_PORT — port for adb mode (default 5277)"
  echo "     DHU_SUGGEST_ADB_TUNNEL — 1 = on failure, also print optional $0 adb hint (default 0)"
}

# android-auto-agents/scripts → workspace root is two levels up
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
SDK=""
if [[ -f "$REPO_ROOT/local.properties" ]]; then
  SDK="$(grep -m1 '^sdk.dir' "$REPO_ROOT/local.properties" | sed 's/^sdk.dir[[:space:]]*=[[:space:]]*//' | tr -d '\r')"
fi
SDK="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$SDK}}"
DHU_DIR="${SDK%/}/extras/google/auto"
DHU="$DHU_DIR/desktop-head-unit"
CFG="$DHU_DIR/config/default.ini"

[[ -x "$DHU" ]] || {
  echo "DHU not found or not executable: $DHU" >&2
  echo "Set sdk.dir in local.properties or ANDROID_HOME to your Android SDK root." >&2
  exit 1
}
[[ -f "$CFG" ]] || CFG=""

DHU_LAST_LOG="${DHU_LAST_LOG:-$REPO_ROOT/artifacts/dhu-last.log}"
mkdir -p "$(dirname "$DHU_LAST_LOG")"

dhu_wrap() {
  if [[ "$(uname -s)" == "Darwin" ]] && [[ "${DHU_CAFFEINATE:-1}" != "0" ]] && command -v caffeinate >/dev/null 2>&1; then
    caffeinate -i -- "$@"
  else
    "$@"
  fi
}

# Run desktop-head-unit with optional -c CFG and optional --always_show_window_on_top.
# Do not use "${arr[@]}" for optional flags: with set -u, an empty array expansion is "unbound".
dhu_invoke() {
  if [[ "${DHU_ALWAYS_ON_TOP:-0}" == "1" ]]; then
    if [[ -n "$CFG" ]]; then
      dhu_wrap "$DHU" -c "$CFG" --always_show_window_on_top "$@"
    else
      dhu_wrap "$DHU" --always_show_window_on_top "$@"
    fi
  else
    if [[ -n "$CFG" ]]; then
      dhu_wrap "$DHU" -c "$CFG" "$@"
    else
      dhu_wrap "$DHU" "$@"
    fi
  fi
}

# Resolve adb serial: fail if multiple devices and ANDROID_SERIAL unset (avoids DHU targeting wrong phone).
dhu_resolve_adb_serial() {
  local devs line
  devs=()
  while IFS= read -r line; do
    [[ -n "$line" ]] && devs+=("$line")
  done < <(adb devices | awk -F'\t' '$2 == "device" { print $1 }')
  if [[ ${#devs[@]} -eq 0 ]]; then
    echo "No adb device in 'device' state. Enable USB debugging and accept the RSA prompt." >&2
    adb devices -l >&2
    return 1
  fi
  if [[ -n "${ANDROID_SERIAL:-}" ]]; then
    local d ok=0
    for d in "${devs[@]}"; do
      if [[ "$d" == "$ANDROID_SERIAL" ]]; then
        ok=1
        break
      fi
    done
    if [[ "$ok" -ne 1 ]]; then
      echo "ANDROID_SERIAL=$ANDROID_SERIAL is not listed as 'device' (wrong device, offline, or unauthorized)." >&2
      adb devices -l >&2
      return 1
    fi
    SERIAL="$ANDROID_SERIAL"
    return 0
  fi
  if [[ ${#devs[@]} -gt 1 ]]; then
    echo "Multiple adb devices are connected; DHU cannot pick the correct phone by guess." >&2
    adb devices -l >&2
    echo "Set ANDROID_SERIAL to your handset's serial (first column). Example:" >&2
    echo "  export ANDROID_SERIAL=${devs[0]}" >&2
    return 1
  fi
  SERIAL="${devs[0]}"
}

SERIAL=""
dhu_resolve_adb_serial || exit 1

RETRIES="${DHU_RETRY:-5}"
MODE="${1:-usb}"
AUTO_USB_LOOP=0
TRANSPORT="usb"

case "$MODE" in
  -h|--help) usage; exit 0 ;;
  auto) MODE=usb; AUTO_USB_LOOP=1 ;;
  usb) ;;
  adb|tunnel) TRANSPORT=adb ;;
  tcp)
    echo "Use '$0 adb' for ADB tunneling (forward + head unit server), not raw TCP." >&2
    exit 1
    ;;
  *) echo "Unknown mode: $MODE" >&2; usage; exit 1 ;;
esac

pkill -x desktop-head-unit 2>/dev/null || true
sleep 1

DHU_ADB_PORT="${DHU_ADB_PORT:-5277}"

if [[ "$TRANSPORT" == "adb" ]]; then
  {
    echo ""
    echo "======== $(date -Iseconds 2>/dev/null || date)  DHU adb tunnel session ========"
  } >>"$DHU_LAST_LOG"
  echo "ADB tunnel mode — serial=$SERIAL, port=$DHU_ADB_PORT"
  echo "On the phone: enable Android Auto developer mode, then overflow menu → Start head unit server."
  echo "Keep the screen unlocked. Then DHU connects over adb forward (not USB accessory)."
  adb -s "$SERIAL" forward "tcp:$DHU_ADB_PORT" "tcp:$DHU_ADB_PORT"
  echo "adb forward tcp:$DHU_ADB_PORT (log: $DHU_LAST_LOG)"
  cd "$DHU_DIR"
  set +e
  dhu_invoke --adb="$DHU_ADB_PORT" 2>&1 | tee -a "$DHU_LAST_LOG"
  code=${PIPESTATUS[0]}
  exit "$code"
fi

# --- USB (AOA) transport ---
if [[ "${DHU_SKIP_FORCE_STOP:-0}" != "1" ]]; then
  echo "Force-stopping Android Auto on device (clean reconnect)…"
  adb -s "$SERIAL" shell am force-stop com.google.android.projection.gearhead 2>/dev/null || true
  sleep 1
else
  echo "Skipping force-stop (DHU_SKIP_FORCE_STOP=1)."
fi

if [[ "${DHU_LAUNCH_ANDROID_AUTO:-1}" == "1" ]]; then
  echo "Bringing Android Auto to foreground on device…"
  adb -s "$SERIAL" shell monkey -p com.google.android.projection.gearhead -c android.intent.category.LAUNCHER 1 2>/dev/null || true
fi

settle="${DHU_PHONE_SETTLE_SEC:-3}"
if [[ "$settle" =~ ^[0-9]+$ ]] && [[ "$settle" -gt 0 ]]; then
  echo "Waiting ${settle}s for phone / USB stack to settle before DHU…"
  sleep "$settle"
fi

echo "DHU log (append): $DHU_LAST_LOG"

dhu_log_indicates_failure() {
  [[ -f "$1" ]] && grep -E -q \
    'Failed to read from transport|Out of sync with phone|Google Automotive Link error|Unrecoverable error|Failed to start Google Automotive Link|UNEXPECTED_RECORD|Shutting down connection due to auth failure|BAD_SIGNATURE|OPENSSL_internal:BAD_SIGNATURE|Segmentation fault|Abort trap|SIG(SEGV|ABRT)|Method cache corrupted|_windowDidOrderOffScreen' \
    "$1"
}

# DHU often exits with code 0 even when the link failed (TLS stuck, -251, or phone-side Android Auto error).
# Treat as incomplete unless we see a finished handshake like a healthy session (see artifacts/dhu-last.log).
dhu_log_indicates_incomplete_usb_session() {
  [[ -f "$1" ]] || return 1
  grep -q 'Attached!' "$1" || return 1
  if ! grep -E -q 'Phone reported protocol' "$1"; then
    return 0
  fi
  if grep -E -q 'Verify returned: ok|SSL negotiation finished successfully' "$1"; then
    return 1
  fi
  return 0
}

if [[ "$MODE" == "usb" ]]; then
  echo "USB accessory mode — SDK DHU with --usb=$SERIAL (same device as adb over this USB cable)"
  echo "Recovery: unplug/replug USB, unlock phone, open Android Auto; try DHU_SKIP_FORCE_STOP=1 or DHU_PHONE_SETTLE_SEC=5"
  sleep 2
  if [[ "$AUTO_USB_LOOP" -eq 1 ]]; then
    LOG=$(mktemp)
    trap 'rm -f "$LOG"' EXIT
    {
      echo ""
      echo "======== $(date -Iseconds 2>/dev/null || date)  DHU auto session ========"
    } >>"$DHU_LAST_LOG"
    attempt=1
    while (( attempt <= RETRIES )); do
      echo ""
      echo "— USB attempt $attempt / $RETRIES —"
      : >"$LOG"
      cd "$DHU_DIR"
      set +e
      dhu_invoke --usb="$SERIAL" 2>&1 | tee "$LOG" | tee -a "$DHU_LAST_LOG"
      code=${PIPESTATUS[0]}
      set -e
      skip_generic_exit_msg=0
      if [[ "$code" -eq 0 ]] && ! dhu_log_indicates_failure "$LOG"; then
        if dhu_log_indicates_incomplete_usb_session "$LOG"; then
          echo "" >&2
          echo "DHU exited with code 0 but the USB session did not finish TLS (missing Verify/SSL success lines). The phone may show an Android Auto error. Retrying if attempts remain." >&2
          skip_generic_exit_msg=1
        else
          echo "DHU exited normally (TLS completed)."
          exit 0
        fi
      fi
      if dhu_log_indicates_failure "$LOG"; then
        echo "" >&2
        echo "DHU lost the USB link (see [E]: lines above). Exit code was $code — often still 0." >&2
        echo "Recovery (USB/SDK path): replug USB, unlock phone, reopen Android Auto; DHU_SKIP_FORCE_STOP=1 $0 auto" >&2
        if [[ "${DHU_SUGGEST_ADB_TUNNEL:-0}" == "1" ]]; then
          echo "Optional alternate transport (head unit server on phone): $0 adb" >&2
        fi
      elif [[ "$skip_generic_exit_msg" -eq 0 ]]; then
        echo "DHU exited with code $code." >&2
        if [[ "$code" -gt 128 ]] || [[ "$code" -eq 139 ]] || [[ "$code" -eq 134 ]] || [[ "$code" -eq 11 ]]; then
          echo "That code often means the DHU process crashed (signal / abort). Full output: $DHU_LAST_LOG" >&2
          echo "On macOS: log show --predicate 'process == \"desktop-head-unit\"' --last 10m" >&2
        fi
      fi
      if [[ "$attempt" -lt "$RETRIES" ]]; then
        echo "Retrying USB in 6s…"
        sleep 6
      fi
      attempt=$((attempt + 1))
    done
    echo "" >&2
    echo "USB mode failed after $RETRIES attempts." >&2
    echo "Try: unplug/replug USB, unlock phone, export ANDROID_SERIAL if multiple devices, then: $0 auto" >&2
    if [[ "${DHU_SUGGEST_ADB_TUNNEL:-0}" == "1" ]]; then
      echo "Optional: $0 adb (requires head unit server — set DHU_SUGGEST_ADB_TUNNEL=0 to hide)." >&2
    fi
    exit 1
  fi
  cd "$DHU_DIR"
  {
    echo ""
    echo "======== $(date -Iseconds 2>/dev/null || date)  DHU usb session ========"
  } >>"$DHU_LAST_LOG"
  set +e
  dhu_invoke --usb="$SERIAL" 2>&1 | tee -a "$DHU_LAST_LOG"
  code=${PIPESTATUS[0]}
  exit "$code"
fi

echo "Internal error: unexpected mode" >&2
exit 2
