# Agents — Power Ampache 2 plugin template

## Commit messages (required for agents)

Include the **current Git branch name** at the start of the **subject line** so history stays attributable after rebases, cherry-picks, or fast-forward merges.

**Format:** `<branch-name>: <short imperative summary>`

**Examples:**

- `cursor-cloud/dev-main-4dc1: Wire playlist songs through MusicFetcher`
- `main: Sync strings with upstream`

Use the exact branch from `git branch --show-current` (do not abbreviate). If the subject would be long, keep the branch prefix and shorten the summary; add detail in the body after a blank line.

**Rationale:** After a rebase, commit hashes change; the branch prefix preserves which line of work produced the change.

## Repository

- **Modules:** `domain`, `data`, `app`, `PowerAmpache2Theme` — respect Clean Architecture boundaries unless the product owner expands scope.
