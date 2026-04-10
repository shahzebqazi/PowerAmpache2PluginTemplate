# Desktop Head Unit and car testing

Standard entrypoint from repo root: **`./android-auto-agents/scripts/dhu-start.sh`** or **`./android-auto-agents/scripts/dhu-start.sh auto`** (**USB accessory** only; no TCP / `adb forward` in this script).

## AI agents — fresh DHU session

- **USB only:** the script **force-stops Android Auto** on the device before launch.
- **USB retries:** **`auto`** reruns on SSL / handshake / premature exit patterns.
- **Shell:** do **not** use **`adb … && pkill -x desktop-head-unit && …`** — **`pkill`** can exit **`1`** and skip the next command. Use **`; pkill … || true;`** or run the script alone.
- **Stable GUI:** long sessions often need **Terminal.app** (or keep the terminal open); IDE-launched shells may exit before DHU stays up.
- **Prereqs:** **`sdk.dir`** in **`local.properties`** at the **Gradle root** (repo root on **`main`**, or the path you set as **`PA2_PLUGIN_GRADLE_ROOT`**), or **`ANDROID_HOME`**. **`ANDROID_SERIAL=…`** when multiple devices.

## What DHU validates

| Build | Path | Use DHU for |
|-------|------|-------------|
| **This fork (`main`)** | Repo root — **`./gradlew :app:assembleDebug`** or set **`PA2_PLUGIN_GRADLE_ROOT`** to that root from **`mockups`** when using harness scripts | **When** the installed APK registers **Media3** / **Android Auto** (manifest-dependent on your branch). |
| **Power-Ampache-2** | Separate clone | **Yes** for full-app **Media3** + Auto sign-off when that tree is the target. |

## Typical USB + phone steps (contributors)

1. Install the **car-capable** APK you are testing (plugin debug and/or full PA2 per manifest).
2. **Power Ampache 2** on the phone: sign in, sync, confirm playback if the host needs real session state.
3. Enable **Android Auto developer mode** and **Unknown sources** (sideloaded builds).
4. **USB** debug authorized; **`adb devices`** shows **`device`**.
5. From **this repo root**: **`./android-auto-agents/scripts/dhu-start.sh`** or **`auto`**.
6. Open the media app in DHU; exercise browse, now playing, errors.

## If something does not appear in DHU

- Confirm the APK declares **`com.google.android.gms.car.application`** and a **media browser / library** service where applicable.
- **`INSTALL_FAILED_UPDATE_INCOMPATIBLE`** → **`adb uninstall`** the conflicting package, reinstall.
- **Infinite loading:** check **Media3** session commands, **`onGetLibraryRoot` / `onGetChildren`**, **`notifyChildrenChanged`** after catalog readiness; capture **`adb logcat`** (**`MediaLibrary`**, **`MediaSession`**, **`AndroidAuto`**).

## Release hygiene

Any **Car App Library** surface needs **`HostValidator`**. **Media3** shipping builds still need manifest/session hygiene and Play **car quality** checks.

## References

- Google: [Run the DHU](https://developer.android.com/training/cars/testing/dhu)
- Repo: **`android-auto-agents/skills/dhu-usb-session.md`**, **`android-auto-agents/workflows/dhu-troubleshooting.md`**
