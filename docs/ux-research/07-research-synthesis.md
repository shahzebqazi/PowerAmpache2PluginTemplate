# Research synthesis — Android Auto + Power Ampache 2

This document pulls phases **A–D** of [../android-auto-ux-research-plan.md](../android-auto-ux-research-plan.md) into one narrative: tradeoffs, tensions, and what we still do not know. **How we ran the phases** is in [../agents/research-methodology.md](../agents/research-methodology.md).

## 1. Platform reality

Power Ampache 2 ships **`SimpleMediaService`** as a **`MediaSessionService`** with a **`MediaSession`** and manifest hooks for **media browser** discovery — the same **family** Google describes in [Create audio media apps](https://developer.android.com/training/cars/media). Whether **full library browse** is implemented in every build must still be **verified in code** ([01-platform-constraint-sheet.md](01-platform-constraint-sheet.md)).

This repo’s **`mockups`** branch carries **plugin `app/`** code and **docs** together. **DHU** and real-car testing use APKs built here and/or **Power-Ampache-2**.

**Tradeoff:** Anything that **looks** like custom head-unit chrome can **misrepresent** real Auto unless you label it **Auto — host media** in prototypes.

## 2. Driver tasks and music habits

**T1–T3** (play/pause, skip, resume) deserve the bulk of engineering and QA time ([02-task-analysis-and-flows.md](02-task-analysis-and-flows.md)). Mainstream patterns reduce learning curve; **deep Ampache** trees are powerful on the phone and **risky** in motion ([03-music-auto-ux-pattern-table.md](03-music-auto-ux-pattern-table.md)).

**Tension:** Power users want **full library**; safety research favors **shallow** paths. **Pragmatic split:** shallow **default** on the car, full tree on **phone**, **voice** for specific requests.

## 3. Safety

- **Binding:** Google **quality** + **safeguards** ([04-distraction-brief.md](04-distraction-brief.md)).
- **Advisory:** NHTSA / EU / literature for internal rubrics ([05-design-guardrails-checklist.md](05-design-guardrails-checklist.md)).

**Gap:** Peer-reviewed citations could be inlined with DOIs in a future revision of `04-distraction-brief.md`.

## 4. Accessibility

- **Phone:** WCAG principles as a **heuristic** for Compose; tokens in [../design-system/](../design-system/00-design-system-index.md).
- **Auto:** Emphasize **semantic media metadata** and **voice**; do not assume a custom screen reader for the host ([06-accessibility-matrix.md](06-accessibility-matrix.md)).

**Tension:** “Show more info” for a11y vs “minimize glance” for safety — **short primary line + optional secondary** on phone; on Auto follow **host** conventions.

## 5. Design notes by surface

| Surface | Implication |
|---------|-------------|
| **Now playing (Auto)** | Stable controls; crisp `PlaybackState`; concise title/artist |
| **Browse (Auto)** | Flat roots; playlists/recents; avoid endless scroll leaves |
| **Search** | Voice-first; keyboard secondary / parked |
| **Queue** | Simple; edit on phone |
| **Settings / accounts** | Phone-first or parked |
| **Phone app** | Full PA2 theme; large targets; TalkBack; optional dynamic color |

## 6. Open questions (for maintainer)

1. **Offline** indication in **host** browse — feasible and desirable?
2. **Multi-account** in car: block, merge roots, or “last used only”?
3. **Lyrics** on Auto: disabled in motion vs OEM-dependent?
4. **Classical** deep hierarchy: acceptable exception with voice?

**Workspace notes (non-binding):** (1) Ship **clear errors** via `MediaSession` / browse first; **offline badge** only with a stable API. (2) **Last-used** account for template-style builds; richer policy in **Power-Ampache-2**. (3) **No lyrics** in motion; parked-only if ever exposed. (4) **Voice-first** for classical deep trees; keep shallow **default** browse for driving.

## 7. Risks (from research plan)

| Risk | Status |
|------|--------|
| Phone-optimized UI ignores Auto limits | Mitigated by constraint sheet + dual-surface design system |
| Outdated distraction studies | Flagged; prefer recent reviews in next pass |
| a11y = more on-screen text | Mitigated by matrix + guardrails |
| DHU ≠ road | Documented; real-HU spot checks still needed |

## 8. Where to go next

- [08-prototype-handoff-package.md](08-prototype-handoff-package.md) — scenario priorities (P0–P3)  
- [../design-system/appendix-pa2-theme-inventory.md](../design-system/appendix-pa2-theme-inventory.md) — theme file map  
- [../user-stories.md](../user-stories.md) — MVP user stories  
- **Branch:** UX research and mock assets live on **`mockups`**, not `dev` or `main`.

---

*Last reviewed: 2026-04-07*
