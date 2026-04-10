# Android Auto harness scripts

Runnable helpers for **design** (mockup), **development** (Gradle), **debugging** (logcat, adb), and **testing** (unit + connected). Prefer these paths from the **consumer repository root** (parent of `android-auto-agents/`) so `local.properties` and paths resolve correctly.

## Nested plugin Gradle root

Export **`PA2_PLUGIN_GRADLE_ROOT`** before any script that runs **`./gradlew`** in the PA2 plugin project (optional if this repo contains **`PowerAmpache2PluginTemplate/`** next to `android-auto-agents/` — scripts auto-detect):

```bash
export PA2_PLUGIN_GRADLE_ROOT=/absolute/path/to/plugin-gradle-root
```

See [`../README.md`](../README.md).

## Quick reference

| Script | Purpose |
|--------|---------|
| [`dhu-start.sh`](dhu-start.sh) | **SDK Desktop Head Unit** + **USB accessory** (`--usb=SERIAL`): `usb` or `auto`. Optional **`adb`** subcommand = alternate transport (forward + head unit server). **`ANDROID_SERIAL`** if multiple devices. |
| [`gradle-root.sh`](gradle-root.sh) | **Deprecated** — exits `1`; use **`gradle-plugin-template.sh`** (root `app` removed) |
| [`gradle-plugin-template.sh`](gradle-plugin-template.sh) | `./gradlew` in **`$PA2_PLUGIN_GRADLE_ROOT`** |
| [`mockup-verify.sh`](mockup-verify.sh) | `npm run build` + `npm run check` in `mockup/` |
| [`adb-devices.sh`](adb-devices.sh) | `adb devices -l` |
| [`adb-plugin-install-debug.sh`](adb-plugin-install-debug.sh) | Uninstall plugin + `:app:installDebug` |
| [`logcat-car-media.sh`](logcat-car-media.sh) | Filtered logcat for Media3-related tags |
| [`connected-test-root.sh`](connected-test-root.sh) | **Deprecated** — use **`gradle-plugin-template.sh`** `:app:connectedDebugAndroidTest` |
| [`adb-screencap.sh`](adb-screencap.sh) | Phone screenshot PNG → `artifacts/screenshots/` (MCP: `adb_screencap_phone`) |
| [`logcat-snapshot.sh`](logcat-snapshot.sh) | Bounded `logcat -d` dump → `artifacts/logcat/` (MCP: `logcat_car_media_snapshot`) |
| [`adb-install-plugin-debug-apk.sh`](adb-install-plugin-debug-apk.sh) | `adb install -r` assembled plugin APK without Gradle (MCP: `adb_install_plugin_debug_apk`) |
| [`aa-plugin-smoke.sh`](aa-plugin-smoke.sh) | Unit tests (optional) + **uninstall + installDebug** + optional screencap + logcat snapshot (does not start DHU) |
| [`aa-plugin-device-cycle.sh`](aa-plugin-device-cycle.sh) | Same as smoke + **optional DHU in background** (`START_DHU=1` default) for UI inspection |
| [`aa-plugin-dhu-refresh.sh`](aa-plugin-dhu-refresh.sh) | **Autonomous agent path:** optional mockup verify + unit tests + installDebug + **force-stop Gearhead + plugin** + **replug banner** + analyze last DHU log + **start DHU** (foreground or background). Prefer this for “verify, reload, refresh” after plugin changes. |
| [`dhu-screencapture-macos.sh`](dhu-screencapture-macos.sh) | Optional macOS full-screen/region capture for DHU docs (not for headless CI) |

Design notes: [`SOLID.md`](SOLID.md).

**DHU vs phone screenshots:** Android Auto on the **phone** while projecting is captured with `adb-screencap.sh`. The **Desktop Head Unit** window is a macOS GUI app—use `dhu-screencapture-macos.sh` or manual capture; unattended window picking is out of scope.

## Examples

```bash
export PA2_PLUGIN_GRADLE_ROOT=/path/to/your/plugin-gradle-root
./android-auto-agents/scripts/dhu-start.sh auto
./android-auto-agents/scripts/gradle-plugin-template.sh :app:testDebugUnitTest
./android-auto-agents/scripts/mockup-verify.sh
ANDROID_SERIAL=XXXX ./android-auto-agents/scripts/adb-plugin-install-debug.sh
# Tests + uninstall old plugin + installDebug + optional DHU (background):
./android-auto-agents/scripts/aa-plugin-device-cycle.sh

# Full autonomous refresh (mockup optional, tests, install, force-stop AA + plugin, human replug banner, DHU):
./android-auto-agents/scripts/aa-plugin-dhu-refresh.sh
VERIFY_MOCKUP=1 SKIP_TESTS=0 ./android-auto-agents/scripts/aa-plugin-dhu-refresh.sh
```

## Shell pitfall (DHU)

Do **not** chain `adb … && pkill -x desktop-head-unit && …/dhu-start.sh` — if `pkill` finds nothing it returns **1** and `&&` skips the launcher. Use `; pkill … || true;` then the script (see `dhu-start.sh` header).
