# Documentation (mockups branch)

This folder holds **UX research**, **design-system** write-ups, **user stories**, and **automation** notes for contributors using AI tools.

- **User stories:** [user-stories.md](user-stories.md)
- **UX research:** [ux-research/README.md](ux-research/README.md)
- **Design system (phone / PA2Theme references):** [design-system/00-design-system-index.md](design-system/00-design-system-index.md)  
- **Auto MVP — PA2 carryover vs Google:** [design-system/04-android-auto-brand-carryover.md](design-system/04-android-auto-brand-carryover.md)
- **Research plan (overview):** [android-auto-ux-research-plan.md](android-auto-ux-research-plan.md)
- **Agents / workflow / tooling:** [agents/README.md](agents/README.md)

**Branch:** **`mockups`** — docs and design assets **only**. **No** application code on this branch; use **`dev`** for the plugin.

## GitHub Pages (research site)

This folder is published as a **static site** (MkDocs Material) via **GitHub Actions** when changes land on **`mockups`**. After **Settings → Pages → GitHub Actions** is enabled:

| URL | Notes |
|-----|--------|
| **`https://sqazi.sh/`** | Custom domain (see **`docs/CNAME`**). The site is at the **domain root**, not under `/PowerAmpache2PluginTemplate/`. |
| `https://shahzebqazi.github.io/PowerAmpache2PluginTemplate/` | Default GitHub Pages URL for this repo (no custom domain). |

If **`http://sqazi.sh/PowerAmpache2PluginTemplate/`** returns **404**, use **`https://sqazi.sh/`** instead — GitHub does not serve this project at a subpath on the custom domain.

Local preview: `pip install -r requirements.txt` (repo root) then `mkdocs serve`. Build output is **`site/`** (gitignored).

---

*Last updated: 2026-04-10*
