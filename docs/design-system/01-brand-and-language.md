# Brand and design language — Power Ampache 2

Aligned with **99designs** “Branding and Design Language”: philosophy, terminology, typography, color (with accessibility notes), logos/assets.

## Design philosophy

- **Music-first clarity:** The UI prioritizes **what is playing** and **what is next**, not chrome.
- **Material 3 baseline:** Semantic roles (`primary`, `surface`, `onSurface`, …) with **Power Ampache** accents (teal/cyan primary in dark fixed scheme; warm tertiary accents).
- **Adaptive identity:** Users may enable **Material You** dynamic color (Android 12+); fixed **dark/light** schemes remain the fallback.
- **In-car alignment:** On **Android Auto**, **glanceability** overrides brand color expression — see [00-design-system-index.md](00-design-system-index.md).

## Language and tone (product copy)

- **Direct, neutral, short** for errors and permissions (avoid blame).
- **Ampache-specific terms** (server, smartlist, demo mode) belong in **settings** and **onboarding**, not on every browse row.
- **Voice and car:** Prefer **spoken-friendly** titles (avoid special characters that TTS mangles when possible).

## Terminology (starter)

| Term | Meaning |
|------|---------|
| Library | Media from connected Ampache-compatible backend |
| Offline | Cached / downloaded playable without network |
| Smartlist | Rule- or server-driven dynamic playlist |
| Queue | Current play order (editable primarily on phone) |

## Typography

- **Family:** **Nunito** (files named `nunito_*` in theme module; Compose object historically named `AppFont.LatoFont`).
- **Roles:** All Material 3 typography slots (`display*` … `label*`) use Nunito ([`Type.kt` in PowerAmpache2Theme](https://github.com/icefields/PowerAmpache2Theme)).
- **Phone-only sizes** for lists/player also live in `app/.../values/dimen.xml` (e.g. `player_songTitle_size` 18sp, `songItem_infoTextSection_textSize_title` 17sp).

## Color — XML tokens (`PowerAmpache2Theme/src/main/res/values/colors.xml`)

| Token | Hex | Role |
|-------|-----|------|
| `primaryDark` | `#FF70CCCC` | Dark scheme primary |
| `onPrimaryDark` | `#FF122E2E` | On-primary |
| `surfaceDark` | `#FF1B1F1D` | Main dark surface |
| `surfaceContainerDark` | `#FF242927` | Grouped surface |
| `surfaceContainerHighDark` | `#FF282E2C` | Higher elevation; queue handle |
| `onSurfaceDark` | `#FFDFE5E3` | Primary text (dark) |
| `onBackgroundDark` | `#FFDFE5E3` | On background |
| `onSurfaceVariantDark` | `#FFCBCED1` | Secondary text |
| `surfaceVariantDark` | `#FF4E5359` | Variant surface |
| `secondaryContainerDark` | `#FF41444C` | Secondary container |
| `onSecondaryContainerDark` | `#FFCFD8E5` | On secondary container |
| `tertiaryDark` | `#FFFABA82` | Accent |
| `onTertiaryDark` | `#FF421900` | On tertiary |
| `errorDark` | `#FFF593AB` | Error |
| `outlineDark` | `#FFA5A9AD` | Outline |
| `surfaceLight` | `#FFF2FAF7` | Light surface |
| `onBackgroundLight` | `#1F2124` | Light text baseline |
| `tertiaryLight` | `#A14300` | Light tertiary |
| `surfaceContainerLight` | `#E9F0ED` | Light container |
| `surfaceContainerHighLight` | `#FFE4EBE8` | Light high container |
| `onSurfaceVariantLight` | `#FF4E5359` | Light secondary text |

**Night override:** `values-night/colors.xml` switches `onSurfaceVariant` → dark variant.

## Color — Compose-only literals (`Theme.kt`)

These complete Material 3 schemes but are **not** in `colors.xml` (document for parity with Figma):

| Usage | Example hex | Note |
|-------|-------------|------|
| Dark `background` | `#FF282E2C` | Page background |
| Dark `primaryContainer` | `#FF1F4C4C` | Container |
| Light `primary` | `#FF1B6B6B` | Brand teal (light fixed) |
| `scrim` | `#FF0B0D0C` | Overlays |

When **dynamic color** is on, **wallpaper-derived** hues replace many fixed primaries; **additionalColours** still attach.

## Additional colours (`AdditionalColours.kt`)

Extended tokens: `surfaceDim`, `surfaceBright`, `surfaceContainerLowest` … `surfaceContainerHighest`, `shadow`, `queueHandle`. Used for **layered** UI and **queue drag** affordances.

## Accessibility (color)

- Do not rely on **primary teal alone** for state; pair with **icons** or **text**.
- Verify **player** and **login** contrast when overlaying **artwork** or **images**.
- **Auto:** contrast is **host-dependent**; do not assert WCAG against projected UI.

## Logos and assets

- Launcher icons: `app/src/main/res/mipmap-*` (not duplicated here).
- **Placeholder** tint: `@color/onSurfaceVariant` (`placeholderBgTint` in app `colors.xml`).
- Release launcher background reference: `#e9eff7` (`launcherBgColourRelease`).

## Maintenance

- Token changes: update **PowerAmpache2Theme** first, then [appendix-pa2-theme-inventory.md](appendix-pa2-theme-inventory.md) and this file.
