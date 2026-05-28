# Squashed cursor branch history (no code)

**Purpose:** Preserve what 12 abandoned fork branches attempted, compared against **`plugin/auto-main`** as of 2026-05-28 (the line icefields merged upstream and continues on). No source from those branches is kept here—only lessons.

**Source branches squashed into this doc:**

| Branch | PR | Merged? |
|--------|-----|---------|
| `feature-cursor/auto-browse-dedupe` | #19 | Closed, not merged |
| `feature-cursor/auto-debug-host-wake` | #17 | Closed, not merged |
| `feature-cursor/auto-lazy-section-loading` | #18 | Closed, not merged |
| `feature-cursor/auto-playback-resumption` | #20 | Closed, not merged |
| `feature-cursor/drive-safe-overlay` | #21 | Closed, not merged |
| `cursor-cloud/shahzebqazi-PluginAndroidAuto-docs-5244` | #13 | Closed, not merged |
| `cursor-cloud/env-setup-c015` | #14 | Closed, not merged |
| `cursor-cloud/archive-main-v1.0.0-4dc1` | (archive) | N/A |
| `cursor-cloud/tech-lead-android-auto-media3-5410` | — | N/A |
| `cursor-cloud/ux-ui-car-plugin-ux-research-5a36` | #1 | Merged to old `dev-main` only |
| `mockups` | #2 | Merged to old `dev-main` only |
| `cursor-cloud/bug-fix-tests-b1c3` | #16 | Closed; **was** merged into `plugin/auto-main` history |

---

## Executive summary — what went wrong (meta)

### Cursor / AI workflow mistakes

1. **Many parallel branches, no integration.** Five `feature-cursor/*` branches landed the same day (2026-05-05) with overlapping edits to `Pa2MediaLibraryService.kt`. None merged. Agents optimized for “one PR per idea” without a single integration branch or maintainer review—classic **merge debt**.

2. **Compile/tests ≠ Android Auto works.** JVM tests in `Pa2MediaLibraryServiceBugTests.kt` documented bugs well, but passing `:app:test` does not prove DHU/head-unit browse, queue scroll, or host IPC. Agents claimed progress from tests the car UI never exercises.

3. **Scope creep in `AGENTS.md` and docs.** `bug-fix-tests-b1c3` and `archive-main` added orchestration, MkDocs, mockups, and GitHub Pages—while the MVP blocker was **host → `MusicFetcher` → browse notifications**, owned by icefields on `plugin/auto-*` branches.

4. **Wrong repo layer.** Several branches edited `app/` to fix symptoms of empty/stale library data. The real fixes were **`data/` + host pushes + `notifyChildrenChanged` per section** (`plugin/auto-emptylist`, `plugin/auto-data`)—outside the default agent scope but required for MVP.

