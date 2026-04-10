# Workflow and repository rules

## Remotes and branches

- **Upstream template:** `icefields/PA2PluginTemplate` — ask for access if `git fetch` fails with “repository not found.”
- **This fork:** `origin` — default remote for day-to-day work.
- **`main`:** Portfolio / GitHub Pages — **not** for UX research or mockup docs.
- **`dev`:** Plugin implementation — **not** for UX research or mockup docs.
- **`mockups`:** **All** UX research, design-system markdown, `docs/user-stories.md`, `mockups/research/`, and Figma links. Do design and research work here.
- **Other branches** (for example `plugin/AndroidAuto`, `cursor-cloud/*`): preserve; do not delete without maintainer approval.

## Scope

- Unless the scope is expanded, **implement inside `app/`** only (when coding on `dev` or feature branches).
- **Architecture:** propose options; the **human maintainer or product owner** decides.
- **Theme:** handheld UI and mockups use **PowerAmpache2Theme** (PA2Theme submodule).

## When to write code

- Do **not** merge or ship application code until the **product owner** explicitly asks for implementation.

## Requirements

- **User stories:** [docs/user-stories.md](../user-stories.md) on the **`mockups`** branch.
- Run a full pass on **functional** and **non-functional** requirements with the maintainer; capture alignment in an executive summary when the team agrees.
- **Use cases:** example flows in the codebase are not the product spec. Prefer **maintainer-provided** real use cases.

## Tracking

- Use the **GitHub Projects** board for this repo.

---

*Last updated: 2026-04-07*
