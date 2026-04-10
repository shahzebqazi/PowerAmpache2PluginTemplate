# MCP (Model Context Protocol) — stubs

This folder describes **future** MCP tools. No server is implemented here yet; implementers can map each JSON file to a **tool** in an MCP server (stdio or HTTP).

## Design

- **Read-only** tools first (`describe_*`, `read_role`) — safe for agents.
- **Exec** tools (`run_gradle`, `dhu_start`) must be **opt-in** and **sandboxed** (user approval, allowlisted commands).

## Files

| Descriptor | Tool `name` | Intended behavior |
|------------|-------------|---------------------|
| [`tool-descriptors/dhu_start_usb.json`](tool-descriptors/dhu_start_usb.json) | `dhu_start_usb` | `./android-auto-agents/scripts/dhu-start.sh` (USB; long-running) |
| [`tool-descriptors/dhu_screencapture_macos.json`](tool-descriptors/dhu_screencapture_macos.json) | `dhu_screencapture_macos` | macOS `dhu-screencapture-macos.sh` (optional DHU/full-screen PNG) |
| [`tool-descriptors/gradle_root_unit_test.json`](tool-descriptors/gradle_root_unit_test.json) | `gradle_root_unit_test` | **Deprecated** — root `app` removed; use **`gradle_template_test`** |
| [`tool-descriptors/gradle_template_test.json`](tool-descriptors/gradle_template_test.json) | `gradle_template_unit_test` | Nested plugin tests via `gradle-plugin-template.sh` (**`PA2_PLUGIN_GRADLE_ROOT`**) |
| [`tool-descriptors/gradle_template_assemble_debug.json`](tool-descriptors/gradle_template_assemble_debug.json) | `gradle_template_assemble_debug` | Nested plugin `:app:assembleDebug` (**`PA2_PLUGIN_GRADLE_ROOT`**) |
| [`tool-descriptors/adb_plugin_install.json`](tool-descriptors/adb_plugin_install.json) | `adb_plugin_reinstall_debug` | `adb-plugin-install-debug.sh` (uninstall + `installDebug`) |
| [`tool-descriptors/adb_install_plugin_debug_apk.json`](tool-descriptors/adb_install_plugin_debug_apk.json) | `adb_install_plugin_debug_apk` | `adb-install-plugin-debug-apk.sh` (`install -r` existing APK) |
| [`tool-descriptors/adb_screencap_phone.json`](tool-descriptors/adb_screencap_phone.json) | `adb_screencap_phone` | `adb-screencap.sh` → `artifacts/screenshots/` |
| [`tool-descriptors/mockup_npm_verify.json`](tool-descriptors/mockup_npm_verify.json) | `mockup_npm_verify` | `mockup-verify.sh` |
| [`tool-descriptors/logcat_car_media.json`](tool-descriptors/logcat_car_media.json) | `logcat_car_media` | `logcat-car-media.sh` (streaming) |
| [`tool-descriptors/logcat_car_media_snapshot.json`](tool-descriptors/logcat_car_media_snapshot.json) | `logcat_car_media_snapshot` | `logcat-snapshot.sh` (bounded dump) |
| [`tool-descriptors/run_aa_plugin_smoke.json`](tool-descriptors/run_aa_plugin_smoke.json) | `run_aa_plugin_smoke` | `aa-plugin-smoke.sh` (tests + uninstall + installDebug + capture) |
| [`tool-descriptors/run_aa_plugin_device_cycle.json`](tool-descriptors/run_aa_plugin_device_cycle.json) | `run_aa_plugin_device_cycle` | `aa-plugin-device-cycle.sh` (smoke + optional background DHU) |
| [`tool-descriptors/read_agent_role.json`](tool-descriptors/read_agent_role.json) | `read_agent_role` | Read `roles/<id>.md` |
| [`manifest.json`](manifest.json) | — | Package metadata |
| [`automation-pipeline.md`](automation-pipeline.md) | — | Recommended tool order for agents |

## Automation

- **Phone screenshots** (`adb_screencap_phone`) are the primary automated visual artifact; they work with a single `adb` device or `ANDROID_SERIAL`.
- **DHU** runs on the desktop; USB transport is covered by `dhu_start_usb`. Full **DHU window** capture is optional (`dhu_screencapture_macos`) and not headless-safe on all setups.
- Generated files go under **`artifacts/`** (gitignored at repo root). MCP servers should cap stdout from Gradle/logcat for LLM context.

## Implementation sketch

1. Resolve **workspace root** (env `WORKSPACE_ROOT` or argv).
2. For `dhu_start_usb`, `exec` with `cwd=workspace_root`, `argv=[./android-auto-agents/scripts/dhu-start.sh, auto|usb]`.
3. For Gradle helpers, prefer **`android-auto-agents/scripts/gradle-*.sh`** so SDK paths match human workflows; set **`PA2_PLUGIN_GRADLE_ROOT`** for nested plugin scripts.
4. Return **stdout/stderr** and **exit code**; cap output length for LLM context. Long-running tools (`dhu_start_usb`, `logcat_car_media`) may need user approval or streaming.
