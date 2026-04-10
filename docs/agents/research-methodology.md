# How the Android Auto UX research was run

This is the **process** behind the notes in [docs/ux-research/](../ux-research/README.md). Readers who only need conclusions can skip this file.

## What we set out to do

- Understand **what drivers actually do** in the car with a music app (glance time, taps, voice).
- Map **Google’s rules** for media on Android Auto (what the app may do vs what the head unit draws).
- Split **phone** (PowerAmpache2Theme, touch, TalkBack) from **Auto** (host UI, session metadata, voice).
- Capture **safety and distraction** context without pretending a doc replaces Play review or OEM certification.

## Phases (A–E)

| Phase | Focus | Main outputs |
|-------|--------|----------------|
| **A** | Platform | Constraint sheet, bibliography |
| **B** | Driver tasks and music flows | Task list (T1–T5), flow diagrams |
| **C** | Distraction | Short brief + guardrail checklist |
| **D** | Accessibility | Phone vs Auto matrix |
| **E** | Synthesis | One narrative doc + prototype priorities |

## Principles we used

1. **Glance first** — less time staring at lists is better.
2. **Shallow trees** — fewer taps from “open” to “playing” when the driver is moving.
3. **Trust the platform** — Auto’s media browser model is real; design for **content and session**, not custom head-unit chrome unless you change product path.
4. **Cite normative sources** — Google’s car media and Play quality docs for hard rules; label the rest as judgment or hypothesis.

## Methods

- Desk research and official Android / Google Play documentation.
- Task analysis for critical flows (resume, skip, shallow browse, voice search).
- Light comparison of **public** patterns (marketing and help pages — no scraping competitor apps).

## Deliverables checklist (maintenance)

- [x] Executive summary
- [x] Platform constraint sheet
- [x] Task / flow analysis
- [x] Distraction brief + guardrails checklist
- [x] Accessibility matrix
- [x] Synthesis + prototype handoff
- [ ] Peer-reviewed DOIs inlined in distraction brief (optional deeper pass)

## Risks we called out

| Risk | Mitigation |
|------|------------|
| Designing for phone and ignoring Auto | Read the constraint sheet before prototyping |
| Old distraction studies | Prefer recent reviews; note when a source is weak |
| “More text = more accessible” in the car | Matrix + guardrails cap metadata in motion |
| Only testing on DHU | Note limitations; verify on a real car when possible |

## Starter links (verify before each release)

- [Android for Cars — media](https://developer.android.com/training/cars/media)
- [Test Android apps for cars](https://developer.android.com/training/cars/testing) (DHU)
- [Android Auto (product)](https://www.android.com/auto/)

---

*Last updated: 2026-04-07*
