# Executive summary — UX research (Android Auto + Power Ampache 2)

**Date:** 2026-03-30  
**Audience:** Maintainers, design, Android engineers.

## Purpose

Summarize an **evidence-oriented** baseline for **safe, low-friction** music use on **Android Auto**, aligned with **Google’s media-for-cars** guidance. **Shipping** host media is **Power Ampache 2** with **Media3** (`MediaSession` + `MediaSessionService` and media-browser discovery in the manifest — confirm **browse** depth in upstream; see [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md)). This workspace’s root **`app`** is a **phone Compose** reference only; **PowerAmpache2PluginTemplate** is **phone** + local **ExoPlayer**, **not** an Auto media app in manifest today. **Android Auto** UI is **host-rendered** from **Power-Ampache-2**’s **Media3** integration.

## Key findings

1. **Platform path:** Upstream PA2 uses **Media3** `MediaSessionService` (`SimpleMediaService`) and a **`MediaSession`** wired to the player. For **Android Auto**, Google’s **host-rendered** media UI applies once the app fully satisfies the **media browser / library** contract ([01-platform-constraint-sheet.md](01-platform-constraint-sheet.md) — including the **code-scan caveat** on browse implementation). Custom pixel-level car UI belongs to **templates** only if the product **migrates** to the Cars App Library path.

2. **Tasks that matter:** **Play/pause**, **skip**, and **resume** are the critical low-load tasks; **deep browse** and **typed search** carry **high** glance and manual demand ([02-task-analysis-and-flows.md](02-task-analysis-and-flows.md)).

3. **Safety:** **Google Play car quality** and **distraction safeguards** are the binding gates; academic and NHTSA-style references inform **heuristics** but do not replace store review ([04-distraction-brief.md](04-distraction-brief.md), [05-design-guardrails-checklist.md](05-design-guardrails-checklist.md)).

4. **Accessibility:** Strong **TalkBack** and scaling work on **phone**; on **Auto**, accessibility is **largely host-owned** — focus on **clear labels**, **stable IDs**, and **voice** ([06-accessibility-matrix.md](06-accessibility-matrix.md)).

5. **Visual identity:** **Phone** UI follows **PowerAmpache2Theme** (Material 3 + optional **Material You** + Nunito). **Auto** chrome does **not** use PA2 colors directly ([../design-system/00-design-system-index.md](../design-system/00-design-system-index.md)).

## Recommended next steps

- Prioritize browse roots: **continue listening**, **recents**, **playlists** before deep **artist → album** paths.
- Exercise **voice** flows and **error** surfacing on the **Desktop Head Unit** and, when possible, a real vehicle.
- Label mockups by surface: **phone (PA2 theme)** vs **host media** vs **plugin Auto** — this repo has **no** root Compose app (use **mockup/** and **Power-Ampache-2** for phone reference).

## Full detail

- [07-research-synthesis.md](07-research-synthesis.md)  
- [08-mockup-handoff-package.md](08-mockup-handoff-package.md)  
- Index: [README.md](README.md)
