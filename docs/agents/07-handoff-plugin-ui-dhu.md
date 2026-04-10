# Handoff — Plugin Android Auto UI (SDK + DHU)

Use this when an agent has **Android SDK**, a **USB device**, and **DHU** available, and should continue **plugin** work aligned with mocks and research — not **Power-Ampache-2** phone UI unless explicitly in scope.

---

## Goal

Implement and validate **Android Auto / Media3–driven “UI”** for **`PowerAmpache2PluginTemplate`** on branch **`plugin/AndroidAuto`**: what the **car host** shows (browse hierarchy, now-playing metadata, artwork, errors, actions) is largely **OEM-rendered**. Your job is to supply the right **session**, **browse tree**, **MediaItem** metadata, and **playback behavior** so DHU matches the **information architecture** in [`mockup/`](../../mockup/README.md) and [`docs/ux-research/08-mockup-handoff-package.md`](../../ux-research/08-mockup-handoff-package.md), within [`docs/agents/03-mockups-and-design.md`](03-mockups-and-design.md) rules (**PA2 = phone app**, **plugin = Auto slice**).

---

## Preconditions

| Requirement | Notes |
|-------------|--------|
| **Repo layout** | Consumer repo: **android-auto** (this tree). Nested clone: **`PowerAmpache2PluginTemplate/`** at `plugin/AndroidAuto` (merge **`upstream/main`** when syncing). |
| **SDK** | `sdk.dir` in `PowerAmpache2PluginTemplate/local.properties` or **`ANDROID_HOME`** / **`ANDROID_SDK_ROOT`**. JDK **17**. |
| **Gradle root for plugin** | `export PA2_PLUGIN_GRADLE_ROOT=/absolute/path/to/PowerAmpache2PluginTemplate` — required for harness scripts from android-auto root ([`android-auto-agents/README.md`](../../android-auto-agents/README.md)). |
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
6. **Mockups** — [`mockup/`](../../mockup/README.md); after UI-facing changes run **`npm run check`** / **`npm run build`** in **`mockup/`** if you touch it.  
7. **Design / UX** — [`docs/design-system/`](../../design-system/00-design-system-index.md), [`docs/ux-research/01-platform-constraint-sheet.md`](../../ux-research/01-platform-constraint-sheet.md).

---

## Current baseline (do not duplicate blindly)

The plugin **`app`** module already includes a **Media3 MVP**: **`PluginMediaLibraryService`**, **`MediaLibrarySession`**, **ExoPlayer**, shallow browse from **domain mocks**, **`automotive_app_desc.xml`**, and car **meta-data** in **`AndroidManifest.xml`**. Paths (relative to **`PowerAmpache2PluginTemplate/`**):

- `app/src/main/java/.../PluginMediaLibraryService.kt`
- `app/src/main/java/.../media/MediaLibraryCatalog.kt`
- `app/src/main/java/.../media/MediaItemFactory.kt`
- `app/src/main/res/xml/automotive_app_desc.xml`

Extend or refine this stack; do not assume a greenfield service unless the task says so.

---

## Commands (evidence before “done”)

From **android-auto repo root** (with **`PA2_PLUGIN_GRADLE_ROOT`** set):

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
- This umbrella repo has **no** root **`app/`** — DHU uses the **nested plugin** or **Power-Ampache-2** APK ([`README.md`](../../README.md)).  
- Live **Ampache / `MusicFetcherImpl`** wiring without **`data`/`domain`** scope in the task.  
- Copying **`PowerAmpache2PluginTemplateOld/`**.  
- Play Store / OEM-only sign-off.

---

## PRD row

When behaviour materially changes, coordinator updates **[`01-prd-and-backlog.md`](01-prd-and-backlog.md)** (e.g. P0-3) per [`android-auto-agents/contracts/verification-checklist.md`](../../android-auto-agents/contracts/verification-checklist.md).
