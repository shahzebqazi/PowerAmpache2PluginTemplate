---
name: dhu-usb-session
description: Start SDK Desktop Head Unit (USB accessory / --usb); optional adb tunnel; avoid shell pitfalls.
---

# DHU session (Power Ampache 2 workspace)

## Start

From **repository root** (parent of `android-auto-agents/`):

```bash
./android-auto-agents/scripts/dhu-start.sh
```

Retries (SSL / handshake flakes):

```bash
./android-auto-agents/scripts/dhu-start.sh auto
```

That uses the **Android SDK** `desktop-head-unit` under **`extras/google/auto/`** with **USB accessory mode** and **`--usb=SERIAL`** — the standard **DHU + adb device** workflow; **no head unit server** on the phone is required.

**Optional:** `dhu-start.sh adb` is a different transport (forward + head unit server). Only if you deliberately want that alternate path.

## Do not

- Chain **`adb … && pkill -x desktop-head-unit`** before the script — **`pkill` exit 1** skips **`&&`** chain.
- Run **`dhu-start.sh`** from inside a **nested Gradle project** subdirectory with a wrong relative path — always invoke **`./android-auto-agents/scripts/dhu-start.sh`** from the **consumer workspace root**.
- Omit **`ANDROID_SERIAL`** when **more than one** device is in `device` state — the script will exit with instructions.

## After code changes on device

```bash
adb uninstall luci.sixsixsix.powerampache2.plugin   # if signature mismatch
export PA2_PLUGIN_GRADLE_ROOT=/path/to/plugin-gradle-root
./android-auto-agents/scripts/adb-plugin-install-debug.sh
```

Then force-stop plugin + Android Auto if DHU still looks stale (see `workflows/dhu-troubleshooting.md`).

## Read more

- `android-auto-agents/workflows/dhu-troubleshooting.md`
- `AGENTS.md` — Desktop Head Unit section
