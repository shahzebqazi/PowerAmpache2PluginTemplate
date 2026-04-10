---
name: android-auto-scripts-harness
description: Use bundled bash scripts under android-auto-agents/scripts for DHU, Gradle, mockup, adb, and logcat (MCP-aligned).
---

# Android Auto scripts harness

## When to use

- **Car / DHU:** run **`./android-auto-agents/scripts/dhu-start.sh`** or **`auto`** from **repo root** (see [`dhu-usb-session.md`](dhu-usb-session.md)). For **verify + install + force-stop AA + replug banner + DHU** in one step, use **`./android-auto-agents/scripts/aa-plugin-dhu-refresh.sh`**.
- **Verification:** [`gradle-plugin-template.sh`](../scripts/gradle-plugin-template.sh) (nested PA2 plugin — set **`PA2_PLUGIN_GRADLE_ROOT`**), [`mockup-verify.sh`](../scripts/mockup-verify.sh) (Svelte mockup). **`gradle-root.sh`** is deprecated (no root `app`).
- **Debug:** [`logcat-car-media.sh`](../scripts/logcat-car-media.sh), [`adb-devices.sh`](../scripts/adb-devices.sh).
- **Install plugin:** [`adb-plugin-install-debug.sh`](../scripts/adb-plugin-install-debug.sh).

## SOLID + MCP

- Design: [`../scripts/SOLID.md`](../scripts/SOLID.md).
- Tool descriptors: [`../mcp/tool-descriptors/`](../mcp/tool-descriptors/) — implement an MCP server with **`cwd=WORKSPACE_ROOT`** and the documented `argv` templates.

## Do not

Chain **`adb … && pkill … && …/dhu-start.sh`** — use `; pkill … || true;` then the DHU script (see script header).
