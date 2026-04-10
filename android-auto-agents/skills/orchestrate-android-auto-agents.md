---
name: orchestrate-android-auto-agents
description: Route Android Auto / PA2 work across coordinator, UX, mockup, developer, tester, debugger, and shell-ci roles.
---

# Orchestrate Android Auto agents

## Roles

Load the matching contract from **`android-auto-agents/roles/`**:

1. **Coordinator** — backlog, tree ownership, done criteria
2. **UX/UI researcher** — evidence and constraints
3. **UI mockup developer** — `mockup/` Svelte
4. **Developer** — Kotlin/Gradle in correct module
5. **Tester** — commands + results
6. **Debugger** — repro, logs, DHU recovery
7. **Shell/CI** — automation evidence

## Default workflow

Mockup-driven UI: **`workflows/mockup-tdd-dhu.md`**.

Nested PA2 plugin Gradle: **`workflows/plugin-template-verify.md`**.

## Verification contract

**`contracts/verification-checklist.md`**

## MCP (future)

See **`android-auto-agents/mcp/`** for JSON tool stubs to implement a server.
