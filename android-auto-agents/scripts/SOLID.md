# Script harness principles (SOLID)

These bash entry points live under `android-auto-agents/scripts/` so **agents, MCP servers, and humans** share one predictable interface. Each script should stay small and composable.

## Single responsibility

| Concern | Script |
|--------|--------|
| Desktop Head Unit (USB) | `dhu-start.sh` |
| Gradle — ~~workspace root `app`~~ | **`gradle-root.sh`** deprecated — use **`gradle-plugin-template.sh`** |
| Gradle — nested PA2 plugin (`PA2_PLUGIN_GRADLE_ROOT`) | `gradle-plugin-template.sh` |
| Mockup build + Svelte check | `mockup-verify.sh` |
| adb device list | `adb-devices.sh` |
| Plugin reinstall debug | `adb-plugin-install-debug.sh` |
| Plugin smoke (tests + reinstall + capture) | `aa-plugin-smoke.sh` |
| Plugin device cycle (+ optional background DHU) | `aa-plugin-device-cycle.sh` |
| Media / session logcat | `logcat-car-media.sh` |
| Instrumented tests (root app) | `connected-test-root.sh` |

Do not fold unrelated steps into one script (e.g. DHU + Gradle); orchestration belongs in **workflows**, **CI**, or an **MCP tool** that calls multiple scripts in order.

## Open for extension

- **Environment:** `ANDROID_SERIAL`, `ANDROID_HOME`, `DHU_RETRY`, `SKIP_NPM_CI` (see each script’s header).
- **New scripts:** add a row to `README.md` in this folder and a **tool descriptor** under `mcp/tool-descriptors/` when an MCP server should expose it.

## Liskov-style consistency

- All scripts resolve the **workspace root** as `android-auto-agents/scripts` → two levels up (`../..`), then `cd` or pass absolute paths. Same assumption as `dhu-start.sh` for `local.properties`.
- **Working directory:** run from **repository root** (`./android-auto-agents/scripts/foo.sh`) unless the script documents otherwise.

## Interface segregation

- `lib/repo-root.sh` — optional helper for sourcing from future scripts; not required for one-liners.
- Exec-heavy scripts (`gradle-root.sh`, `gradle-plugin-template.sh`) delegate to **`./gradlew`** so Gradle owns the JVM lifecycle.

## Dependency inversion

- Scripts depend on **`adb`**, **`./gradlew`**, and **npm** on `PATH`, not on a particular IDE.
- **SDK location** comes from `local.properties` / `ANDROID_HOME` (same as Android Studio).

## MCP mapping

See `../mcp/README.md` and `../mcp/manifest.json`. Implementations should set `cwd` to `WORKSPACE_ROOT` and invoke the path in the matching `tool-descriptors/*.json`.
