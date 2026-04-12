# Start here

## Repository

- **Upstream template:** `icefields/PowerAmpache2PluginTemplate` — branch **`main`** tracks **`upstream/main`** only (no feature work there).
- **This fork:** development happens on **`cursor-cloud/dev-main-4dc1`** and topic branches under **`cursor-cloud/`**.
- **Modules:** `domain`, `data`, `app`, `PowerAmpache2Theme` — respect Clean Architecture boundaries unless scope expands.

## Agents

- **Branch policy, commit message format, and module notes:** see [`AGENTS.md`](AGENTS.md).
- **Commit subjects** on dev branches: `<branch-name>: <short imperative summary>` (use the full current branch name from `git branch --show-current`).

## Perpetual coding agents

Autonomous agents should **not** block on Kanban or a “Proceed?” gate unless the task explicitly requires it. Use **sensible defaults** when product links or UI details are missing; record assumptions in the PR.

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

10. User runs the app on the phone (or DHU / car); confirms browse UI and playback behavior; shares screenshots or screen recording if reporting issues.

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
   - Agents **cannot** see the phone screen unless the **human** provides artifacts: screenshots, screen recording, or exported DHU logs.  
   - Ask for: app version / branch, steps taken, and **one logcat snippet** around the failure time when reporting UI or playback bugs.

5. **Foreground service & notifications**  
   - On Android 13+, notification permission may affect FGS behavior; include that in repro steps if playback or service start fails after install.
