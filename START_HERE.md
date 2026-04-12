# Start here

## Repository

- **Upstream template:** `icefields/PowerAmpache2PluginTemplate` — branch **`main`** tracks **`upstream/main`** only (no feature work there).
- **This fork:** development happens on **`cursor-cloud/dev-main-4dc1`** and topic branches under **`cursor-cloud/`**.
- **Modules:** `domain`, `data`, `app`, `PowerAmpache2Theme` — respect Clean Architecture boundaries unless scope expands.

## Agents

- **Branch policy, commit message format, and module notes:** see [`AGENTS.md`](AGENTS.md).
- **Commit subjects** on dev branches: `<branch-name>: <short imperative summary>` (use the full current branch name from `git branch --show-current`).

## Handoff for an AI coding agent

Use this section as **onboarding context** when picking up work in a new session. It does not replace [`AGENTS.md`](AGENTS.md) or the task-specific briefs below.

### Identity and scope

- **Upstream:** `icefields/PowerAmpache2PluginTemplate` — **`main`** mirrors **`upstream/main`** only; no feature commits there.
- **This fork:** integrate on **`cursor-cloud/dev-main-4dc1`**; feature work on **`cursor-cloud/<topic>`** branches.
- **Architecture:** `domain`, `data`, `app`, `PowerAmpache2Theme` — **do not** change `domain/` or `data/` (including `PA2DataFetchService`) or **`MainActivity`’s launcher flow** unless the task explicitly expands scope. Media / Android Auto work stays in **`app/`** (see Media3 brief below).

### Build environment (what to expect)

- **`local.properties`** must define `sdk.dir=...` (file is gitignored). Align with the human’s Android Studio SDK when possible (e.g. `~/Android/Sdk`).
- **JDK / Gradle:** The **Gradle Kotlin DSL** may fail if the **daemon runs on JDK 26+** (e.g. `IllegalArgumentException` parsing the Java version). This repo’s **`gradlew`** prefers a **project-local JDK 21** under **`.jdks/jdk-21*`** when present; **`.jdks/`** is gitignored. If no `.jdks` exists, use **JDK 17 or 21** for Gradle (`JAVA_HOME` or system packages). Do not claim `./gradlew` works on JDK 26 without verifying.
- **Author / debug APKs:** Root-level **`*.apk`** is gitignored. Installing a **debug** build over an **author-signed release** may require **`adb uninstall`** first due to **signature mismatch**; **versionCode** on device may also block install without uninstall or downgrade flags.

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
