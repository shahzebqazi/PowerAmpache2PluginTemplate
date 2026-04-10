# Automation pipeline (agents / MCP)

Recommended order to validate the **PA2 Android Auto plugin** path with **adb-backed** artifacts (CI-friendly) plus optional **Desktop Head Unit** on the host.

**Prerequisite:** set **`PA2_PLUGIN_GRADLE_ROOT`** to the plugin project directory that contains `./gradlew` (see [README.md](../README.md)).

## CI-friendly path (device or emulator)

1. **`gradle_template_unit_test`** — `:app:testDebugUnitTest` via `gradle-plugin-template.sh`.
2. **`gradle_template_assemble_debug`** — `:app:assembleDebug`.
3. Install either:
   - **`adb_plugin_reinstall_debug`** — uninstall + Gradle `installDebug`, or
   - **`adb_install_plugin_debug_apk`** — `adb install -r` on `app/build/outputs/apk/debug/app-debug.apk` if the APK is already built.
4. On the device: open **Android Auto** / media app once if needed; exercise the **plugin** media entry (manual or UI automation outside this repo).
5. **`adb_screencap_phone`** — PNG under `artifacts/screenshots/` (phone often shows Android Auto while projecting).
6. **`logcat_car_media_snapshot`** — bounded dump under `artifacts/logcat/`; default tags target Media3 / car media.

**Composite:** **`run_aa_plugin_smoke`** runs tests (optional skip), **uninstall + `installDebug`** (clean reinstall), optional sleep, screencap, and logcat snapshot. It does **not** start DHU.

**Full interactive cycle:** **`run_aa_plugin_device_cycle`** runs the same steps as smoke, then starts **`dhu_start_usb`** in the **background** by default (`START_DHU=1`) so you can inspect the head unit UI. Set `START_DHU=0` or `SKIP_DHU=1` to skip.

## Interactive host path (DHU)

1. Connect USB; **`dhu_start_usb`** with `mode` `usb` or `auto` from repo root (long-running; often needs a real terminal session). Set **`ANDROID_SERIAL`** if multiple adb devices.
2. Default DHU testing is **USB accessory + SDK `desktop-head-unit`** (no head unit server on the phone). Optional **`dhu_start_usb`** `mode` **`adb`** only if you explicitly use the ADB-tunnel path from [Run the DHU](https://developer.android.com/training/cars/testing/dhu).
3. Optional: **`dhu_screencapture_macos`** on macOS for a PNG of the DHU or full screen — not headless-safe; see script header.

## Limits

- **DHU window** capture is OS-specific and not as stable as **adb screencap** for regression testing.
- Pixel-diff / baseline image comparison is **out of scope** here; capture files are for human or future tooling review.
- **`artifacts/`** is gitignored; outputs are local-only unless your CI uploads them.

See also [README.md](README.md) and [../scripts/README.md](../scripts/README.md).
