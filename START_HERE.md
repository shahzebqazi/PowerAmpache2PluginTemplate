# Start here

## Repository

- **Upstream template:** `icefields/PowerAmpache2PluginTemplate` — branch **`main`** tracks **`upstream/main`** only (no feature work there).
- **This fork:** development happens on **`cursor-cloud/dev-main-4dc1`** and topic branches under **`cursor-cloud/`**.
- **Modules:** `domain`, `data`, `app`, `PowerAmpache2Theme` — respect Clean Architecture boundaries; treat **`domain/`** and **`data/`** as **maintainer-only** to change unless a task explicitly says otherwise (see **Handoff** below).

## Contributing upstream (`PluginAndroidAuto`)

Use this when preparing a **one-off** pull request to **`icefields/PowerAmpache2PluginTemplate`** with Android Auto–related (or agreed) changes. Policy for this fork:

| Topic | Policy |
| --- | --- |
| **Upstream branch `PluginAndroidAuto`** | **Do not assume** it exists. Confirm with the **upstream maintainer** whether to open against **`PluginAndroidAuto`**, **`main`**, or another base before you rebase. |
| **Longevity** | Treat upstream AA integration as a **one-off PR target** (not a standing “always merge here” branch) unless the maintainer says otherwise. |
| **Contributor branch naming** | Prefer a **topic branch that includes the contributor’s name** so the PR is easy to attribute (e.g. `cursor-cloud/shahzebqazi-PluginAndroidAuto-<topic>-5244`). |
| **`domain/` / `data/`** | **No** changes unless **explicitly approved by the developer** for that contribution. Default upstream scope: **`app/`** only, matching this repo’s handoff. |
| **Host / IPC** | **Confirmed:** the plugin depends on the Power Ampache 2 host binding **`PA2DataFetchService`**. **Known bugs** exist and will be worked on **before release** — state what you verified in the PR. |
| **CI & repo rules** | Match **upstream CI** and read whatever **contributing / CLA / DCO** docs exist on the upstream repo at PR time (upstream **`README`** may be minimal; follow **GitHub Actions** and any `CONTRIBUTING` file). |

### Before you open a pull request upstream

1. **Confirm base branch** with the maintainer (`PluginAndroidAuto` vs `main` vs other).
2. **`upstream` remote** — Add `icefields/PowerAmpache2PluginTemplate` as **`upstream`** if missing; run **`git fetch upstream`** before rebasing.
3. **Minimal commits** — Prefer a **small branch off the agreed upstream base** with cherry-picks or focused commits, not an unreviewed merge of this fork’s integration branch unless asked.
4. **Build & tests** — Run **`./gradlew :app:assembleDebug`** (and tests the maintainer expects) on the **upstream-based** branch; fix **upstream CI** failures before requesting review.
5. **PR body** — List files under **`app/`** vs **`domain/`** / **`data/`**; if you touch shared layers, cite **developer approval**.

### Suggested git shape (illustrative)

```bash
git fetch upstream
# If PluginAndroidAuto exists and maintainer agreed:
git checkout -b cursor-cloud/shahzebqazi-PluginAndroidAuto-mychange-5244 upstream/PluginAndroidAuto
# If it does not exist yet, use the base the maintainer names (often main):
# git checkout -b cursor-cloud/shahzebqazi-PluginAndroidAuto-mychange-5244 upstream/main
# Apply commits, then:
git push -u origin cursor-cloud/shahzebqazi-PluginAndroidAuto-mychange-5244
```

Open the PR from your fork against **`icefields/PowerAmpache2PluginTemplate`** with the **base branch the maintainer confirmed**.

## Agents

- **Branch policy, commit message format, and module notes:** see [`AGENTS.md`](AGENTS.md).
- **Commit subjects** on dev branches: `<branch-name>: <short imperative summary>` (use the full current branch name from `git branch --show-current`).

## Handoff for an AI coding agent

Use this section as **onboarding context** when picking up work in a new session. It does not replace [`AGENTS.md`](AGENTS.md) or the task-specific briefs below.

### Identity and scope

