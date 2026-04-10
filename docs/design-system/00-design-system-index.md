# Power Ampache 2 design system — phone and Android Auto

This folder describes **how PA2 looks and reads** on the **phone**, and how that **differs** from **Android Auto**, where the car — not your theme — paints most of the screen.

## Android Auto vs phone (read this first)

On **Android Auto**, the **head unit** draws browse lists, now playing, and transport. The app does **not** apply **Compose** colors or **Nunito** to that chrome. You **still** carry PA2 forward through **Media3**: **artwork**, **metadata** (titles, artist, album), **browse structure**, and **copy tone** — the same **content identity** as the main app. That is **not** custom Auto UI; it is what Google’s media model is built for. See **[04-android-auto-brand-carryover.md](04-android-auto-brand-carryover.md)** for the full **PA2 + Google** strategy.

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
| 04 | [04-android-auto-brand-carryover.md](04-android-auto-brand-carryover.md) — **PA2 fidelity + Google Auto** (MVP Media3) |
| Apx | [appendix-pa2-theme-inventory.md](appendix-pa2-theme-inventory.md) — file map |

## Two surfaces

| Surface | Who owns the visuals | PA2 alignment |
|---------|---------------------|----------------|
| **Phone** | PA2 Compose + `PowerAmpache2Theme` | **Full theme** — colors, Nunito, Material You |
| **Android Auto (Media3)** | Head unit draws chrome | **Content-level PA2** — artwork, metadata, labels, IA; **not** teal/Nunito on OEM chrome |

**Custom** pixel-level Auto UI is **out of scope** for MVP; **brand carryover** through **session content** is **in scope**. Details: [04-android-auto-brand-carryover.md](04-android-auto-brand-carryover.md).

## Prototypes (Figma, static frames)

Show **two labelled surfaces**: **Phone — PA2 theme** (this folder) and **Auto — host media** (neutral chrome; **content** matches production). Use a **neutral badge** on car frames. Mockups help reviews and copy; **Play** compliance is proven in **code + host**, not PNGs alone — see [../ux-research/05-design-guardrails-checklist.md](../ux-research/05-design-guardrails-checklist.md). Frame labels for tools: [../agents/prototype-and-engineering.md](../agents/prototype-and-engineering.md).

## Optional

- **Markdown in-repo** is the canonical **documentation** for this fork on **`mockups`** (no theme code here — references point at **Power-Ampache-2** / **icefields/PowerAmpache2Theme**).
- **Figma** may sit beside it; keep tokens aligned with upstream `PowerAmpache2Theme` and this folder.

## Related plugins

The main app talks to other plugins (info, lyrics, Chromecast, etc.). Their **branding** should stay **secondary** to PA2 tokens unless a plugin ships its own surface. This extraction focused on **`PowerAmpache2Theme`** as the shared layer.

---

*Last reviewed: 2026-04-10*
