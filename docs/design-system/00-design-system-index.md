# Power Ampache 2 design system — index (phone + Android Auto)

This design system follows the structure in [99designs — What does a design system consist of?](https://99designs.com/blog/tips/design-systems/): **branding and design language**, **layout and navigation**, **components and patterns**, plus **implementation** notes.

## Android Auto vs the phone app (read this first)

On **Android Auto**, the **head unit** draws browse lists, now playing, and transport controls. The app does **not** skin that UI with Power Ampache colors or Nunito. Integration work is **Media3**: a reliable `MediaSession`, a sensible `MediaItem` tree, accurate metadata and artwork, voice actions, and error reporting, within [Google’s car media](https://developer.android.com/training/cars/media) and distraction rules.

**Phone** surfaces use **PowerAmpache2Theme** (Nunito, Material 3 tokens, optional dynamic color). That is the scope of the token tables in this folder.

## Goals

- Give designers and engineers one place to see how **brand tokens** map to **surfaces**.
- Keep **phone** (PA2 + PowerAmpache2Theme) separate from **Android Auto** (host-rendered media for the app’s media-browser integration).
- Stay aligned with UX research: [../ux-research/README.md](../ux-research/README.md).

## Audience

- Android and Compose engineers working on the handheld app
- Engineers integrating **Media3** / Android Auto (session, browse tree, metadata — not car pixel theme)
- Anyone building browser mockups or marketing screenshots

## Source of truth

| Layer | Location |
|-------|----------|
| **Code (theme module)** | `Power-Ampache-2/PowerAmpache2Theme/` (Git submodule in upstream repo) |
| **Code (app dimens + app colours)** | `Power-Ampache-2/app/src/main/res/values/` |
| **Inventory (this repo)** | [appendix-pa2-theme-inventory.md](appendix-pa2-theme-inventory.md) |

When the upstream theme changes, update the **appendix** and token tables in [01-brand-and-language.md](01-brand-and-language.md).

## Documents

| # | Document |
|---|----------|
| 01 | [01-brand-and-language.md](01-brand-and-language.md) — philosophy, tone, typography, color, assets |
| 02 | [02-layout-and-navigation.md](02-layout-and-navigation.md) — spacing, grids, IA, Auto vs phone |
| 03 | [03-components-and-patterns.md](03-components-and-patterns.md) — UI patterns and states |
| Apx | [appendix-pa2-theme-inventory.md](appendix-pa2-theme-inventory.md) — file map and semantic roles |

## Critical distinction: two surfaces

| Surface | Who controls visuals | PA2 theme? |
|---------|---------------------|------------|
| **Phone** | PA2 Compose + `PowerAmpache2Theme` | **Yes** — colors, Nunito, Material You |
| **Android Auto (media path)** | **Head unit / Android Auto host** | **No** — app supplies **content** (titles, art, tree) |

Do not promise PA2 **teal primary** or **Nunito** inside the **projected** media UI unless the product moves to a **custom template** path and platform allows it.

### Browser mockups (`mockup/`)

The Vite + Svelte app shows **two labelled surfaces**: **Phone — PA2 theme** (tokens and Nunito from this folder) and **Auto — host media** (neutral chrome; content-only parity with production). Mockup **frame labels** use a **neutral badge** on car frames so stakeholders do not confuse preview chrome with OEM styling. Mockups support reviews and copy checks; **compliance** with [Google Play app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality) and [distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards) is proven in **shipping code + host behaviour**, not in the browser alone (see [../ux-research/05-design-guardrails-checklist.md](../ux-research/05-design-guardrails-checklist.md)).

## Optional tools

- **Markdown in-repo** is the canonical **documentation** for this workspace.
- **Figma** (or similar) may be added later; keep tokens synced from `PowerAmpache2Theme` and this folder.

## Related plugins

The upstream app queries **infoplugin**, **lyricsplugin**, **chromecastplugin** packages; their **branding** should remain **subordinate** to PA2 tokens unless a plugin ships a standalone surface. Theme extraction for this package focused on **`PowerAmpache2Theme`** (shared with those integrations when they depend on the same module).