- **Upstream:** `icefields/PowerAmpache2PluginTemplate` — **`main`** mirrors **`upstream/main`** only; no feature commits there.
- **This fork:** integrate on **`cursor-cloud/dev-main-4dc1`**; feature work on **`cursor-cloud/<topic>`** branches.
- **This is a plugin:** the **Power Ampache 2 host** owns real network access, auth, and the JSON protocol over Messenger. The plugin’s `domain/` + `data/` layers are **shared contracts and glue** (`MusicFetcher`, `PA2DataFetchService`, DTOs). They are **not** a place to invent “IPC hardening,” “trusted sender” policies, or host-protocol changes unless the **maintainer** explicitly assigns that work with a real spec from the host app.
- **Architecture:** `domain`, `data`, `app`, `PowerAmpache2Theme` — **do not** change `domain/` or `data/` (including `PA2DataFetchService`, **`MusicFetcher` / `MusicFetcherImpl`**, or **DTOs**) or **`MainActivity`’s launcher flow** for routine Android Auto / Media3 / UI tasks. Put those fixes in **`app/`** only. The **only** exception is when the **maintainer** clearly expands scope (e.g. a coordinated host + plugin release); vague “empty browse” or “DTO key” agent commits are not sufficient justification.
- **History note:** On **`cursor-cloud/dev-main-4dc1`**, a batch of Cursor commits (subjects mentioned “IPC,” “DTO keys,” “harden,” `START_STICKY`, etc., ca. 2026-04-12) was **reverted**. Those messages described work that does **not** match how this plugin should evolve: treat that narrative as **invalid** and do not reintroduce the same kind of edits without maintainer direction.
- **Media / Android Auto:** implement browse, session, and queue UI in **`app/`** only (see Media3 brief below); do not use empty lists or car UI bugs as a reason to edit **`domain/`** / **`data/`** without maintainer scope.

### Build environment (what to expect)

- **`local.properties`** must define `sdk.dir=...` (file is gitignored). Align with the human’s Android Studio SDK when possible (e.g. `~/Android/Sdk`).
- **JDK / Gradle:** The **Gradle Kotlin DSL** may fail if the **daemon runs on JDK 26+** (e.g. `IllegalArgumentException` parsing the Java version). This repo’s **`gradlew`** prefers a **project-local JDK 21** under **`.jdks/jdk-21*`** when present; **`.jdks/`** is gitignored. If no `.jdks` exists, use **JDK 17 or 21** for Gradle (`JAVA_HOME` or system packages). Do not claim `./gradlew` works on JDK 26 without verifying.
- **Author / debug APKs:** Root-level **`*.apk`** is gitignored. Installing a **debug** build over an **author-signed release** may require **`adb uninstall`** first due to **signature mismatch**; **versionCode** on device may also block install without uninstall or downgrade flags.

### Known issues and startup (field status — update as bugs are fixed)

This section records **observed** behavior so agents and humans share the same expectations. It is not a substitute for logcat on your device.

**Startup reliability**

- The plugin **does load**, but **startup is not yet fully predictable**: several components can start or rely on **`PA2DataFetchService`** (`PluginApplication.onCreate`, `Pa2MediaLibraryService.onCreate`, and the host binding via **`register_client`**). It is easy to wonder whether a **previous process**, **task affinity**, or **order of host vs plugin** is affecting what you see.
- **What to do:** When debugging, capture **one cold start** after `adb shell am force-stop luci.sixsixsix.powerampache2.plugin` (and reproduce host order: open host first vs Auto first). Use log lines from **`PA2DataFetch`**, **`MusicFetcherImpl`**, and **`Pa2MediaLibraryService`** to see whether `clientMessenger` is registered and flows receive JSON.
- **Intent:** Future work should make **one clear initialization path** (documented order, optional single coordinator in `app/`) without breaking [`AGENTS.md`](AGENTS.md) constraints unless the **maintainer** expands scope.

**Android Auto: USB works vs “For You” / widgets**

