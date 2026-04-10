# Automation and AI contributors

These notes support **people** reading research and design docs and **agents** doing implementation work. **Human-facing** UX write-ups: [docs/ux-research/](../ux-research/README.md) and [docs/design-system/](../design-system/00-design-system-index.md).

**UX/UI designer handoff** (role, constraints, reading list): **[Agents.md](../Agents.md)** on the **`mockups`** branch.

## Workflow and tooling (this branch)

- [Workflow and repository rules](workflow.md)
- [Tooling (IDE, MCP, Figma)](tooling.md)
- [Prototype frames and review checklists](prototype-and-engineering.md)
- [How the UX research was run (phases, methods)](research-methodology.md)

## Instruction shards (PRD, DHU, plugin scope)

Long-form content split out so root **[Agents.md](../../Agents.md)** (on **`main`**) stays scannable. Read **`AGENTS.md`** on the branch you are implementing from for boundaries; open these when your task needs depth.

| File | Contents |
|------|----------|
| [`01-prd-and-backlog.md`](01-prd-and-backlog.md) | PRD-style backlog table, workspace snapshot (update when behaviour changes). |
| [`02-dhu-and-car-testing.md`](02-dhu-and-car-testing.md) | Desktop Head Unit (USB), what to validate where, troubleshooting. |
| [`03-mockups-and-design.md`](03-mockups-and-design.md) | Mockup rules: **Power Ampache 2 = phone host**, plugin = Auto slice; car vs phone labelling. |
| [`04-roles-detailed.md`](04-roles-detailed.md) | UX researcher, mockup developer, development swarm (expanded). |
| [`05-integrity-and-tests.md`](05-integrity-and-tests.md) | Truth hierarchy, what counts as proof, coroutine tests, stubs. |
| [`06-plugin-template-hotspots.md`](06-plugin-template-hotspots.md) | Gradle roots, credentials, typical Kotlin entry points (refresh after upstream pulls). |
| [`07-handoff-plugin-ui-dhu.md`](07-handoff-plugin-ui-dhu.md) | Handoff for an agent with SDK + DHU: plugin Auto UI (Media3), specs, commands, boundaries. |
| [`08-portfolio-and-pr-policy.md`](08-portfolio-and-pr-policy.md) | Umbrella vs plugin fork, icefields PR scope, agent rules, PA2-Theme read-only, salvage/cleanup notes. |

When you **add** a new shard, link it from this README and from **`AGENTS.md` → Document map** on the implementation branch.

## Android Auto agent pack (scripts + MCP stubs)

Portable **roles**, **workflows**, **bash scripts**, and **MCP tool descriptor JSON** for DHU, Gradle, adb, and mockup verification: **[android-auto-agents/](../../android-auto-agents/README.md)** (repo root on this branch).

**Branch rule:** Research, design docs, user stories, and `mockups/` assets belong on the **`mockups`** branch. Implementation (Kotlin, Gradle) lives on **`main`**.

---

*Last updated: 2026-04-10 — second pass: `main` + `mockups` + feature branches (no umbrella / `dev` / `plugin/AndroidAuto`).*
