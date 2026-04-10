# Android Auto AI agents (workspace package)

Structured **roles**, **workflows**, and **MCP-oriented stubs** for agentic tooling around **Android Auto**, **Media3**, **DHU**, and PA2-related Gradle layouts.

## Relationship to the repo

| Source | Role |
|--------|------|
| **[`AGENTS.md`](../AGENTS.md)** | Single source of truth for **PRD backlog**, swarm rules, DHU steps, and maintainer boundaries until this package grows. |
| **`android-auto-agents/`** | **Portable** role contracts, verification methodology, and future **MCP** tool JSON — safe to extract into a separate repo later. |
| **[`scripts/dhu-start.sh`](scripts/dhu-start.sh)** | Canonical **USB-only** Desktop Head Unit launcher (do not chain with `&& pkill` — see script header). |
| **[`scripts/aa-plugin-dhu-refresh.sh`](scripts/aa-plugin-dhu-refresh.sh)** | **Autonomous** verify → install → **force-stop Android Auto + plugin** → **replug instructions** → DHU (see [`scripts/README.md`](scripts/README.md)). |

## Nested PA2 plugin Gradle root

Scripts that build or install the **plugin** APK require:

```bash
export PA2_PLUGIN_GRADLE_ROOT=/absolute/path/to/directory/containing/gradlew
```

Use the **absolute path** to the plugin project’s Gradle root (the folder with `./gradlew`). The consumer workspace documents where that clone lives (see **`AGENTS.md`** / **`README.md`**). This package does **not** hardcode a subdirectory name.

## Layout

| Path | Contents |
|------|----------|
| [`roles/`](roles/) | One file per agent **persona**: goals, inputs, outputs, guardrails. |
| [`workflows/`](workflows/) | End-to-end **methodology** (mockup → TDD → DHU, install/signing, Gradle roots). |
| [`mcp/`](mcp/) | **Tool descriptor** JSON stubs + manifest; see [`mcp/automation-pipeline.md`](mcp/automation-pipeline.md) for recommended agent tool order. |
| [`skills/`](skills/) | Short **skill** text for Cursor/Codex-style loaders (linking into `roles/` + `workflows/`). |
| [`contracts/`](contracts/) | **Checklists** and acceptance patterns agents should not hand-wave. |
| [`scripts/`](scripts/) | **Harness scripts** (DHU, Gradle, mockup, adb, logcat) — see [`scripts/README.md`](scripts/README.md) and [`scripts/SOLID.md`](scripts/SOLID.md). |

## Principles

1. **Host-rendered media** — Car UI for the standard path is **OEM/host**; agents optimize **metadata, session, browse, errors**, not pixel-perfect custom AA chrome unless the product adopts Car App Library.
2. **Correct Gradle root** — Root `app` vs nested PA2 plugin vs **Power-Ampache-2**; cite commands from the tree you edited. Set **`PA2_PLUGIN_GRADLE_ROOT`** when using plugin harness scripts.
3. **Evidence before “done”** — Paste **`./gradlew`** / **`npm run check`** results; car work expects **DHU** notes when in scope.
4. **No secrets** — Never commit `local.properties`, keystores, or server credentials; use `local.properties.example` / debug hints only.

## Cursor (this project)

- **Project rule:** [`.cursor/rules/android-auto-agents.mdc`](../.cursor/rules/android-auto-agents.mdc) with **`alwaysApply: true`** — Cursor loads the agent pack table and DHU guardrails in every chat for this workspace.
- **MCP:** Add a server under **Cursor Settings → MCP** when you implement one against `mcp/tool-descriptors/`.

## Next steps (optional)

- Publish this folder as **`android-auto-agents`** on GitHub and submodule it here.
- Implement `mcp/server` (Node or Python) that wraps `android-auto-agents/scripts/*.sh`, `gradlew`, and `adb` with the descriptors in `mcp/tool-descriptors/`.