- **USB Android Auto** can show the plugin and **browse** via **`Pa2MediaLibraryService`** (library root → sections → playlists/albums → songs).
- **Google’s “For You” (or similar recommendation / home-row widgets)** may **not** use the same APIs as the **Media** browser. This template implements **`MediaLibraryService` + browse tree**, not a full **Android Auto app-level “For You”** integration. If that surface does not load or play, it may be **out of scope** for the current codebase or require **additional** Media3 / OEM integration — verify whether the issue is **Media tab browse** vs **For You** before filing code against the wrong surface.
- **Now Playing / “currently playing” on the plugin** depends on the host pushing **`MusicFetcher.currentQueueFlow`** and the media session mirroring that queue into ExoPlayer. If metadata stays stale, check host IPC and **`syncPlayerFromHostQueue`** behavior (host updates while playback is active).

**Plugin UI (“no tracks”)**

- **`MainActivity`** is designed to **launch the Power Ampache 2 host** and **`finish()`** immediately; the **Compose sample UI** (`testContent()` / `SongListScreen`) is **commented out** by default. So **the launcher does not show a track list** — that is **intentional** unless you enable the test UI or expand scope.
- If you need an **in-app** track list for QA, uncomment **`testContent()`** in `MainActivity` or add a **debug-only** activity; document any change in the PR.

**Responsive / reactive gaps**

- Surfaces that should react to **library changes** use **`MusicFetcher` `StateFlow`s** and **`notifyChildrenChanged`** from **`Pa2MediaLibraryService`**. Gaps usually mean **no host data** (empty flows), **timeouts** on drill-down (see `FETCH_TIMEOUT_MS`), or **host not sending** the relevant JSON actions — not a missing Compose `LaunchedEffect` in the default launcher (there is no visible UI).

### Android Auto / Media3 (current direction)

