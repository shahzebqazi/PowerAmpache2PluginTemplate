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