5. **Misaligned upstream issue (#1).** Tracking `MusicFetcherImpl` / IPC as a fork task implied Cursor could ship production data layer work without icefields—**AI misalignment** (see closed issue on upstream).

### Software engineering mistakes

- **Closed PRs without merge** left good ideas (dedupe, lazy load) unintegrated while `plugin/auto-main` moved on.
- **Default branch drift:** `cursor-cloud/dev-main-4dc1` stagnated; real integration happened on `plugin/auto-main`.
- **Symptom fixes before reproduction:** e.g. dedupe browse rows before confirming whether duplicates come from host JSON or client cache.

### Kotlin / Android Auto mistakes

- **Blocking browse with `withTimeoutOrNull` in `getChildren`** (lazy-section branch) risks ANR/timeouts on the MediaLibrary thread; prefer **push model**: host fills flows → service **notifies** section IDs (what icefields shipped).
- **`onPlaybackResumption` returning index 0** ignores “resume where user left off” and session state.
- **Auto-connect side effect:** waking PA2 host from `onConnect` on every controller—racey, untestable in JVM, wrong lifecycle.
- **Queue scroll bug (#3):** not addressed on these branches; fix path is **diff-guard `syncPlayerFromHostQueue` / `replaceMediaItem`** and try `MediaLibrarySession.Builder.setPeriodicPositionUpdateEnabled(false)` per [androidx/media#2192](https://github.com/androidx/media/issues/2192)—not dedupe or overlay UI.
- **Launcher contract:** replacing `MainActivity` with a “drive safe” overlay breaks the documented “open host + finish” flow and AGENTS **out-of-scope** rules for routine AA work.

---

## Per-branch retrospectives (vs `plugin/auto-main`)

### `feature-cursor/auto-browse-dedupe` (PR #19)

**Diff vs `plugin/auto-main`:** +105 lines in `Pa2MediaLibraryService.kt` + tests — `dedupeAlbumsByStableIdPreserveOrder`, `dedupePlaylists…`, `dedupeSongs…` applied to section snapshot and cache.

**What `plugin/auto-main` does instead:** No client-side dedupe; icefields fixed **empty/stale browse** via per-section `notifyChildrenChanged` and host/version bumps—not by deduplicating lists in the UI layer.

**Lessons:**

| Area | Lesson |
|------|--------|
| **SWE** | If duplicates appear in Auto, **log host JSON** and `MusicFetcher` emissions first; dedupe in `app/` hides data bugs. |
| **Android** | Stable `MediaIds` matter; dedupe by `id` is correct *only after* IDs are trustworthy from IPC. |
| **Cursor** | Agent added unit tests for pure functions—good—but branch never merged; **integration beats local helpers**. |

---

### `feature-cursor/auto-lazy-section-loading` (PR #18)

**Diff vs `plugin/auto-main`:** Replaced immediate `getChildren` reads with `sectionChildrenFuture` + `requestAndReadCachedBrowseItems` (timeout wait on `StateFlow`).

**What `plugin/auto-main` does instead:** Separate coroutines per section call `notifyChildrenChanged` when each flow gets data (`subscribeToLibraryChanges`)—**push refresh**, no blocking wait in `getChildren`.

**Lessons:**

| Area | Lesson |
|------|--------|
| **Android Auto** | Browse should return quickly; [content hierarchy](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy) expects async **notification**, not long `ListenableFuture` waits. |
| **Kotlin** | `filter { it.isEmpty() }.first()` inside browse callbacks couples UI thread to host latency. |
| **Cursor** | Agent treated “empty section” as “need to wait in getChildren”; icefields treated it as “notify when data arrives”. **icefields’ model matches Media3 docs.** |

---

### `feature-cursor/auto-debug-host-wake` (PR #17)

**Diff vs `plugin/auto-main`:** `ensurePowerAmpache2HostStartedNonBlocking()` on **every** `onConnect` for automotive controllers; removed Toast path.

**What `plugin/auto-main` does instead:** Host wake still tied to browse/root flows elsewhere; no unconditional connect hook in the diff baseline.

**Lessons:**

| Area | Lesson |
|------|--------|
| **Android** | Starting another app from `MediaSession` connect is a **side effect**—hard to reason about, breaks when multiple controllers connect. |
| **SWE** | Debug vs release package order belongs in **`Utils.kt` / manifest**, verified once—not per-session wake. |
| **Cursor** | “Prefer debug host” is a dev ergonomics idea, not a production Auto requirement. |

---

### `feature-cursor/auto-playback-resumption` (PR #20)

**Diff vs `plugin/auto-main`:** Implemented `onPlaybackResumption` returning **entire deduped queue** with **`startIndex = 0`**.

**What `plugin/auto-main` does instead:** Relies on queue mirroring (`subscribeToHostQueueMirror` / `syncPlayerFromHostQueue`) and normal play paths—not this callback shape.

**Lessons:**

| Area | Lesson |
|------|--------|
| **Android Auto** | [Enable playback](https://developer.android.com/training/cars/media/enable-playback): resumption should restore **last media item and queue position**, not always track 0. |
| **Kotlin/Media3** | `MediaItemsWithStartPosition` must align with `Player` state and `setActiveQueueItemId`. |
| **Cursor** | Agent filled an API stub to “implement resumption” without DHU validation—likely wrong behavior. |

---

### `feature-cursor/drive-safe-overlay` (PR #21)

**Diff vs `plugin/auto-main`:** Replaced `SongListScreen` in `MainActivity` with full-screen Compose “Drive safe” card + `launchPowerAmpache2`.

**What `plugin/auto-main` does instead:** Keeps launcher/testing surface; AA work stays in `Pa2MediaLibraryService`.

**Lessons:**

| Area | Lesson |
|------|--------|
| **SWE** | **Distraction UX** for cars is governed by [Android app quality for cars](https://developer.android.com/docs/quality/car-app-quality)—not a custom full-screen plugin launcher. |
| **AGENTS scope** | `MainActivity` launcher contract is **out of bounds** for routine AA tasks unless maintainer expands scope. |
| **Cursor** | Agent conflated “car safety messaging” with plugin MVP; icefields never asked for this. |

---

### `cursor-cloud/bug-fix-tests-b1c3` (PR #16, optional)

**Diff vs `plugin/auto-main`:** Mostly **already ancestral** to `plugin/auto-main`; branch added orchestrator scripts, slice logcat tooling, extended `AGENTS.md`, and `Pa2MediaLibraryServiceBugTests` (bugs 1–7).

**What `plugin/auto-main` retains:** Bug-test file and many service fixes; orchestrator prose may differ.

**Lessons:**

| Area | Lesson |
|------|--------|
| **Cursor** | **Orchestrator loops** (Composer workers, Project #7 cards) multiply commits without DHU proof—good for process docs, dangerous as substitute for maintainer integration. |
| **SWE** | Tests named `bugN_*` are excellent **specs**; fixes must land on **`plugin/auto-main`** in small PRs with icefields, not sit on closed PR #16. |
| **Kotlin** | Bug 1 (Hilt `lateinit` in property initializers) is a real Android footgun—worth keeping tests; fix is **move flows to `onCreate` after injection**. |

**Bug test cheat sheet (still relevant on `plugin/auto-main`):**

| Bug | Problem | Correct direction |
|-----|---------|-------------------|
| 1 | Use cases read before `@Inject` | Initialize flows after Hilt `onCreate` |
| 2 | `combine(emptyList())` crashes | Guard empty combine |
| 3 | IPC requests dropped if `clientMessenger` null | Queue/retry until bound |
| 4 | Browse timeout too short for car | Tune `FETCH_TIMEOUT_MS` with DHU evidence |
| 5 | N parallel playlist song requests | Batch/throttle host IPC |
| 6 | Empty browse before host push | Per-section notify (icefields) |
| 7 | Five `notifyChildrenChanged(ROOT)` | One root notify per batch—or per-section only |

---

### `cursor-cloud/shahzebqazi-PluginAndroidAuto-docs-5244` (PR #13)

**Diff vs `plugin/auto-main`:** +4 lines in `README.md` (minimal branch-request blurb).

**What `plugin/auto-main` has:** Full portfolio README (hero, IPC diagram, UX links)—**supersedes** this branch entirely.

**Lesson (Cursor):** Do not open doc-only PRs on stale base; **rebase on `plugin/auto-main`** or edit README in one commit there.

---

### `cursor-cloud/env-setup-c015` (PR #14)

**Diff vs `plugin/auto-main`:** +18 lines in `AGENTS.md` (Cursor Cloud JDK/SDK notes).

**What `plugin/auto-main` has:** Longer `AGENTS.md` with build env, DHU scripts, upstream policy—partial overlap.

**Lesson:** Environment docs belong in **`AGENTS.md` on the integration branch**, not a dangling cloud-only branch.

---

### `cursor-cloud/archive-main-v1.0.0-4dc1`

**Diff vs `plugin/auto-main`:** **~282 files**, +MkDocs, GitHub Pages workflow, `mockups/web-mockup`, research docs—**massive divergence** from current Kotlin tree.

**Lesson (SWE + Cursor):** **Archive branches are not integration branches.** Treat as tag/snapshot only; never compare feature work against them. MVP lived on `plugin/auto-*`.

---

### `cursor-cloud/tech-lead-android-auto-media3-5410`

**Diff vs `plugin/auto-main`:** Early `AGENTS.md` / handoff (~2026-04-10).

**Lesson:** First agent pass established rules later **contradicted by history** (e.g. reverted IPC batch on `dev-main`). **Re-read `AGENTS.md` on `plugin/auto-main` only**; retire early agent branches.

---

### `cursor-cloud/ux-ui-car-plugin-ux-research-5a36` + `mockups`

**Diff vs `plugin/auto-main`:** UX research markdown under `docs/ux-research/`; `mockups` branch adds web mockup toolchain—not in lean `plugin/auto-main` MVP.

**Lesson:** UX research is valuable **product input** but **not shipping code** for icefields’ Auto MVP. Keep research on `mockups` or `docs/` only if maintainer wants it; don’t block Kotlin integration.

---

## What icefields did right (contrast)

Use this as the correction vector—not the abandoned branches:

| Problem you hit | icefields / `plugin/auto-main` approach |
|-----------------|----------------------------------------|
| Empty browse sections | `plugin/auto-emptylist`: notify **per section** when data arrives |
| Version / release alignment | `plugin/auto-data`: version bump on integration path |
| Host ordering / IPC | `plugin/auto-fixes`, `bugfix/auto-no_playback` |
| Upstream MVP | Merge `plugin/auto-main` → `icefields/plugin/auto` (PR #2) |

**Your role on the fork:** `app/` Media3, DHU evidence, issue #3 queue scroll—**after** confirming host pushes in logcat, not parallel unfixed `feature-cursor/*` branches.

---

## Recommended habits going forward

1. **One integration branch:** `plugin/auto-main` (or maintainer-named). Feature branches live **hours/days**, then merge or delete.
2. **Before Cursor edits `Pa2MediaLibraryService`:** `adb logcat` + grep `MusicFetcher` / `PA2DataFetch`—prove host sent `recent_albums`, `favourite_albums`, etc.
3. **PR checklist:** `./gradlew :app:assembleDebug` + **DHU screenshot or slice** + “not verified on car” if missing.
4. **Do not touch `data/`/`domain/`** without icefields approval (see closed upstream issue #1).
5. **Queue scroll (#3):** diff-guard queue mirror; try `setPeriodicPositionUpdateEnabled(false)`; read [androidx/media#2192](https://github.com/androidx/media/issues/2192).
6. **Default branch:** Point GitHub default at **`plugin/auto-main`**, not stale `cursor-cloud/dev-main-4dc1`.

---

## How this branch was created

```bash
git checkout plugin/auto-main
git checkout -b cursor-cloud/history-squashed
# this file only — no code from squashed branches
git commit -m "cursor-cloud/history-squashed: Squash abandoned branch lessons (docs only)"
```

To inspect what an old branch *would* have changed (locally, before delete):

```bash
git fetch origin
git diff origin/plugin/auto-main...origin/<old-branch-name>
```

Old branch tips are listed in git reflog on any clone that fetched before deletion.