- Browse + playback for AA are implemented via **`Pa2MediaLibraryService`** (Media3), **`MediaIds.kt`**, and app-only dependencies in **`app/build.gradle.kts`** / **`gradle/libs.versions.toml`** (e.g. `media3-exoplayer`, `media3-session`, **`androidx.concurrent:concurrent-futures`** for `CallbackToFutureAdapter`). Wire **only** through **`MusicFetcher`** and existing flows; host must still bind **`PA2DataFetchService`** for real data.
- **Desktop Head Unit (DHU):** Install **`extras;google;auto`** with `sdkmanager` (Android Auto Desktop Head Unit Emulator). **Recommended (DHU 2.x):** USB **Accessory (AOA)** — **`scripts/run-dhu-usb.sh`** (with **`ANDROID_SERIAL`** or a serial argument if **multiple** `adb` devices are listed). The script sets **`SDL_VIDEODRIVER=x11`** on Wayland (DHU uses SDL; without this, the window often **closes immediately**). **Detached from Cursor/IDE:** **`DHU_BACKGROUND=1 ./scripts/run-dhu-usb.sh`** — on Linux with systemd, DHU is started under **`systemd-run --user --scope`** so it **survives** when the IDE closes the terminal (plain **`nohup … &`** is not enough). Requires **`libc++`** on Linux (`pacman -S libc++`). See [Google: Connect using Accessory Mode](https://developer.android.com/training/cars/testing/dhu#connection-aoap). **Fallback:** **`scripts/run-dhu-adb-tunnel.sh`** after enabling Android Auto **Developer** → **Start head unit server** and `adb forward tcp:5277 tcp:5277` (older ADB-tunnel flow). **Agents:** log lines such as “Attached!” are **not** proof the DHU UI stayed open; only the human can confirm.
- **Visual verification gap (important):** The **Android Auto / DHU (and related) UI has not been visually inspected** as part of this repository’s documented workflow. Automated checks (e.g. instrumented **`MediaBrowser`** tests against **`Pa2MediaLibraryService`**) validate the **media browse API**, not pixels on the head unit. Treat car/DHU behavior as **unproven** until a human has seen it.

### Verification habits

- Prefer **`./gradlew :app:assembleDebug`** when an SDK is available; use **`adb`** / **`adb logcat`** when a device is attached. Do not assert success without command output.
- **Log filters** (adjust as needed): `Pa2Media`, `MediaLibrary`, `ExoPlayer`, `MediaSession`, package `luci.sixsixsix.powerampache2.plugin`.
- **Kanban / GitHub Projects:** Agents cannot create project cards from the repo; if the human links a card/issue URL, reference it in the PR; otherwise **do not block** on missing board state (see “Perpetual coding agents” below).

### Deliverables

- Follow commit format in [`AGENTS.md`](AGENTS.md).
- Document stable **media ID** changes and **constraints** (what you did / did not touch) in the PR body.
- For Android Auto / DHU-facing work: **perpetual coding agents** must **debug on-device**, **deliver screenshots** of **working** features (browse sections, drill-down, playback where applicable), and note gaps; the **human reviews the code and UI** once implementation is ready (see below).

## Perpetual coding agents

Autonomous agents should **not** block on Kanban or a “Proceed?” gate unless the task explicitly requires it. Use **sensible defaults** when product links or UI details are missing; record assumptions in the PR.

### Android Auto / DHU — obligations for perpetual coding agents

Until the maintainer has **visually** confirmed behavior on DHU or a car:

1. **Do not assume** the head-unit UI works because **`./gradlew` or instrumented tests pass** — the **UI has not been visually inspected** in-repo; treat DHU as the source of truth for “does it look right.”
2. **Debug on real hardware** where possible: USB phone + **`scripts/run-dhu-usb.sh`** (or equivalent), **`adb logcat`** with the filters in **Verification habits**, fix crashes / empty trees / stuck loading with evidence.
3. **Deliver screenshots** (or short screen recordings) of **working** features in the PR or linked issue: e.g. launcher → **Auto Plugin for Power Ampache 2**, root browse, at least one drill-down, playback if URLs exist. If something cannot be shown (no host, no network), **state why** explicitly.
4. **Human sign-off:** The **human inspects the code** (and DHU/car if they choose) **after** the agent considers the work implemented. Agents should summarize what was automated vs. what still needs human eyes.

### Android Auto–visible MVP (Media3) — agent brief

Use this as the task spec when implementing a minimal car/host-visible browse and play flow.

**Mission:** Deliver a minimal **Android Auto–visible** experience using **Media3** (typically `MediaLibraryService` + `MediaSession` / `MediaBrowser`) so the car/host UI can **browse and play** content.

**Architecture rules (non-negotiable):**

- Wire only through existing **app-layer, domain-facing APIs** (e.g. `MusicFetcher` / use cases exposing `Flow` / `StateFlow`): playlists (list), favourite albums, recent albums, **newest/latest albums** (`LatestAlbumsStateFlow` / `MusicFetcher.latestAlbumsFlow` matches “newest” here), highest-rated albums, `getSongsFromPlaylist`, `getSongsFromAlbum`.
- **Collect/subscribe** so exposed lists stay fresh.
- **Do not** change `MainActivity`’s launcher flow.
- **Do not** touch **`domain/`** or **`data/`** (including `PA2DataFetchService`).

**Dependencies:** Prefer **Media3** (and **ExoPlayer** if needed) **only in `app/build.gradle.kts`** — no domain/data changes.

**Kanban / tracking:** `AGENTS.md` does not name a GitHub Project. If the task includes a card/issue URL, link it in the PR; otherwise proceed and use the PR as the record.

**Defaults** (override only if the task says otherwise):

| Topic | Default |
|--------|--------|
| Newest | `LatestAlbumsStateFlow` / `MusicFetcher.latestAlbumsFlow` — no new domain/data shims |
| Browse tree | Root → sections (Playlists, Favourite albums, Recent, Newest, Highest-rated) → items → songs |
| Media3 IDs | Propose a stable scheme (e.g. `root`, `section/...`, `playlist/{id}`, `album/{id}`) and document it in the PR |
| Playback | MVP = browse + queue/play via `MediaSession` with ExoPlayer in the service when URIs exist; fail gracefully without touching domain/data |
| Branch | Topic branch off `cursor-cloud/dev-main-4dc1`, e.g. `cursor-cloud/android-auto-media3-mvp-e77e` (or another `cursor-cloud/...-e77e` name if specified) |

**Git / delivery:** Commit and push on the topic branch; open or update a **draft PR** to **`cursor-cloud/dev-main-4dc1`** with constraints, ID scheme, and files touched.

---

### Android Auto Media3 MVP — work completed (summary)

The following landed on topic branch **`cursor-cloud/android-auto-media3-mvp-bb4d`** (see PR linked from that branch / merge history):

- **Media3** (`media3-exoplayer`, `media3-session`) added **only** in the app module (`app/build.gradle.kts`, `gradle/libs.versions.toml`).
- **`Pa2MediaLibraryService`** — `MediaLibraryService` + `MediaLibrarySession` + ExoPlayer; browse tree wired exclusively through **`MusicFetcher`** (sections: Playlists, Favourite / Recent / Newest / Highest-rated albums; drill-down via `getSongsFromPlaylist` / `getSongsFromAlbum` with flow updates and `notifyChildrenChanged`).
- **Stable media IDs** documented in `app/.../auto/MediaIds.kt` (`root`, `section/...`, `playlist/{id}`, `album/{id}`, `song/{id}`).
- **Manifest:** exported service, FGS / notification permissions as required, `automotive_app_desc.xml` for Android for Cars, Media3 + legacy `MediaBrowserService` intent actions.
- **Constraints preserved:** no changes to `MainActivity` launcher flow, `domain/`, `data/`, or `PA2DataFetchService`.

**Reminder:** Full library data still depends on the host app binding to **`PA2DataFetchService`** so `MusicFetcher` listener callbacks run; the media service does not replace that listener.

---

### Next step — project kanban and perpetual agents

**Kanban:** The human maintains the board (e.g. [Project #7](https://github.com/users/shahzebqazi/projects/7)). Suggested cards for the **next phase**: (1) **Startup / process** — document ordering of `PA2DataFetchService` + host `register_client`; (2) **Android Auto** — verify Media browse vs “For You” scope; fix **Now Playing** / queue mirror with host in **`app/`**; (3) **Empty library / timeout** — UX when host is slow or offline; (4) **Optional debug UI** — safe way to show tracks without changing the default launcher contract.

**Perpetual coding agents** should loop: reproduce → fix in **`app/`** → `./gradlew :app:assembleDebug` → device logcat → PR with evidence → repeat until behavior is **functional, operational, and intentional**. **`domain/`** / **`data/`** are **out of bounds** unless the **maintainer** assigns a coordinated host+plugin change.

### Kanban: Android Auto guidelines alignment (Project #7)

Agents **cannot** create or update GitHub Project cards from the repository without **GitHub CLI** credentials that include **`project`** and **`read:project`** scopes. **Maintainer:** after `gh auth refresh -h github.com -s read:project -s project`, run **`./scripts/create-project-7-android-auto-cards.sh`** to create **draft** items on [Project #7](https://github.com/users/shahzebqazi/projects/7), or add the cards below manually. **Agents:** when a card or linked issue URL is assigned, follow **Instructions for AI agents — Android Auto guideline alignment** below.

#### Copy-paste card titles and descriptions

**Card 1 — Title:** `Android Auto: Honor root hints (≤4 root tabs)`

**Card 1 — Description:**

```text
Goal: Align browse root with Google’s Android for Cars guidance: root hints typically limit top-level tabs to four (MediaConstants.BROWSER_ROOT_HINTS_KEY_ROOT_CHILDREN_LIMIT).

Context: Pa2MediaLibraryService exposes five section nodes under root (playlists + four album sections). Some head units may drop or bury the fifth tab.

Tasks:
- Read: developer.android.com/training/cars/media/create-media-browser/content-hierarchy (“Structure the root menu”).
- In Media3, read LibraryParams / root-equivalent hints in onGetLibraryRoot or onGetChildren for parent root, and reshape the tree (e.g. nest two sections under one browsable “Albums” node, or merge categories) so default AA shows ≤4 top-level browsable children when the hint says 4.
- Update instrumented tests (root child count / IDs) and document the new hierarchy in the PR.

Out of scope unless agreed: changing domain/data or MainActivity launcher contract.
```

**Card 2 — Title:** `Android Auto: Artwork on browse and playback items`

**Card 2 — Description:**

```text
Goal: Populate MediaMetadata artwork (artworkUri) for songs/albums where the domain model already provides URLs (e.g. Song.imageUrl), per Google media design guidance for in-car UI.

Tasks:
- Read: “Display media artwork” / browsing views in Android for Cars media docs and Design for Driving media checklist.
- In app/…/auto/Pa2MediaLibraryService (and helpers): set artwork on playable and browsable items when URLs are non-empty; handle invalid URIs gracefully.
- Verify assembleDebug; extend or add tests if metadata assertions are practical.

Scope: app/ only unless image URLs require data-layer fixes (then negotiate scope).
```

**Card 3 — Title:** `Android Auto: Play-from-search and MEDIA_PLAY_FROM_SEARCH`

**Card 3 — Description:**

```text
Goal: Either implement play-from-search / voice search behavior expected for the manifest intent android.media.action.MEDIA_PLAY_FROM_SEARCH, or remove the intent filter if unsupported.

Tasks:
- Read: developer.android.com training/cars/media — voice actions, search; Media3 MediaLibrarySession.Callback for search/play-from-search APIs as applicable.
- Implement resolution of search queries into playable media, or document why the plugin defers to the host and adjust manifest accordingly.
- PR must state Assistant / AA test status (or “not tested on DHU” with logcat evidence for what was tested).

Scope: app/ preferred.
```

**Card 4 — Title:** `Android Auto: Large browse lists and distraction limits`

**Card 4 — Description:**

```text
Goal: Mitigate strict per-level item limits in Android Auto / AAOS when playlists or album track lists are very large (docs: “strict limits” on items per menu level).

Tasks:
- Read: content-hierarchy + browsing views design pages.
- Design chunked browsing or subfolders (e.g. “Page 2”, alphabetical buckets) without breaking MediaIds stability for existing clients if possible; document migration in PR.
- Add a test or documented cap behavior.

Scope: app/ only unless MusicFetcher contract must change.
```

**Card 5 — Title:** `Android Auto: Session activity and branding polish`

**Card 5 — Description:**

```text
Goal: Optional polish: MediaLibrarySession.setSessionActivity to deep-link to an appropriate app screen; ensure service/app icon and theme support car branding (accent / colorPrimary as appropriate).

Tasks:
- Read: Media3 session docs and Google “Provide branding elements” for media apps.
- Implement session activity PendingIntent if product owner agrees on target activity (may be limited for plugin UX).
- Document what was set and what still requires human verification on DHU.

Scope: app/ only.
```

**Card 6 — Title:** `Android Auto: Caller validation (PackageValidator pattern)`

**Card 6 — Description:**

```text
Goal: Evaluate whether Pa2MediaLibraryService should restrict browse/session clients using an allowlist pattern (system apps, Android Auto, Google Assistant packages) while returning non-null root for trusted callers.

Tasks:
- Read: UAMP PackageValidator / developer.android.com media browser “Add package validation”.
- If implemented, document allowed packages and Assistant; do not block onGetRoot with slow I/O; keep behavior safe for Play policy.

Scope: app/ only.
```

#### Instructions for AI agents — Android Auto guideline alignment

When implementing any card above (or a maintainer-supplied umbrella issue):

1. **Review first (cite in PR):** Read the current [Android for Cars media training path](https://developer.android.com/training/cars/media), especially [Content hierarchy](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy) (root hints, pagination notes, caller guidance) and [Design for Driving — media apps](https://developers.google.com/cars/design/create-apps/media-apps/overview) (tabs, branding, voice). Summarize which guidelines drove each code change.
2. **Default scope:** Changes in **`app/`** only (`Pa2MediaLibraryService`, `MediaIds`, manifest, tests, resources). Do **not** modify `domain/`, `data/`, or **`MainActivity`’s launcher flow** unless the card explicitly expands scope or the maintainer agrees.
3. **Branch and commits:** Work on **`cursor-cloud/dev-main-4dc1`** or a **`cursor-cloud/<topic>`** branch. Commit messages: **`<branch-name>: <imperative summary>`** (full branch name), per [`AGENTS.md`](AGENTS.md).
4. **Verify before claiming done:** Run **`./gradlew :app:assembleDebug`**; if browse/session behavior changed, run **`./gradlew :app:connectedDebugAndroidTest`** when a device/emulator is available. Update **`Pa2MediaLibraryInstrumentedTest`** if root structure or IDs change.
5. **PR:** Link the **GitHub issue / Project card URL**; list **automated** vs **human DHU/car** verification; attach logcat snippets and **screenshots** for AA-facing changes when possible (see **Perpetual coding agents** above).
6. **Do not block** on the board: if cards are missing, still implement when asked; note in the PR that the maintainer should add the card to Project #7.

### Next step — suggested GitHub Project board items (USB phone test & debug)

Agents cannot create cards on your GitHub Project from this repo; **add these as issues/cards yourself** (e.g. [Project #7](https://github.com/users/shahzebqazi/projects/7)), then link them in PRs.

**Environment & build**

1. Install Android SDK / set `ANDROID_HOME`; create `local.properties` with `sdk.dir=...` if missing.
2. On branch with Media3 work: run `./gradlew :app:assembleDebug` and fix any compile errors.
3. Document JDK version and AGP/Kotlin versions if CI or teammates hit mismatches.

**USB device**

4. Enable **Developer options** → **USB debugging** on the phone; connect USB; accept RSA fingerprint.
5. Verify `adb devices` shows `device` (not `unauthorized`). If needed: revoke USB debugging authorizations and replug.
6. Install debug APK: `./gradlew :app:installDebug` or `adb install -r app/build/outputs/apk/debug/app-debug.apk`.

**Runtime debugging**

7. Capture logs while exercising the app: `adb logcat | grep -E "Pa2Media|MediaLibrary|ExoPlayer|AndroidRuntime"` (adjust tags as needed).
8. Confirm **`Pa2MediaLibraryService`** starts when Android Auto / media browser connects; note any `SecurityException`, FGS, or binding errors in logcat.
9. With **Power Ampache 2** host installed and bound to **`PA2DataFetchService`**, verify browse lists populate and playback works when `songUrl` is present.

**Visual inspection (human)**

10. **You** run the app on the phone (or DHU / car), confirm browse UI and playback, and **review the code** once agents have implemented fixes. Agents should supply screenshots of working features first; **this doc does not replace** your own inspection of the implementation and head-unit behavior. Share screenshots or a screen recording when reporting issues.

---

### Instructions for AI agents — test, debug, and support visual inspection

When the task involves **verifying the app** or **debugging on a real device**, agents should:

1. **Prefer running builds locally when the environment allows**  
   - If `ANDROID_HOME` / `sdk.dir` is configured, run `./gradlew :app:assembleDebug` (and `:app:installDebug` when `adb` is available).  
   - If the sandbox has **no Android SDK**, state that clearly and list exact commands for the human to run; do not claim the APK was built or installed.

2. **Use ADB when available**  
   - `adb devices` — confirm one authorized device.  
   - `adb install -r` / Gradle `installDebug` — deploy the debug build.  
   - `adb logcat` (optionally filtered by package `luci.sixsixsix.powerampache2.plugin` or tags above) — capture crashes, Media3 session errors, and service lifecycle.

3. **Do not block on Kanban**  
   - If project cards are missing, still implement fixes; note in the PR what was validated and what requires the human’s device.

4. **Visual inspection**  
   - Agents **cannot** see the phone or DHU unless the **human** provides artifacts — so **perpetual agents must** capture and attach their **own** screenshots (or recordings) of **working** Android Auto features when claiming browse/playback work, plus logcat for failures.  
   - When reporting bugs, still ask for: app version / branch, steps taken, and **one logcat snippet** around the failure time.  
   - **Human:** plan to **inspect the code** after implementation; agents document what they validated vs. what you must still verify on DHU/car.

5. **Foreground service & notifications**  
   - On Android 13+, notification permission may affect FGS behavior; include that in repro steps if playback or service start fails after install.
