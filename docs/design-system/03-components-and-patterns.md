# Components and patterns

Aligned with **99designs** “Components and Patterns”: modular UI, states, icons/imagery, interactive behaviours. Split by **surface**.

## Phone — pattern library (Compose / Material 3)

Document **patterns** PA2 already implements; map engineering names when known from `dimen.xml` and screen packages.

| Pattern | Description | Key tokens / notes |
|---------|-------------|-------------------|
| **List row — song** | Title, artist, album lines | `songItem_*` text sizes 17/14/14sp, card radius 1dp |
| **List row — album** | Art + title/artist | `albumItem_*`, corner 5dp |
| **Mini player** | Persistent bar | Height 70dp |
| **Full player** | Art, scrubber, actions | `player_*` dimens; `additionalColours` for slider track |
| **Queue sheet** | Drag handle, reorder | `queue_dragHandle_height` 45dp; `queueHandle` colour |
| **Home grids** | Recent vs default art sizes | 120dp vs 150dp |
| **Login / signup** | Bottom sheets on `surfaceDark` | `loginScreen_*` XML colour slots |
| **Settings list** | Standard rows | `settings_padding_*` |
| **Chips / tags** | Album detail | `albumDetail_chip_*` |
| **Dialogs** | Info / confirm | `dialogInfo_*` |

### States

- **Default / pressed / disabled:** Use Material 3 **Button**, **IconButton**, **Card** defaults with PA2 `ColorScheme`.
- **Loading:** Prefer **linear** or **skeleton** on lists; avoid **infinite** spinners without context.
- **Error:** Snackbar or dialog; **short** copy ([guardrails G8](../ux-research/05-design-guardrails-checklist.md)).
- **Empty:** Explain **why** (no server, no offline) + **one** primary action.

### Iconography and imagery

- **Material** icons + custom assets in `drawable` / `mipmap`.
- **Album art:** Rounded shapes per card; **placeholder** uses `onSurfaceVariant`.
- **Format:** Vector where possible on phone; **artwork** from network follows **coil** / loader patterns in app.

### Motion

- Respect **system reduced motion** where implemented; avoid **decorative** motion on player controls.

---

## Android Auto — patterns (host media UI)

These are **behavioural** patterns, not PA2-styled widgets.

| Pattern | App responsibility | Host responsibility |
|---------|-------------------|---------------------|
| **Browse list** | Children `MediaItem`s, stable IDs, subtitles | Rendering, scroll, rotary |
| **Playable leaf** | `FLAG_PLAYABLE`, metadata | Tap to play |
| **Browsable node** | `FLAG_BROWSABLE`, sensible title | Drill-in |
| **Now playing** | `PlaybackState`, queue title | Transport UI |
| **Voice** | Session **actions** and **search** support | NLP, confirmation |
| **Errors** | Report via media error APIs | Display |

**Cross-link:** [../ux-research/03-music-auto-ux-pattern-table.md](../ux-research/03-music-auto-ux-pattern-table.md).

---

## Anti-patterns

- **Applying PA2 hex colours** to Figma frames labelled “Android Auto” for **media browser** product — misleading.
- **Deep settings** in projected UI for **server URL** edit while driving.
- **Lyrics** as large scrollable text in motion (safety + distraction).

---

## Revision

When new screens ship (e.g. smartlist UI), add a **row** under phone patterns with **dimen** prefix or file reference.
