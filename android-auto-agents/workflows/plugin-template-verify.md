# Workflow: PA2 nested plugin Gradle verification

## When to use

Any change under the **plugin project’s** **`app/`** module (maintainer default scope).

## Environment

```bash
export PA2_PLUGIN_GRADLE_ROOT=/absolute/path/to/plugin-gradle-root   # directory containing ./gradlew
```

## Build and unit tests

```bash
cd "$PA2_PLUGIN_GRADLE_ROOT"
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```

Or from the **consumer workspace root**:

```bash
./android-auto-agents/scripts/gradle-plugin-template.sh :app:assembleDebug
./android-auto-agents/scripts/gradle-plugin-template.sh :app:testDebugUnitTest
```

## Install on device

Signature conflicts:

```bash
adb uninstall luci.sixsixsix.powerampache2.plugin
```

Then:

```bash
./android-auto-agents/scripts/adb-plugin-install-debug.sh
```

## Android Auto / DHU

- **Unknown sources** in Android Auto developer settings for sideloaded builds
- Consumer workspace root: `./android-auto-agents/scripts/dhu-start.sh` or `./android-auto-agents/scripts/dhu-start.sh auto`

## Common failures

| Symptom | Action |
|---------|--------|
| `INSTALL_FAILED_UPDATE_INCOMPATIBLE` | `adb uninstall` package, reinstall |
| `PA2_PLUGIN_GRADLE_ROOT` errors | Export absolute path to Gradle root |
| KSP / Kotlin mismatch | Align `ksp` version with `kotlin` in `gradle/libs.versions.toml` |
| DHU exits 0 but no handshake | Use **`auto`** mode; check SSL / USB lines in DHU log |
