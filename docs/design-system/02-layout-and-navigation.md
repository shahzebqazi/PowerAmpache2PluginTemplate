# Layout and navigation — phone vs Android Auto

Aligned with **99designs** “Layout and Navigation”: units, hierarchy, interaction, navigation standards, device specifications.

## Units and measurement

- **Phone:** **dp** for spacing and component size, **sp** for text (`dimen.xml` in `Power-Ampache-2/app/src/main/res/values/dimen.xml`).
- **Compose:** Modifier padding uses the same logical units as XML.

## Spacing scale (representative tokens from `dimen.xml`)

| Token | Value | Typical use |
|-------|-------|-------------|
| `divider_padding` | 16dp | Section separation |
| `listItem_padding_horizontal` | 16dp | List rows |
| `listItem_padding_vertical` | 8dp | List rows |
| `player_screen_padding` | 16dp | Player screen gutters |
| `player_screen_title_padding` | 24dp | Title block |
| `player_controls_spacing` | 16dp | Control clusters |
| `miniPlayer_height` | 70dp | Mini player bar |
| `main_floating_button_size` | 95dp | FAB-class control |
| `home_row_spacing` | 16dp | Home grids |
| `home_album_item_image_size_default` | 150dp | Album art tile |
| `home_album_item_image_size_recent` | 120dp | Recent row art |

Use these as **defaults** for mockups of **phone** screens; do not assume the same numbers on **Auto**.

## Navigation — phone (information architecture)

High-level areas (conceptual; confirm against current nav graph):

- **Main / home** — albums, playlists, genres, recents
- **Library detail** — album, playlist, artist flows
- **Player / queue** — full-screen player, queue sheet
- **Settings** — theme (`PowerAmpTheme`: system, dark, light, Material You variants), accounts, downloads
- **Auth** — login / demo (uses `surfaceDark`, `primaryDark` from theme)

**Depth:** Prefer **shallow** paths to **playback** on phone when mirroring **car** mental models (e.g. prominent “continue”).

## Layout — Android Auto (media browser path)

- **Not** governed by PA2 `dimen.xml`.
- **Browse:** Hierarchical **`MediaItem`** tree; host renders **lists** (and OEM-specific layouts).
- **Now playing:** Host player; PA2 supplies **metadata** and **state**.
- **Interaction:** Touch, **rotary**, and **voice** depend on **vehicle**; design **content order** and **short labels** for all three.

**Cross-reference:** [../ux-research/01-platform-constraint-sheet.md](../ux-research/01-platform-constraint-sheet.md), [../ux-research/03-music-auto-ux-pattern-table.md](../ux-research/03-music-auto-ux-pattern-table.md).

## Breakpoints

- **Phone:** Standard **single-pane** Compose layouts; tablet/multi-pane **not** assumed unless product adds them.
- **Auto:** **Host** breakpoint logic; app provides **scalable** artwork where required by media guidelines.

## Grids

- **Home / discovery:** Card and row grids use spacing above; align **16dp** as baseline rhythm.
- **Lists:** `songItem_*` and `albumItem_*` paddings define **dense but readable** music lists.

## Non-standard devices

- **Desktop Head Unit:** Development; document **input** and **latency** differences vs real HU ([Testing](https://developer.android.com/training/cars/testing)).
- **Automotive OS:** Same **media** architecture for audio path; verify **AAOS**-specific quality checklist items at release time.

## Now playing (host media reference)

**Host-rendered** Android Auto remains **different** from handheld UI. Handheld now playing lives in **Power-Ampache-2**; car surfaces are **host-drawn** from your **MediaSession** and **`MediaItem`** tree.

For **prototype** frames, aim for host-like **metadata**, **seek** row (**~48dp** surface intent where the host exposes it), and **transport** — **illustration only**, not OEM pixels.
