# Workflow and repository rules

## Remotes and branches

- **Upstream template (maintainer):** **icefields/PowerAmpache2PluginTemplate**. Ask for access if **`git fetch`** fails with “repository not found.”
- **This fork:** **`origin`** — default remote for pushes.
- **`main`:** Default **implementation** branch — Kotlin, Gradle, **`app/`**, **`data/`**, **`domain/`**, theme submodule. Merge **`upstream/main`** here when syncing the maintainer.
- **`mockups`:** **Design and research only** — `docs/`, `mockups/`, `android-auto-agents/`. **No** application source on this branch.
- **Feature branches:** e.g. **`cursor-cloud/*`** — topic work branched from **`main`**; open PRs to **`main`** on the fork first.

## Scope

- **On `mockups`:** documentation, user stories, **`mockups/web-mockup/`**, and agent scripts — only.
- **On `main`:** implementation; unless scope expands, prefer **`app/`** for the plugin shell.
- **Architecture:** propose options; the **human** product owner or maintainer decides.
- **Theme:** handheld UI uses **PowerAmpache2Theme** (see design-system docs; full phone product is **Power-Ampache-2**).

## When to write application code

- **Not on `mockups`.** Land code on **`main`** or a **feature branch off `main`**.

## Requirements

- **User stories:** [docs/user-stories.md](../user-stories.md) on the **`mockups`** branch (or cherry-picked docs on **`main`** when aligned).
- Treat maintainer **real** use cases as authoritative over placeholder examples in code on **`main`**.

## Tracking

- Use the repo **GitHub Projects** Kanban per root **AGENTS.md**; keep cards in sync with work.

---

*Last updated: 2026-04-10 — second pass: `main` + `mockups` + feature branches.*
