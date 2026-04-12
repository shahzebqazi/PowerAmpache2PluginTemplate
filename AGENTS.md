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

## Product status and agent loop (this fork)

- **Startup and lifecycle** are not fully predictable yet: multiple entry points (`PluginApplication`, `MainActivity`, `Pa2MediaLibraryService`) can start `PA2DataFetchService`, and interaction with **previously running** processes or task stacks can make behavior feel inconsistent until mapped with logcat. See **Known issues and startup** in [`START_HERE.md`](START_HERE.md).
- **Android Auto (USB)** can list the plugin and browse in some flows, but **Google’s “For You” / recommendation-style surfaces** are not the same as this app’s **Media** browse tree (`Pa2MediaLibraryService`). If those widgets do not load or play tracks, distinguish **platform “For You”** (may require separate integration) from **in-app browse** under the plugin’s library root.
- **Perpetual coding agents** should treat **functional Android Auto + reliable host IPC** as the current priority: reproduce on device, fix with evidence (`./gradlew` + `adb logcat`), document what changed, and repeat until the UI is **operational and intentional** — not “should work.”
- **Kanban / GitHub Projects:** Agents cannot create or update project boards from this repo. The human maintains the [project board](https://github.com/users/shahzebqazi/projects/7) (or linked issue URLs); agents reference those links in PRs and use PR descriptions as the execution record. **Android Auto guideline alignment:** copy-paste **Kanban cards** and **agent instructions** live under **“Kanban: Android Auto guidelines alignment (Project #7)”** in [`START_HERE.md`](START_HERE.md).

Onboarding detail, log filters, and **what is intentionally not implemented** (e.g. launcher `MainActivity` UI): see [`START_HERE.md`](START_HERE.md).
