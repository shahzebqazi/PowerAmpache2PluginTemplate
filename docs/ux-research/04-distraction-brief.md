# Distraction — what it means for music on Android Auto

**Purpose:** A readable summary of **why** shallow browse and voice matter, plus **where the rules actually come from** for a consumer music app. Long-form citations sit in [bibliography.md](bibliography.md); a future pass can add DOIs for peer-reviewed papers.

## In short

- Doing **anything** besides driving — looking away, taking hands off, thinking hard — competes with the road. Music apps are **less** demanding than messaging or visual search, but **deep browse** and **typing** still hurt.
- **Google** sets **distraction safeguards** and **Play quality** rules for car media apps. Treat those as **binding** for shipping ([distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards), [app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality)).
- **NHTSA** and similar bodies publish research and guidelines on driver attention. Use the **specific** document and revision you mean — the [general hub](https://www.nhtsa.gov/research-data/distracted-driving) is not one numbered rulebook. None of this replaces **OEM** certification or **Play** review.

## A simple framework

| Channel | Examples in music | What we worry about |
|---------|-------------------|------------------------|
| **Visual** | Reading titles, scanning lists | How long and how often eyes leave the road |
| **Manual** | Taps, scrolls, rotary nudges | Time with hands busy |
| **Cognitive** | Choosing among many browse nodes | Memory and decision time |

**Design response:** **Shallow** trees where you can, **voice** for search when moving, **predictable** now playing controls, **short** strings on browse rows.

## Regions and stores

- **United States:** NHTSA material can inform **heuristics**; cite concrete publications when you quote numbers.
- **European Union:** High-level road safety pages set **tone**, not Android Auto compliance.
- **Google Play:** **Android app quality for cars** is the practical gate for many teams.

## Android Auto specifically

Google states that distraction matters and apps must follow **design requirements** for listing ([Cars media overview](https://developer.android.com/training/cars/media)). The **media browser** model puts most layout in the **host**, which cuts arbitrary custom screens — but **browsing and scrolling** are still real risks.

## What we try to do in PA2

| Area | Aim |
|------|-----|
| Browse | **Wide** roots — recents, playlists — before deep taxonomies as the default |
| Search | **Voice-first**; keyboard-heavy flows when **parked** or on **phone** |
| Metadata | Short **title / artist** lines |
| Errors | One **clear** line + retry; avoid multi-step recovery while moving |
| Queue | **Secondary** on the car; serious edits on **phone** |

## Gaps and honest guesses

- We have not yet inlined **peer-reviewed** citations into this brief — placeholder for a systematic literature pass.
- **Hypothesis:** people forgive fewer features on Auto if **play, pause, and skip** are flawless.
- **Tension:** “Rich library browsing” vs “shallow hierarchy” — see [07-research-synthesis.md](07-research-synthesis.md).

## Related

- [05-design-guardrails-checklist.md](05-design-guardrails-checklist.md) — day-to-day checklist  
- [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md) — allowed vs discouraged

---

*Last reviewed: 2026-04-07*
