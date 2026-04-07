# Design guardrails checklist

Use before major UX reviews, browse-tree changes, or Android Auto release candidates. Tag each item **SRC** (linked source) or **ASM** (team assumption).

| # | Guardrail | SRC / ASM | Verification |
|---|-----------|-----------|--------------|
| G1 | Meet current **Google Play — Android app quality for cars** for **media** | SRC | Pre-submission checklist |
| G2 | Implement **distraction safeguards** per media training doc | SRC | Code + DHU behaviour |
| G3 | **Play/pause** reachable within **one** deliberate action from now-playing-equivalent | ASM | DHU / device |
| G4 | **Skip** next/previous within **one** action each from same surface | ASM | DHU / device |
| G5 | Avoid **keyboard-dependent** flows for primary playback tasks while driving | ASM (aligned with distraction-safeguards **intent**; confirm wording vs doc) | Voice path tested |
| G6 | Prefer **≤2 browse actions** from root to start playback for “happy path” scenarios | ASM | Task analysis [02](02-task-analysis-and-flows.md) |
| G7 | **Truncate or ellipsize** long titles on browse rows; no multi-line blocks in motion | ASM | UI review |
| G8 | **Offline / error** states use **one short sentence** + optional retry | ASM | Copy review |
| G9 | **Queue reorder / bulk edit** not required on Auto; phone is primary | ASM | Scope doc |
| G10 | **Settings / account / server URL** changes default to **parked** or **phone** | ASM | Flow audit |
| G11 | Artwork and icons follow **media app architecture** (sizes, session) | SRC | Platform doc |
| G12 | Do not add **in-motion video** or non-media surfaces | SRC | [Cars media](https://developer.android.com/training/cars/media) |

**Primary SRC links (cars / Play):** [App quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality) · [Implement distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards) · [Create audio media apps](https://developer.android.com/training/cars/media) · [Errors in car media apps](https://developer.android.com/training/cars/media/errors)

---

## Design prototypes vs shipping verification

| Guardrail | What prototypes can show | What still requires product / platform proof |
|-----------|---------------------------|---------------------------------------------|
| G1, G2 | N/A — prototypes are not a submission artifact | Play checklist, host behaviour, policy updates |
| G3, G4 | Now-playing layouts with play/pause and skip **visible** on one surface | That **MediaSession** actions and host UI actually expose them in one step on target OEMs |
| G5 | Flows without keyboard for P0/P1 (browse + voice implied) | Voice **MediaSession** wiring and locale tests |
| G6, G7 | Shallow root browse, **ellipsized** row copy | Real **`MediaItem`** tree depth and string limits in production |
| G8 | **One sentence + retry** error copy | [Error reporting](https://developer.android.com/training/cars/media/errors) in app code |
| G9 | Queue frame labelled **read-mostly**; reorder on phone | Scope in app + host queue UX |
| G10 | Copy / notes only | Flow audit: settings on phone or parked |
| G11, G12 | Static frames only; no video | Artwork pipeline, category rules |

Treat wireframes and Figma as **aligned with intent** when they follow [08-prototype-handoff-package.md](08-prototype-handoff-package.md) labels and the **Fidelity, labelling, and compliance limits** section there.

---

## Review record

| Date | Reviewer | Notes |
|------|----------|-------|
| 2026-03-30 | Research pass (initial) | Populate SRC links to exact quality-checklist sections in next edit. |
| 2026-03-30 | Mockup/doc hardening | Added primary SRC link row; **Browser mockups vs shipping verification** table (later generalized to design prototypes). |

---

## Open questions for maintainer

- Minimum **browse depth** acceptable for classical/opera users (many tracks per work)?
- **Explicit content** flags in browse on Auto — host support?
- **Multi-account**: single “car profile” vs full switching?
