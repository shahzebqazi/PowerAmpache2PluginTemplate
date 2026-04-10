# Integrity, tests, and honest completion

## Truth hierarchy

| Priority | Source |
|----------|--------|
| 1 | **`AGENTS.md`** + **`docs/agents/01-prd-and-backlog.md`** (backlog / snapshot) |
| 2 | **Checked-in code** (manifests, Gradle, Kotlin) |
| 3 | **README**, chat, issues — secondary |

Before claiming **Android Auto** wiring, **open `AndroidManifest.xml`** on a **`main`** checkout (repo root), not **`PowerAmpache2PluginTemplateOld/`** if you have a local copy.

## What counts as proof

- **Placeholder** tests (**`ExampleUnitTest`**, trivial asserts) are **not** proof of Media3, DHU, or browse.
- Real work needs assertions on **behaviour**: state, queue, errors, mapping, Compose semantics.
- **Anti-patterns:** delete failing tests; **`assertTrue(true)`**; mock the **class under test** so it cannot fail.
- **Coroutine / ViewModel tests:** **`runTest`**, **`MainDispatcherRule`** (or **`StandardTestDispatcher`** + idle) so **`viewModelScope.launch`** actually runs.
- **Fakes** at boundaries are fine when outcomes are asserted.

## Stubs

If **`MusicFetcherImpl`** or similar is minimal, **document** it — do not imply full drill-down works until implemented and backlog updated.

## Verification

- **“Tests pass”** means **relevant** tasks were run and results cited (or failure details).
- **Car tasks:** state **DHU / device** attempts or **why blocked**.

## Coordinator follow-up

If **README** disagrees with **AGENTS** or manifest facts, fix **README** in the same pass.
