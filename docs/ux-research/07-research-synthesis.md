# Research synthesis — Android Auto + Power Ampache 2

## 1. Introduction

This document reconciles phases **A–D** into one narrative and calls out **tradeoffs**, **contradictions**, and **open questions**. Source plan: [android-auto-ux-research-plan.md](../android-auto-ux-research-plan.md).

## 2. Platform reality (Phase A)

- **PA2** ships **`SimpleMediaService` : `MediaSessionService`** with a **`MediaSession`** and manifest intent filters for **legacy media browser** discovery — the same **family** of integration described in [Create audio media apps](https://developer.android.com/training/cars/media). Whether **full library browse** is implemented in upstream must be **verified in code** (see caveat in [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md)).
- This repo’s **`app/`** module is a **Compose** plugin UI reference; **Media3** / **DHU** validation uses **Power-Ampache-2** and/or **PowerAmpache2PluginTemplate** APKs.

**Tradeoff:** Anything that **looks** like custom head-unit chrome can **misrepresent** PA2’s **host-rendered** car UX unless labelled (**Auto — host media** in prototypes).

## 3. Driver tasks and music patterns (Phase B)

- Critical tasks **T1–T3** (play/pause, skip, resume) should dominate engineering and QA time ([02-task-analysis-and-flows.md](02-task-analysis-and-flows.md)).
- Browse patterns that mirror **mainstream** music apps reduce learning curve; deep **Ampache** taxonomies are powerful but **risky** in motion ([03-music-auto-ux-pattern-table.md](03-music-auto-ux-pattern-table.md)).

**Contradiction:** Power users want **full library**; safety research favors **shallow** paths. **Resolution:** **Progressive disclosure** — shallow car root, full tree on **phone**; voice for **specific** requests.

## 4. Safety (Phase C)

- **Binding:** Google **quality** + **safeguards** ([04-distraction-brief.md](04-distraction-brief.md)).
- **Advisory:** NHTSA / EU / literature for internal rubric ([05-design-guardrails-checklist.md](05-design-guardrails-checklist.md)).

**Gap:** Peer-reviewed citations should be **inlined** with DOIs in a future revision of `04-distraction-brief.md`.

## 5. Accessibility (Phase D)

- **Phone:** WCAG principles as **heuristic** for Compose UI; PA2 theme tokens documented under [../design-system/](../design-system/00-design-system-index.md).
- **Auto:** Emphasize **semantic media metadata** and **voice**; avoid assuming custom **screen reader** control ([06-accessibility-matrix.md](06-accessibility-matrix.md)).

**Contradiction:** “Show more info” for a11y vs “minimize glance” for safety — **prefer short primary line + optional secondary** on phone; on Auto follow **host** conventions.

## 6. Design implications by surface

| Surface | Implication |
|---------|-------------|
| **Now playing (Auto)** | Stable controls; crisp `PlaybackState`; concise title/artist |
| **Browse (Auto)** | Flat roots; playlists/recents; avoid endless scroll leaves |
| **Search** | Voice-first; keyboard secondary / parked |
| **Queue** | Simple; edit on phone |
| **Settings / accounts** | Phone-first or parked |
| **Phone app** | Full PA2 theme; large targets; TalkBack; optional dynamic color |

## 7. Open questions (for maintainer)

1. **Offline** indication in **host** browse — feasible and desirable?
2. **Multi-account** in car: block, merge roots, or “last used only”?
3. **Lyrics** on Auto: disabled in motion vs OEM-dependent?
4. **Classical** deep hierarchy: acceptable exception with voice?

**Workspace proposals (Mar 2026, non-binding):** (1) Ship **clear errors** via `MediaSession` / browse first; **offline badge** only with a stable host API for it. (2) **Last-used** account for template-style builds; richer policy in **Power-Ampache-2**. (3) **No lyrics** in motion; parked-only if ever exposed. (4) **Voice-first** for classical deep trees; keep shallow **default** browse for driving.

## 8. Risks (from research plan, risk section)

| Risk | Status |
|------|--------|
| Phone-optimized UI ignores Auto limits | Mitigated by constraint sheet + dual-surface design system |
| Outdated distraction studies | Flagged; prefer recent reviews in next pass |
| a11y = more on-screen text | Mitigated by matrix + guardrails |
| DHU ≠ road | Documented; real-HU spot checks still needed |

## 9. Next documents

- Prototype priorities and engineering checklist: [08-prototype-handoff-package.md](08-prototype-handoff-package.md)
- Phone theme file map: [../design-system/appendix-pa2-theme-inventory.md](../design-system/appendix-pa2-theme-inventory.md)
- **MVP user stories** (requirements traceability to research): [../user-stories.md](../user-stories.md)

## 10. Revision — 2026-04-07

- UX research branch **`cursor-cloud/car-plugin-ux-research-5a36`** (docs + design system) was **merged into `dev`**.
- **User stories** were consolidated into [../user-stories.md](../user-stories.md); **platform constraint sheet** and **executive summary** were corrected for accuracy (this repo **does** ship an `app/` Compose module; Auto remains **host-rendered**).
