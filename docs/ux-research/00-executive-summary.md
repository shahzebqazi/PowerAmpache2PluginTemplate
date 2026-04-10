# Executive summary — Android Auto and Power Ampache 2

**Research snapshot:** 2026-03-30 · **Last refreshed:** 2026-04-07

This note is for **maintainers, designers, and Android engineers** who need a single page before diving into the rest of [this folder](README.md).

## Why we did this

Streaming music in the car is easy to underestimate. Small choices — how deep the browse tree goes, how long titles are, whether search defaults to voice — change **glance time** and **hands off the wheel**. Google also sets **hard rules** for media apps on Android Auto and for Play listing.

Power Ampache 2’s **main** Android app uses **Media3** (`MediaSession` hosted in a `MediaSessionService`, media browser discovery in the manifest). **Android Auto** does not use your app’s colors or fonts on the head unit: it shows **host-rendered** lists and the player, fed by your **session**, **metadata**, and **browse tree**.

On the `**mockups`** branch, this repo includes plugin `**app/**` code and docs together. Validate integrated **Auto** behaviour on the **Desktop Head Unit** or hardware with the APK built from this branch or **Power-Ampache-2**, as described in [08-prototype-handoff-package.md](08-prototype-handoff-package.md).

## What we learned

1. **Platform** — PA2 follows Google’s **media session** pattern for cars. How **complete** library browse is on Auto in every build should still be **checked in upstream**; see [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md). Custom “pixel” car UI belongs to a **different** path (Car App Library templates) if the product ever goes there.
2. **Tasks that matter** — **Play/pause**, **skip**, and **getting back to something familiar** carry the least load while moving. **Deep browsing** and **typing-heavy search** are the expensive ones ([02-task-analysis-and-flows.md](02-task-analysis-and-flows.md)).
3. **Safety** — Google’s **car quality** and **distraction safeguards** are the practical gates for shipping. Academic and government references in our notes are **supporting context**, not a substitute for Play review ([04-distraction-brief.md](04-distraction-brief.md), [05-design-guardrails-checklist.md](05-design-guardrails-checklist.md)).
4. **Accessibility** — On the **phone**, invest in TalkBack, scaling, and contrast. On **Auto**, you mostly influence **labels**, **stable IDs**, and **voice** — the host owns the chrome ([06-accessibility-matrix.md](06-accessibility-matrix.md)).
5. **Look and feel** — **Phone:** PowerAmpache2Theme (Material 3, Nunito, optional dynamic color). **Auto:** the car’s UI; you supply **content**, not a teal skin ([../design-system/00-design-system-index.md](../design-system/00-design-system-index.md)).

## Suggested next steps

- Put **continue listening**, **recents**, and **playlists** near the **top** of browse before long artist → album → track paths.
- Exercise **voice** and **error** paths on the **DHU** and, when you can, a real car.
- Label design work by surface: **phone (PA2 theme)** vs **Auto (host media)**. Full handheld UI lives in **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)**.

## Where to read more

- [07-research-synthesis.md](07-research-synthesis.md) — tradeoffs and open questions  
- [08-prototype-handoff-package.md](08-prototype-handoff-package.md) — scenario priorities (P0–P3)  
- [MVP user stories](../user-stories.md)  
- [Index of all UX docs](README.md)

