# Workflow and repository rules

## Remotes and branches

- **Upstream template:** `icefields/PA2PluginTemplate` — ask for access if `git fetch` fails with “repository not found.”
- **This fork:** `origin` — default remote for day-to-day work.
- **`main`:** Portfolio / GitHub Pages — **not** for UX research or mockup docs.
- **`dev`:** **Plugin implementation** (Kotlin, Gradle, `app/`). **No** research or mockup docs live here.
- **`mockups`:** **Design and research only** — `docs/`, `mockups/`, Figma notes. **No** application source code on this branch.
- **Other branches** (for example `plugin/AndroidAuto`, `cursor-cloud/*`): preserve; do not delete without maintainer approval.

## Scope

- **On `mockups`:** edit documentation, user stories, and mockup assets only.
- **On `dev`:** implementation; unless scope expands, prefer **`app/`** for the plugin.
- **Architecture:** propose options; the **human** product owner or maintainer decides.
- **Theme:** handheld UI references **PowerAmpache2Theme** (see design-system docs; code lives in **Power-Ampache-2** / **`dev`**).

## When to write application code

- **Not on `mockups`.** Land code on **`dev`** (or feature branches) when the product owner asks.

## Requirements

- **User stories:** [docs/user-stories.md](../user-stories.md) on this branch.
- Treat maintainer **real** use cases as authoritative over placeholder examples in code on **`dev`**.

## Tracking

- Use the repo **GitHub Projects** board; keep cards in sync with work.

---

*Last updated: 2026-04-10*
