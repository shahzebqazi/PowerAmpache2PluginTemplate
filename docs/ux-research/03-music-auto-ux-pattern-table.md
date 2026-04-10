# Music on Android Auto — patterns and risks

This table ties **flows** to **how hard** they tend to be in the car — glance time, hands, and mental load — and to **who draws the UI** (you vs the head unit). Cross-check with [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md) and [02-task-analysis-and-flows.md](02-task-analysis-and-flows.md).

**Risk** here means **qualitative** demand while driving — not a formal score.

| Flow | Typical depth | Who controls the chrome | Risk | What usually helps |
|------|----------------|--------------------------|------|-------------------|
| **Now playing** | On screen now | Host + your session metadata | Low if controls stay big and stable | Solid `PlaybackState`, short title/artist, good art |
| **Browse: root** | One level in | Your `MediaItem` children | Medium — scanning a list | Short labels, sane groupings, favorites up front |
| **Browse: artist → album → track** | Three or more | Your tree | **High** | Playlists, recents, voice, “recent albums” |
| **Queue** | One screen | Depends on OEM | Medium–high | Keep it simple; **reorder** mostly on phone |
| **Search** | Results list | Often voice-led | **High** with keyboard | Voice first; precision search when parked or on phone |
| **Error: no network** | — | You surface via session/errors | Medium | One short line + retry; offline content at root if you have it |
| **Continue listening** | Zero or one step | Your policy for root | Low | Fast path back to last source |

## Power Ampache concepts on the car

| Idea | Sensible shape on Auto | Watch out for |
|------|------------------------|---------------|
| Server / account | Avoid switching every trip | Multi-step auth in the car |
| Smartlists | Near root or “For you” | Medium complexity |
| Playlists | Root | Usually fine |
| Albums / artists | Classic 2–3 level path | **Default** deep path is tiring |
| Flat song list | Only with search / voice filter | Long scroll |
| Offline | Subtree or clear at root | Confusion if “what plays” is unclear |
| Lyrics / extras | Phone-first | Reading while driving |

## This repo vs the main app

This project has a small **`app/`** Compose module (plugin). **Power-Ampache-2** is the full phone product. For **Android Auto**, compare prototypes like this:

- **Auto:** host lists and player — you own **tree + session + metadata**, not teal pixels.
- **Phone:** **PowerAmpache2Theme**, full layout control.

## When to revisit this table

- Big changes to **Ampache** or PA2 browse.
- New **[car quality](https://developer.android.com/docs/quality-guidelines/car-app-quality)** checklist from Google.
- Findings from **DHU** or **parked** testing in a real car.

---

*Last reviewed: 2026-04-07*
