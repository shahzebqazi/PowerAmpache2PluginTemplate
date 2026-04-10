# Power Ampache 2 × Android Auto — browser mockup

**Svelte + Vite** preview for **browse / queue / error** scenarios. It lives under **`mockups/web-mockup/`** on the **`mockups`** branch of **PowerAmpache2PluginTemplate**.

## What this is (and is not)

**Not a product surface:** The plugin does **not** ship a custom Android Auto **player** or head-unit UI. **Android Auto draws** browse lists, now playing, and transport; the app provides **`MediaItem`** data, **metadata**, and a **Media3** `MediaLibraryService` session (see `AndroidAutoMediaLibraryService` on **`main`**).

**This folder:** Stakeholder-friendly **illustrations** for how **library structure** and **session copy** might read on the host — plus **queue** and **error** frames. We **removed** the old full-screen “now playing” mockup so we do not imply we implement car-side player chrome.

**Phone app:** Full handheld UX is **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)** — not mocked here.

| Area | Notes |
|------|--------|
| **Browse** | **`AutoBrowseSection.svelte`** — list patterns; mini strip at top is **decorative** (host-owned in production). |
| **Queue, error** | **`AutoQueue`**, **`AutoErrorState`** — illustrative only. |
| **`FrameLabel`** | **`surface="auto-pa2"`** vs **`surface="auto"`** for badge discipline (see design docs). |

**DHU screenshots:** Optional **`public/dhu/*.png`** for the **home** hero (captures from the Desktop Head Unit, not this HTML).

**Research / design pages:** `#/research`, `#/design`, `#/architecture` — markdown and Mermaid from `docs/` where wired in code.

## Run locally

```bash
cd mockups/web-mockup
npm ci
npm run dev
```

Open the URL Vite prints (usually `http://localhost:5173`).

## GitHub Pages base path

For a project site, set **`VITE_BASE_PATH`** to your Pages path (no trailing slash), e.g. `/PowerAmpache2PluginTemplate`, in CI or env before `npm run build`.

## Related docs

- [docs/design-system/00-design-system-index.md](../docs/design-system/00-design-system-index.md)
- [docs/ux-research/05-design-guardrails-checklist.md](../docs/ux-research/05-design-guardrails-checklist.md)
- [AGENTS.md](../../AGENTS.md) on **`main`** for the implementation branch
