# User stories — Android Auto plugin (MVP)

**Status:** Draft — living document  
**Last updated:** 2026-04-10  
**Related research:** [docs/ux-research/](ux-research/README.md)  
**Design system (phone):** [docs/design-system/00-design-system-index.md](design-system/00-design-system-index.md)  
**Auto vs phone (brand / IA):** [docs/design-system/04-android-auto-brand-carryover.md](design-system/04-android-auto-brand-carryover.md)

## Purpose

This file is the **single list of user stories** for the **MVP Android Auto** experience for Power Ampache 2, aligned with **UX research** in this repository and with **product decisions** captured in conversation (host-rendered UI, no custom car chrome for MVP, **Media3** for transport and session). Stories that mention **metadata**, **browse**, or **errors** on Auto should stay consistent with the **two-surface** strategy in [04-android-auto-brand-carryover.md](design-system/04-android-auto-brand-carryover.md) (host chrome vs PA2 content).

**Out of scope for this document:** Line-by-line engineering tasks (browse node IDs, `MediaLibrarySession` callbacks). Those belong in implementation specs or tickets.

---

## Conventions

- **As a / I want / So that** — standard story form.
- **Acceptance notes** — observable behavior, not stack mandates.
- **Traceability** — links to research tasks **T1–T5** ([02-task-analysis-and-flows.md](ux-research/02-task-analysis-and-flows.md)) and prototype priorities **P0–P3** ([08-prototype-handoff-package.md](ux-research/08-prototype-handoff-package.md)) where applicable.

---

## MVP user stories

### US-01 — Browsable media library on Android Auto

**As a** driver using Android Auto,  
**I want** a **browsable** media library that follows the platform’s **media browser** patterns,  
**so that** I can reach my music through the **familiar Android Auto** browser without a custom plugin-specific car UI.

**Acceptance notes**

- Library structure is expressed as **browsable and playable** media items; the **head unit** renders lists and navigation ([01-platform-constraint-sheet.md](ux-research/01-platform-constraint-sheet.md)).
- MVP does **not** add **custom layouts**, overlays, or **custom back** behavior on Auto; navigation follows **host** conventions ([00-executive-summary.md](ux-research/00-executive-summary.md), [01-platform-constraint-sheet.md](ux-research/01-platform-constraint-sheet.md)).

**Traceability:** P1 root browse; platform “browsable/playable tree”.

---

### US-02 — Shallow entry points (collections)

**As a** driver,  
**I want** **shallow** entry points at the browse root (for example **favorites**, **recent** activity, **playlists**, and other **high-value** collections the product selects),  
**so that** I can start music **without deep** browsing while driving.

**Acceptance notes**

- Root children are **limited and scannable**; deep taxonomies are **not** the default driving path ([02-task-analysis-and-flows.md](ux-research/02-task-analysis-and-flows.md), [07-research-synthesis.md](ux-research/07-research-synthesis.md)).
- The **exact** set and labels of root sections are a **product/engineering** decision; UX research recommends prioritizing **continue listening**, **recents**, and **playlists** before long **artist → album** paths ([00-executive-summary.md](ux-research/00-executive-summary.md)).

**Traceability:** T3, T4; P1 “Root browse”; synthesis “flat roots”.

---

### US-03 — Open a collection and see albums or playlists

**As a** driver,  
**I want** to open a **collection** (for example favorites or playlists) and see **albums and/or playlists** with **titles** and **artwork** where available,  
**so that** I can recognize what to play at a glance.

**Acceptance notes**

- Items are **labeled** for **voice** and display (see US-06) ([06-accessibility-matrix.md](ux-research/06-accessibility-matrix.md)).

**Traceability:** Browse pattern; “flat roots” / mainstream patterns ([03-music-auto-ux-pattern-table.md](ux-research/03-music-auto-ux-pattern-table.md)).

---

### US-04 — Open an album or playlist and see tracks

**As a** driver,  
**I want** to open an **album** or **playlist** and see its **tracks** in order,  
**so that** I can pick a specific song when I need to.

**Acceptance notes**

- Prefer **reasonable list length** and **shallow** steps; **deep** browse is higher cognitive load ([02-task-analysis-and-flows.md](ux-research/02-task-analysis-and-flows.md)).

**Traceability:** Composite flow “Browse library to play one track” ([02-task-analysis-and-flows.md](ux-research/02-task-analysis-and-flows.md)); P2 artist → album → track as **non-default** path ([08-prototype-handoff-package.md](ux-research/08-prototype-handoff-package.md)).

---

### US-05 — Start playback from the browse tree

**As a** driver,  
**I want** to **start playback** of a track I selected in the library,  
**so that** music plays in the car **without** picking up the phone.

**Acceptance notes**

- Playback integrates with **`MediaSession`** / **`PlaybackState`** expectations ([01-platform-constraint-sheet.md](ux-research/01-platform-constraint-sheet.md)).

**Traceability:** T3; P0 Now playing (follow-on).

---

### US-06 — Now Playing: clear metadata and artwork

