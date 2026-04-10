# Power Ampache 2 design system — phone and Android Auto

This folder describes **how PA2 looks and reads** on the **phone**, and how that **differs** from **Android Auto**, where the car — not your theme — paints most of the screen.

## Android Auto vs phone (read this first)

On **Android Auto**, the **head unit** draws browse lists, now playing, and transport. The app does **not** skin that UI with Power Ampache colors or Nunito. Your integration work is **Media3**: a solid `MediaSession`, a sensible `MediaItem` tree, accurate metadata and artwork, voice actions, and error reporting — within [Google’s car media guidance](https://developer.android.com/training/cars/media) and distraction rules.

On the **phone**, surfaces use **PowerAmpache2Theme** (Nunito, Material 3, optional dynamic color). That is what the token tables in this folder describe.

## Why this exists

- One place to see how **brand** and **tokens** map to real screens.
- A clear line between **phone** (you control layout and color) and **Auto** (you control **content** and **session**).
- Alignment with UX research: [../ux-research/README.md](../ux-research/README.md).

## Who uses it

- Android and Compose engineers on the handheld app
- People integrating **Media3** / Android Auto (session, browse tree, metadata — not painting the head unit)
- Designers and anyone building **mockups** or store screenshots

## Where the truth lives

| Layer | Location |
|-------|----------|
| **Theme module (code)** | `Power-Ampache-2/PowerAmpache2Theme/` — submodule in the main repo |
| **App dimens and colours** | `Power-Ampache-2/app/src/main/res/values/` |
| **Inventory in this repo** | [appendix-pa2-theme-inventory.md](appendix-pa2-theme-inventory.md) |

When the upstream theme changes, refresh the **appendix** and the token tables in [01-brand-and-language.md](01-brand-and-language.md).

## Documents here

| # | Document |
|---|----------|
| 01 | [01-brand-and-language.md](01-brand-and-language.md) — voice, type, color, assets |
| 02 | [02-layout-and-navigation.md](02-layout-and-navigation.md) — spacing, grids, IA, Auto vs phone |
| 03 | [03-components-and-patterns.md](03-components-and-patterns.md) — patterns and states |
| Apx | [appendix-pa2-theme-inventory.md](appendix-pa2-theme-inventory.md) — file map |

## Two surfaces

| Surface | Who owns the visuals | PA2 theme? |
|---------|---------------------|------------|
| **Phone** | PA2 Compose + `PowerAmpache2Theme` | **Yes** — colors, Nunito, Material You |
| **Android Auto (media path)** | Head unit / Android Auto host | **No** — you supply **titles, art, tree**, not teal chrome |

Do not promise PA2 **teal** or **Nunito** inside the **projected** media UI unless the product moves to a **custom template** path and the platform allows it.

## Prototypes (Figma, static frames)

Show **two labelled surfaces**: **Phone — PA2 theme** (this folder) and **Auto — host media** (neutral chrome; **content** matches production). Use a **neutral badge** on car frames. Mockups help reviews and copy; **Play** compliance is proven in **code + host**, not PNGs alone — see [../ux-research/05-design-guardrails-checklist.md](../ux-research/05-design-guardrails-checklist.md). Frame labels for tools: [../agents/prototype-and-engineering.md](../agents/prototype-and-engineering.md).

## Optional

- **Markdown in-repo** is the canonical **documentation** for this fork on **`mockups`**.
- **Figma** may sit beside it; keep tokens aligned with `PowerAmpache2Theme` and this folder.

## Related plugins

The main app talks to other plugins (info, lyrics, Chromecast, etc.). Their **branding** should stay **secondary** to PA2 tokens unless a plugin ships its own surface. This extraction focused on **`PowerAmpache2Theme`** as the shared layer.

---

*Last reviewed: 2026-04-07*
