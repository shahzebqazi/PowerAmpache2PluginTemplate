# Workflow: Mockup → TDD → DHU

Mandatory order for **mockup-backed** UI in this workspace (see [`AGENTS.md`](../../AGENTS.md) Development swarm).

## 1. Review mockup

- Open the relevant **`#/mockup/...`** route and read the matching **Svelte** source.
- Treat output as **hierarchy, density, intent** — not pixel spec for **host-rendered** Auto.

## 2. TDD

- **Red:** failing tests in **`app/src/test`** (and **`androidTest`** when Compose semantics/integration require).
- **Green:** minimal Kotlin/resources in the **correct `app` module** (maintainer scope for the tree you edit).
- **Refactor:** only with tests green.

## 3. Automated verification

**Nested PA2 plugin** (set **`PA2_PLUGIN_GRADLE_ROOT`** to **`PowerAmpache2PluginTemplate/`**):

```text
./android-auto-agents/scripts/gradle-plugin-template.sh :app:testDebugUnitTest
./android-auto-agents/scripts/gradle-plugin-template.sh :app:connectedDebugAndroidTest   # when UI/integration demands it
```

**Mockup only:** `cd mockup && npm run check`

## 4. DHU (car / Media3 in scope)

- Install the **debug APK** that registers on Auto (**plugin** or **Power-Ampache-2** per task).
- From **repo root**: `./android-auto-agents/scripts/dhu-start.sh` or `./android-auto-agents/scripts/dhu-start.sh auto`.
- Record **flows exercised** vs mockup **tasks** (not browser pixels).

## Exit criteria

- Tests cited and passing.
- DHU notes present when the task touches **Android Auto** behaviour.
