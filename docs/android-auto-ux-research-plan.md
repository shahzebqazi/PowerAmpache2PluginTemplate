# Android Auto UX research — what this was about

We ran a structured pass so Power Ampache 2’s **in-car** experience could be judged on **driving safety**, **platform rules**, and **real tasks** — not on whoever had the strongest opinion in the room.

## Goals

| Goal | What “good” looks like |
|------|-------------------------|
| **Driver UX** | Clear picture of what people do in the car: play, pause, skip, shallow browse, voice — and how deep browsing compares to those. |
| **Accessibility** | Honest split: **phone** (TalkBack, type size, contrast) vs **Android Auto** (mostly host-owned UI; the app still owes good titles, IDs, and session metadata). |
| **Safety** | Grounding in **Google’s** media-for-cars and Play quality expectations, plus general distraction research where it helps — without claiming a markdown file equals a compliance sign-off. |

**In scope:** Music and media on Android Auto–style surfaces, Power Ampache–like flows (library, search, queue, offline where it matters).

**Out of scope unless the product asks:** Full regulatory certification, OEM sign-off, navigation or chat apps.

## What we produced

The living write-ups are under **[docs/ux-research/](ux-research/README.md)** — executive summary, platform constraints, tasks, patterns, distraction notes, guardrails, accessibility, synthesis, and prototype priorities.

**How we ran the phases, methods, and deliverable checklist** — for maintainers and automation — is in **[docs/agents/research-methodology.md](agents/research-methodology.md)**.

## One thing to remember

On **Android Auto**, the **car** draws most of the UI. The app supplies a **sensible media tree**, **accurate metadata**, **artwork**, **session behaviour**, and **errors** in the ways Google documents. The **phone** app is where **PowerAmpache2Theme** and full layout control live — see **[docs/design-system/](design-system/00-design-system-index.md)**.

**Branch:** Keep research and mockup docs on **`mockups`**, not `dev` or `main`.

---

*Last updated: 2026-04-07*
