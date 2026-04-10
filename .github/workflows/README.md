# GitHub Actions — Pages

**GitHub Pages** for this repository is deployed by **`.github/workflows/pages.yml` on the `mockups` branch** (MkDocs research site). Do **not** add a second workflow on `main` that uploads the whole repository — it will overwrite the published site.

After changing research docs, merge to **`mockups`** (or push there) so the site rebuilds.

**Environment:** The deploy job uses the **`github-pages`** environment. If deployment fails with *“Branch … is not allowed to deploy to github-pages”*, open **Settings → Environments → `github-pages` → Deployment branches** and allow **`mockups`** (or “All branches”), then re-run the failed workflow or push again.
