# Handoff — Plugin Android Auto UI (SDK + DHU)

Use this when an agent has **Android SDK**, a **USB device**, and **DHU** available, and should continue **plugin** work aligned with mocks and research — not **Power-Ampache-2** phone UI unless explicitly in scope.

---

## Goal

Implement and validate **Android Auto / Media3** behaviour for **`PowerAmpache2PluginTemplate`** on **`main`**: what the **car host** shows (browse hierarchy, now-playing metadata, artwork, errors, actions) is **OEM-rendered** — you do **not** implement a custom car player. **Session**, **browse tree**, **MediaItem** metadata, and **playback behavior** should align with the **information architecture** in [`mockups/web-mockup/`](../../mockups/web-mockup/README.md) and [`docs/ux-research/08-prototype-handoff-package.md`](../ux-research/08-prototype-handoff-package.md), within [`docs/agents/03-mockups-and-design.md`](03-mockups-and-design.md) rules (**PA2 = phone app**, **plugin = Auto slice**).

---

## Preconditions

| Requirement | Notes |
|-------------|--------|
| **Repo layout** | **`mockups`** branch (this tree): docs + `mockups/` + `android-auto-agents/`. **Implementation:** clone or open the same repo on **`main`** (single Gradle project — no nested clone required). |
| **SDK** | `sdk.dir` in `local.properties` at the **plugin repo root** (on **`main`**) or **`ANDROID_HOME`** / **`ANDROID_SDK_ROOT`**. JDK **17** (or the version in `gradle.properties`). |
| **Gradle root for harness scripts** | If you run scripts from the **`mockups`** checkout: `export PA2_PLUGIN_GRADLE_ROOT=/absolute/path/to/a/main-branch-clone` ([`android-auto-agents/README.md`](../../android-auto-agents/README.md)). |
| **Device** | USB debugging, **`adb devices`** shows `device`. |
| **DHU** | SDK Manager → **Android Auto Desktop Head Unit** (or `sdkmanager "extras;google;auto"`). |
| **Forbidden** | Do **not** use **`PowerAmpache2PluginTemplateOld/`** as source of truth ([`01-prd-and-backlog.md`](01-prd-and-backlog.md)). |

---

## Specs to read before coding

1. **[`AGENTS.md`](../../AGENTS.md)** — boundaries, swarm rules, default **`app/`** only.  
2. **[`03-mockups-and-design.md`](03-mockups-and-design.md)** — phone vs plugin framing.  
3. **[`02-dhu-and-car-testing.md`](02-dhu-and-car-testing.md)** — USB DHU, what DHU validates.  
4. **[`06-plugin-template-hotspots.md`](06-plugin-template-hotspots.md)** — Gradle map, mocks under **`domain/.../model/mocks/`**.  
5. **[`android-auto-agents/contracts/android-auto-media-compliance-checklist.md`](../../android-auto-agents/contracts/android-auto-media-compliance-checklist.md)** — manifest / session / errors.  
6. **Mockups** — [`mockups/web-mockup/`](../../mockups/web-mockup/README.md); after UI-facing changes run **`npm run check`** / **`npm run build`** there if you touch it.  
7. **Design / UX** — [`docs/design-system/`](../../design-system/00-design-system-index.md), [`docs/ux-research/01-platform-constraint-sheet.md`](../../ux-research/01-platform-constraint-sheet.md).

---

## Current baseline (do not duplicate blindly)

The plugin **`app`** module on **`main`** includes a **Media3**-based **`AndroidAutoMediaLibraryService`** (`MediaLibrarySession`, **ExoPlayer**, browse from **MusicFetcher** / domain). Verify **actual** paths and manifest entries after each pull — do not assume extra helper types unless present on your branch.

Extend or refine this stack; do not assume a greenfield service unless the task says so.

---

## Commands (evidence before “done”)

From the repo root that contains **`android-auto-agents/`** (e.g. **`mockups`** branch), with **`PA2_PLUGIN_GRADLE_ROOT`** pointing at a **`main`** checkout **or** from the **`main`** Gradle root directly:

```bash
./android-auto-agents/scripts/gradle-plugin-template.sh :app:testDebugUnitTest
./android-auto-agents/scripts/gradle-plugin-template.sh :app:assembleDebug
# Install debug APK to device, then:
./android-auto-agents/scripts/dhu-start.sh auto
```

**DHU guardrails:** run the script from repo root; do **not** chain with `adb … && pkill … && …` in a way that skips the launcher — see script header in [`dhu-start.sh`](../../android-auto-agents/scripts/dhu-start.sh).

**Logcat (optional):** [`android-auto-agents/scripts/logcat-car-media.sh`](../../android-auto-agents/scripts/logcat-car-media.sh) for media-related tags.

---

## What to implement next (suggested order)

1. **Map mockup screens** to **Media3 surfaces** (browse nodes, now-playing fields, error rows) — host UI is not Compose in the car; changes are **metadata**, **tree shape**, **queue**, **session errors**.  
2. **Align browse depth and labels** with mockup IA; keep **shallow** until product asks for deeper hierarchy.  
3. **Harden errors** — invalid streams, empty catalog — per [Google car media errors](https://developer.android.com/training/cars/media/errors) and project checklist.  
4. **DHU smoke** — app in media list, browse, play/pause, art/placeholder, forced error path.  
5. **Unit tests** — keep pure mapping / ID logic in JVM tests under **`app/src/test/`** ([`05-integrity-and-tests.md`](05-integrity-and-tests.md)).

---

## Out of scope unless explicitly assigned

- Full **Power-Ampache-2** phone app UI (separate repo).  
- The **`mockups`** branch has **no** Gradle **`app/`** — use a **`main`** checkout for APK builds; DHU exercises the installed media app ([`README.md`](../../README.md)).  
- Live **Ampache / `MusicFetcherImpl`** wiring without **`data`/`domain`** scope in the task.  
- Copying **`PowerAmpache2PluginTemplateOld/`**.  
- Play Store / OEM-only sign-off.

---

## PRD row

When behaviour materially changes, coordinator updates **[`01-prd-and-backlog.md`](01-prd-and-backlog.md)** (e.g. P0-3) per [`android-auto-agents/contracts/verification-checklist.md`](../../android-auto-agents/contracts/verification-checklist.md).
