# Design guardrails — before you ship car media

Use this list before big UX reviews, browse-tree changes, or an Android Auto release candidate.

**Labels on each line:** **SRC** = tied to a linked source (Google / Play). **ASM** = team assumption — still test on hardware.

| # | Guardrail | SRC / ASM | How to check |
|---|-----------|-----------|--------------|
| G1 | Meet **[Android app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality)** for **media** | SRC | Play pre-submission checklist |
| G2 | Follow **[distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards)** for media apps | SRC | Code + DHU |
| G3 | **Play/pause** within **one** deliberate action from the now-playing surface | ASM | DHU / car |
| G4 | **Skip** next and previous within **one** action each from that surface | ASM | DHU / car |
| G5 | Do not rely on **typing** for primary playback while moving | ASM (aligned with safeguard **intent**) | Voice path tested |
| G6 | Aim for **≤2 browse steps** from root to playback on happy paths | ASM | [02-task-analysis-and-flows.md](02-task-analysis-and-flows.md) |
| G7 | **Ellipsize** long titles on browse rows; avoid big multi-line blocks in motion | ASM | Design review |
| G8 | **Offline / errors:** one short sentence + optional retry | ASM | Copy pass |
| G9 | **Queue:** read-mostly on Auto; heavy edit on **phone** | ASM | Scope |
| G10 | **Settings / account / server URL** — default to **parked** or **phone** | ASM | Flow audit |
| G11 | **Artwork** sizes follow [media app architecture](https://developer.android.com/guide/topics/media-apps/media-apps-overview) expectations | SRC | Platform doc |
| G12 | No **in-motion video** or non-media surfaces | SRC | [Cars media](https://developer.android.com/training/cars/media) |

**Primary Google links:** [App quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality) · [Distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards) · [Create audio media apps](https://developer.android.com/training/cars/media) · [Errors in car media](https://developer.android.com/training/cars/media/errors)

## Prototypes vs production

Mockups can show **intent** — hierarchy, copy length, which scenarios you cover. They **do not** prove Play compliance. **G1** and **G2** still need real integration and host behaviour. For frame labels (phone vs Auto), see [../agents/prototype-and-engineering.md](../agents/prototype-and-engineering.md).

## Review log

| Date | Notes |
|------|--------|
| 2026-03-30 | First pass; SRC links consolidated |
| 2026-04-07 | Humanized; prototype tooling in docs/agents |

## Questions for the maintainer

- Minimum **browse depth** for classical / opera (many tracks per work)?
- **Explicit content** in browse — what does the host support?
- **Multiple accounts** — one car profile vs full switching?

---

*Last reviewed: 2026-04-07*
