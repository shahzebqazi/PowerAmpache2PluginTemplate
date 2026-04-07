# Music on Android Auto — UX pattern table

Cross-reference: [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md), [02-task-analysis-and-flows.md](02-task-analysis-and-flows.md).

**Legend — Primary risk:** Glance / manual / cognitive demand while driving (qualitative).

| Flow | Typical depth (levels) | Host vs app control | Primary risk | Mitigation |
|------|------------------------|---------------------|--------------|------------|
| **Now playing** | 0 (surface) | Host UI + session metadata | Low if controls are large and stable | Reliable `PlaybackState`, artwork, title/artist brevity |
| **Browse: root** | 1 | App tree (`MediaItem` children) | Medium (scan list) | Cap visible cognitive load: short labels, groupings, favorites first |
| **Browse: artist → album → track** | 3+ | App tree | **High** | Flatten with playlists; voice; “recent albums” |
| **Queue** | 1 | Depends on host implementation | Medium–High | Keep queue **short**; avoid complex reorder on car |
| **Search** | 1+ results | Often voice-mediated | **High** if keyboard | Prefer voice; defer precision search to parked / phone |
| **Error: no network** | — | App must expose via session / errors doc | Medium | Clear, short message; offline content surfaced at root |
| **Continue listening** | 0–1 | App policy for root children | Low | Single-tap resume from last source |

---

## Power Ampache 2 feature mapping

| PA2 domain concept | Suggested Auto browse shape | Risk note |
|--------------------|----------------------------|-----------|
| Server / account | Avoid per-trip switching; use one active library | High if multi-step auth |
| Smartlists | Root or “For you” | Medium |
| Playlists | Root | Low–Medium |
| Albums / Artists | Standard 2–3 level | High if default path |
| Songs (flat) | Only with search/voice filter | Very high scroll |
| Offline library | Subtree or badge at root | Medium if unclear what is local |
| Lyrics / extras | Phone-first | N/A or low on Auto unless template allows |

---

## Phone vs car APK

This repo has **no** root **`app`** module. **Power-Ampache-2** is the phone product; **PowerAmpache2PluginTemplate** (or PA2) supplies the **Media3** build for **DHU**. When comparing mockups:

- **PA2 Auto:** Pattern column “Host vs app” = mostly **host-rendered** lists and player chrome.
- **Phone frames:** **Phone — PA2 theme** in **`mockup/`**; car frames use **Auto — host media** patterns.

---

## Review cadence

Revisit this table after:

- Major **Ampache API** or PA2 browse changes.
- New **Google Play car quality** checklist revisions.
- Usability findings from **DHU** or parked testing.
