# DHU troubleshooting (USB)

This repo standardizes on **`android-auto-agents/scripts/dhu-start.sh`** (**USB accessory** only).

## Shell mistakes

- **Wrong:** `adb … && pkill -x desktop-head-unit && ./android-auto-agents/scripts/dhu-start.sh` — if `pkill` finds nothing it returns **1** and **`&&` skips** the script.
- **Right:** `… ; pkill -x desktop-head-unit 2>/dev/null || true; ./android-auto-agents/scripts/dhu-start.sh`

## Stale projection / old APK on DHU

1. `adb uninstall luci.sixsixsix.powerampache2.plugin` (if testing plugin) then `installDebug`
2. `adb shell am force-stop luci.sixsixsix.powerampache2.plugin`
3. `adb shell am force-stop com.google.android.projection.gearhead`
4. Quit DHU on the host; rerun `./android-auto-agents/scripts/dhu-start.sh` from **repo root**
5. Open **Android Auto** on the phone; pick the media app again in DHU

## Wrong device / “DHU not my phone”

- **`dhu-start.sh`** now **fails fast** if **more than one** adb device is in `device` state and **`ANDROID_SERIAL`** is unset. Export the serial from `adb devices` (first column) for the handset on USB: `export ANDROID_SERIAL=…` then rerun.

## Google Automotive Link error **-251** / **Framing Error** / **Out of sync**

**Normal testing** here is **SDK DHU + USB accessory (AOA)** — `extras/google/auto/desktop-head-unit` with **`--usb=SERIAL`** (what **`dhu-start.sh`** / **`dhu-start.sh auto`** run). You do **not** need the phone’s **head unit server** for that path.

Logs may show **`Unrecoverable error -251`** while USB mode still **finds** the device. That is usually **not** wrong serial; try:

1. Unplug/replug USB, unlock the phone, open **Android Auto**.
2. **`DHU_LAUNCH_ANDROID_AUTO=1`** (default), **`DHU_PHONE_SETTLE_SEC=5`**, **`DHU_SKIP_FORCE_STOP=1`** if force-stop keeps breaking the session.
3. **`export ANDROID_SERIAL=…`** when multiple adb devices are connected.

**Optional alternate (only if you explicitly want it):** [ADB tunneling](https://developer.android.com/training/cars/testing/dhu) uses **`adb forward`** + **`desktop-head-unit --adb`** and requires **Start head unit server** on the phone — **`./android-auto-agents/scripts/dhu-start.sh adb`**. Set **`DHU_SUGGEST_ADB_TUNNEL=1`** if you want failure messages to mention this; it is **not** the default workflow.

## Log patterns (`auto` mode retries)

- **`UNEXPECTED_RECORD`**, **`Shutting down connection due to auth failure`**, or exit **0** without **`Phone reported protocol`** → flaky USB/SSL; **`./android-auto-agents/scripts/dhu-start.sh auto`** retries.

## Tiling window managers (macOS) — DHU window crashes

If **`desktop-head-unit`** dies with **`Abort trap`**, **`Method cache corrupted`**, or **`_windowDidOrderOffScreen`** / **`SDLView`** in `artifacts/dhu-last.log`, an external WM (yabai, Amethyst, etc.) is likely fighting DHU’s window.

1. **`dhu-start.sh` defaults to not passing `--always_show_window_on_top`** (set **`DHU_ALWAYS_ON_TOP=1`** only if you need the old behavior on stock macOS).
2. Float DHU on a dedicated **Space** or disable tiling for that window if crashes persist.

## IDE / agent-launched shells

Long-running **DHU GUI** may not stay open if the parent automation exits. Contributors often run DHU from **Terminal.app** for stable sessions (see [`AGENTS.md`](../../AGENTS.md)).

## References

- Google [Run the DHU](https://developer.android.com/training/cars/testing/dhu) for **non-USB** setups outside this repo.
