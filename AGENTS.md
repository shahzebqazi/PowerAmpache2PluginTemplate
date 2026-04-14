# Agents — Power Ampache 2 plugin template

## Branch policy

- **`main`:** Tracks **`upstream/main`** only (`icefields/PowerAmpache2PluginTemplate`). It should be a **fresh pull from upstream** — no feature work, no agent commits, no extra commits on top. Sync with `git fetch upstream` and `git reset --hard upstream/main` (then push to `origin/main` as needed).
- **Development:** All work happens on **`cursor-cloud/dev-main-4dc1`** (or other topic branches). That is the integration branch for this fork.

## Commit messages (required for agents)

On development branches, include the **current Git branch name** at the start of the **subject line** so history stays attributable after rebases, cherry-picks, or fast-forward merges.

**Format:** `<branch-name>: <short imperative summary>`

**Examples:**

- `cursor-cloud/dev-main-4dc1: Wire playlist songs through MusicFetcher`

Use the exact branch from `git branch --show-current` (do not abbreviate). If the subject would be long, keep the branch prefix and shorten the summary; add detail in the body after a blank line.

**Rationale:** After a rebase, commit hashes change; the branch prefix preserves which line of work produced the change.

## Repository

- **Modules:** `domain`, `data`, `app`, `PowerAmpache2Theme` — respect Clean Architecture boundaries unless the product owner expands scope.

## Android Auto — browse tree and search (`Pa2MediaLibraryService`)

- **Library content** (playlists, album sections, tracks) flows through **`MusicFetcher`** only. The **Power Ampache 2 host** must bind to **`PA2DataFetchService`** and push data over the existing Messenger IPC; opening the host (e.g. via `MainActivity`) is often required before browse fills in. See comments in [`app/src/main/java/luci/sixsixsix/powerampache2/plugin/auto/Pa2MediaLibraryService.kt`](app/src/main/java/luci/sixsixsix/powerampache2/plugin/auto/Pa2MediaLibraryService.kt).
- **Library search** (`onSearch` / `onGetSearchResult`) is **not implemented** (product **backlog** / future release). Media3’s defaults would return “not supported” if the search UI were offered. For **Android Auto / Automotive** controllers, **`Pa2MediaLibraryService`** removes **`COMMAND_CODE_LIBRARY_SEARCH`** and **`COMMAND_CODE_LIBRARY_GET_SEARCH_RESULT`** in **`onConnect`** so the head unit does not show a broken search entry point. To ship search later: implement those callbacks, then stop removing those commands (details in KDoc on `Pa2LibraryCallback` in the same file).

## Android Auto / DHU — UI bug loop (human + Cursor)

Use this when the goal is to **inspect the Android Auto UI** (Desktop Head Unit or a car), **report bugs**, and have **Cursor fix code under `app/`** and **automate the reload cycle** on each iteration.

### What the human does

1. **Inspect** the plugin in DHU (or the car): browse, playback, back stack, etc.
2. **Report** the bug in chat: what you expected, what happened, screen or section if relevant. Optional: paste a short `adb logcat` snippet if the agent should confirm a crash or exception.
3. **Wait** for the agent to run the iteration script (below), then **re-test** the same flow.

### What the agent must do after each fix (automate)

Unless the human asked to skip a step, **run the full iteration** from the repo root so the phone and DHU are in a clean state:

```bash
./scripts/dev-aa-ui-iteration.sh
```

If **multiple** devices appear in `adb devices` (USB + wireless), set the **USB serial** so the install and `adb` commands target one device:

```bash
ANDROID_SERIAL=<serial-from-adb-devices> ./scripts/dev-aa-ui-iteration.sh
```

The script, in order:

1. **Stops** any running `desktop-head-unit` (closes old DHU).
2. **Builds and installs** `./gradlew :app:installDebug` (respects `ANDROID_SERIAL` when set).
3. **Force-stops** the plugin package (`luci.sixsixsix.powerampache2.plugin`) so old processes and services are cleared.
4. **Starts** `MainActivity` once (opens the Power Ampache 2 host when installed; the activity may finish immediately — that is expected).
5. **Starts DHU again** in the background (`scripts/run-dhu-usb.sh` with `DHU_BACKGROUND=1`).

**Scope:** implement fixes **only in `app/`** unless the product owner expands scope.

**Options:**

- `INSTALL_ONLY=1` or `NO_DHU=1` — install + reset app; **do not** restart DHU (use when DHU is already fine or only the APK changed).
- `NO_MAIN=1` — skip launching `MainActivity` after force-stop (use if the host is already open and you want to avoid a duplicate launch).

**If you cannot use USB AOAP** (DHU over USB accessory): use the **ADB tunnel** path documented in [`START_HERE.md`](START_HERE.md) (`scripts/run-dhu-adb-tunnel.sh` after “Start head unit server” on the phone). The iteration script is **USB DHU**-oriented; tunnel users may run `installDebug` + force-stop manually and restart DHU with the tunnel script.

### Evidence

- **Build:** `./gradlew :app:installDebug` must succeed.
- **Runtime:** `adb logcat` with filters from [`START_HERE.md`](START_HERE.md) when diagnosing crashes or empty browse trees.

## Product status and agent loop (this fork)

- **Startup and lifecycle** are not fully predictable yet: multiple entry points (`PluginApplication`, `MainActivity`, `Pa2MediaLibraryService`) can start `PA2DataFetchService`, and interaction with **previously running** processes or task stacks can make behavior feel inconsistent until mapped with logcat. See **Known issues and startup** in [`START_HERE.md`](START_HERE.md).
- **Android Auto (USB)** can list the plugin and browse in some flows, but **Google’s “For You” / recommendation-style surfaces** are not the same as this app’s **Media** browse tree (`Pa2MediaLibraryService`). If those widgets do not load or play tracks, distinguish **platform “For You”** (may require separate integration) from **in-app browse** under the plugin’s library root.
- **Perpetual coding agents** should treat **functional Android Auto + reliable host IPC** as the current priority: reproduce on device, fix with evidence (`./gradlew` + `adb logcat`), document what changed, and repeat until the UI is **operational and intentional** — not “should work.”
- **Kanban / GitHub Projects:** Agents cannot create or update project boards from this repo. The human maintains the [project board](https://github.com/users/shahzebqazi/projects/7) (or linked issue URLs); agents reference those links in PRs and use PR descriptions as the execution record. **Android Auto guideline alignment:** copy-paste **Kanban cards** and **agent instructions** live under **“Kanban: Android Auto guidelines alignment (Project #7)”** in [`START_HERE.md`](START_HERE.md).

Onboarding detail, log filters, and **what is intentionally not implemented** (e.g. launcher `MainActivity` UI): see [`START_HERE.md`](START_HERE.md).