**As a** driver,  
**I want** **Now Playing** to show **clear** title, artist, album, and **artwork** as the platform allows,  
**so that** I understand what is playing with **minimal** glance time.

**Acceptance notes**

- Follow **host** conventions for primary/secondary lines; **semantic** metadata supports **voice** and display ([06-accessibility-matrix.md](ux-research/06-accessibility-matrix.md), [07-research-synthesis.md](ux-research/07-research-synthesis.md)).
- **Auto** chrome does **not** use **PA2 theme colors** as skin on the head unit; **phone** mockups use **PowerAmpache2Theme** ([00-executive-summary.md](ux-research/00-executive-summary.md)).

**Traceability:** P0 Now playing; synthesis table “Now playing (Auto)”.

---

### US-07 — Transport controls (steering wheel and head unit)

**As a** driver,  
**I want** **play**, **pause**, **skip next**, **skip previous**, and **seek** (when the car supports it) through the **standard media** controls,  
**so that** I can control playback **eyes-forward** using the steering wheel or head unit.

**Acceptance notes**

- Implemented via **Media3** / **`MediaSession`** / **player** wiring — **not** a separate custom “car button UI” in the plugin ([02-task-analysis-and-flows.md](ux-research/02-task-analysis-and-flows.md) T1, T2; [01-platform-constraint-sheet.md](ux-research/01-platform-constraint-sheet.md)).

**Traceability:** T1, T2; P0 Now playing.

---

### US-08 — Resume recent playback

**As a** driver,  
**I want** to **resume** or return to **recent** playback with **minimal** browsing,  
**so that** I can continue what I was listening to quickly.

**Acceptance notes**

- Research targets **≤1 browse step from root** for resume-style scenarios where the product supports them ([08-prototype-handoff-package.md](ux-research/08-prototype-handoff-package.md) P0 Resume).

**Traceability:** T1, T4; P0 Resume.

---

### US-09 — Shuffle and repeat (when exposed by session)

**As a** driver,  
**I want** to change **shuffle** and **repeat** from the **media** controls the platform exposes,  
**so that** I can change how playback behaves without custom plugin screens.

**Acceptance notes**

- Availability depends on **session** capabilities and **host** UI; story applies when the implementation advertises these commands.

**Traceability:** Session-level behavior; complements P0 Now playing.

---

### US-10 — Voice play (where supported)

**As a** driver,  
**I want** to use **voice** to play music (for example a playlist or album) when Google’s **voice actions** apply,  
**so that** I can start music with **less** manual browsing.

**Acceptance notes**

- Follow [Voice actions](https://developer.android.com/training/cars/media/voice-actions) and product scope; **typed search** while moving is **high risk** ([02-task-analysis-and-flows.md](ux-research/02-task-analysis-and-flows.md) T5).

**Traceability:** T5; P1 Voice play.

---

### US-11 — Errors and connectivity (no deep menus)

**As a** driver,  
**when** playback or browse fails (for example **offline** or **server unreachable**),  
**I want** **short**, understandable **feedback** without digging through deep menus,  
**so that** I can recover or retry **safely**.

**Acceptance notes**

- Align with **Media3** / **session** error patterns where applicable ([08-prototype-handoff-package.md](ux-research/08-prototype-handoff-package.md) P2 Error; [07-research-synthesis.md](ux-research/07-research-synthesis.md)).

**Traceability:** P2 Error.

---

### US-12 — Catalog comes from the real app session

**As a** listener who already uses Power Ampache 2 with a **working** library,  
**I want** Android Auto to reflect **my** server-backed **catalog** and streaming behavior,  
**so that** Auto shows **real** content — not placeholder demo data — for shipped builds.

**Acceptance notes**

- **Mock** or **demo** data is acceptable only for **development** when explicitly enabled; production behavior is **real** backend content (product policy).

**Traceability:** Engineering alignment; not a UX lab document — captured here for **MVP completeness**.

---

## Traceability summary

| ID | Story (short) | Research tasks | Prototype tier |
|----|----------------|------------------|----------------|
| US-01 | Browsable library / host UI | Platform sheet | P1 |
| US-02 | Shallow root collections | T3, T4 | P1 |
| US-03 | Collections → albums/playlists | T3, T4 | P1 |
| US-04 | Album/playlist → tracks | T3 | P2 path |
| US-05 | Play from browse | T3 | P0–P1 |
| US-06 | Now Playing metadata | T1 | P0 |
| US-07 | Transport controls | T1, T2 | P0 |
| US-08 | Resume / recents | T1, T4 | P0 |
| US-09 | Shuffle / repeat | — | P0 (subset) |
| US-10 | Voice play | T5 | P1 |
| US-11 | Errors | — | P2 |
| US-12 | Real catalog | Policy | — |

---

## Changelog

| Date | Change |
|------|--------|
| 2026-04-10 | UX pass: linked [04-android-auto-brand-carryover.md](design-system/04-android-auto-brand-carryover.md); clarified two-surface alignment for metadata/browse/errors on Auto. |
| 2026-04-07 | Initial document on **mockups** branch; MVP decisions (host UI, Media3 transport, no custom Auto chrome). |
