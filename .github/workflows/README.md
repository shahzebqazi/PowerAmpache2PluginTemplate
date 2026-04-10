# GitHub Actions — Pages

**GitHub Pages** for this repository is deployed by **`.github/workflows/pages.yml` on the `mockups` branch** (MkDocs research site). Do **not** add a second workflow on `main` that uploads the whole repository — it will overwrite the published site.

After changing research docs, merge to **`mockups`** (or push there) so the site rebuilds.
