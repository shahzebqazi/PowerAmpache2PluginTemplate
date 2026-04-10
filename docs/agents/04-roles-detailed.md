# Roles (detailed)

Portable playbooks also live under **`android-auto-agents/roles/`**.

## Coordinator

Sequencing, scope boundaries (**`mockups/`** vs **`main`** vs **Power-Ampache-2**), merge targets, and **done** criteria (tests + **DHU** when car work). Keeps **design** and **Kotlin** tracks from fighting.

## UX / UI researcher

Evidence-backed research on **driving music** UI, **accessibility**, **safety / distraction**. Outputs: briefs with **citations**, implications for **PA2 phone** vs **Auto**. Plan: [`docs/android-auto-ux-research-plan.md`](../../android-auto-ux-research-plan.md).

## UI mockup developer (Svelte)

Disposable **`mockups/web-mockup/`** prototypes. **Must** follow [`03-mockups-and-design.md`](03-mockups-and-design.md) (**PA2 = phone**, plugin = Auto slice). Handoff package: [`docs/ux-research/08-prototype-handoff-package.md`](../ux-research/08-prototype-handoff-package.md). Validate with Svelte tooling / MCP when available.

## Development and testing swarm

Kotlin / Gradle / tests. **Default: template `app/` only** unless the task names **`data`/`domain`**.

### Mockup-driven workflow

1. **Read** the mockup + handoff scenario.
2. **TDD:** failing tests first (**`app/src/test/`**, **`app/src/androidTest/`**); **fake** domain boundaries from **`app`** tests so **`data`/`domain`** stay untouched unless in scope.
3. **Verify:** **`PA2_PLUGIN_GRADLE_ROOT=.../PowerAmpache2PluginTemplate ./android-auto-agents/scripts/gradle-plugin-template.sh :app:testDebugUnitTest`** (and **`:app:lintDebug`** as needed); **`cd mockup && npm run check`** when editing mockups.
4. **DHU** when the change is **car / Media3** — see [`02-dhu-and-car-testing.md`](02-dhu-and-car-testing.md).

## Shell / CI agent (optional)

Runs Gradle, **`adb`**, DHU prep, Pages deploy; pastes command output when claiming green.

## Coordination handoffs

| Handoff | From | To |
|--------|------|-----|
| Research | Researcher | Mockup + dev swarm |
| Mockup | Mockup dev | Dev swarm: **mockup → TDD → DHU** |
| PR target | Human | **PowerAmpache2PluginTemplate** or **Power-Ampache-2** per task |
