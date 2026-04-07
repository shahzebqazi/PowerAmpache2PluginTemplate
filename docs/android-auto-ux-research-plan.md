# UX/UI research plan — Android Auto (driver UX, accessibility, safety)

This plan supports building an in-car experience that is **usable while driving**, **accessible**, and **aligned with distraction and platform constraints**. It matches the **UX/UI researcher** role in `AGENTS.md`: evidence-backed briefs, citations, and design implications—not untested opinion.

---

## 1. Goals and scope

| Goal | What “done” looks like |
|------|-------------------------|
| **Driver UX** | Document primary driving tasks (glance, one-hand, voice), information hierarchy for music apps, and how Android Auto shapes browse vs. now-playing vs. queue—so design decisions map to real driving constraints. |
| **Accessibility** | A clear split between **phone companion UI** (TalkBack, scaling, contrast) and **projected / automotive surfaces** (what OEMs and Google support, rotary input, limited customization); gaps called out explicitly. |
| **Safety** | A traceable set of **guidelines and research** on visual/manual/cognitive demand while driving, tied to **what your stack allows** (templates, interaction limits, no unsafe patterns). |

**In scope:** Music/media browsing and playback in Android Auto–style contexts, Power Ampache–like flows (library, search, queue, offline where relevant).

**Out of scope (unless product demands):** Full regulatory compliance sign-off; OEM-specific certification; navigation or messaging apps (different distraction profiles).

---

## 2. Research principles (non-negotiables)

1. **Glanceability first** — Time-on-task and eyes-off-road are the core metrics; favor summaries over dense lists.
2. **Shallow hierarchies** — Prefer fewer steps to play/skip/resume; document maximum acceptable depth with evidence.
3. **Platform truth** — Android Auto / Automotive OS impose **templates and limits**; research must converge with [Android for Cars](https://developer.android.com/training/cars) documentation, not idealized mobile UI.
4. **Accessibility without compromising safety** — More information density is not “more accessible” in a moving vehicle; document **reduced motion**, **large targets**, and **voice** where platform allows.
5. **Cite everything normative** — Guidelines, standards, and peer-reviewed work get full citations; hypotheses are labeled as such.

---

## 3. Phase overview

| Phase | Focus | Main outputs |
|-------|--------|----------------|
| **A** | Foundations & platform | Constraint map (Auto vs. phone), glossary, source bibliography |
| **B** | Driver task & music UX | Task analysis, competitive/ pattern scan, screen-level implications |
| **C** | Safety & cognitive load | Distraction research synthesis, design guardrails checklist |
| **D** | Accessibility | Automotive + mobile a11y matrix, gaps for projected UI |
| **E** | Synthesis & handoff | Design implications doc, risks, open questions for mockups/engineering |

Phases **B–D** can overlap once Phase **A** is far enough to avoid rework.

---

## 4. Phase A — Foundations and platform constraints

**Questions to answer**

- Which integration path applies: **Media3 / MediaBrowser** (standard music UI on Auto) vs. **Car App Library** (custom templates)? How does that cap UI freedom?
- What are **template types** (e.g. list, grid, pane), **row limits**, **image rules**, and **banned interactions** for your category (e.g. media)?
- How does **Desktop Head Unit (DHU)** testing differ from real head units (latency, input, day/night)?

**Activities**

- Read official **Android for Cars** / **Android Auto** developer docs end-to-end for your chosen API path.
- Build a one-page **constraint map**: “Allowed / discouraged / forbidden” for your app.

**Artifacts**

- Annotated bibliography (URLs + last-reviewed date).
- **Platform constraint sheet** (single source of truth for designers and devs).

---

## 5. Phase B — Driver UX and music-specific flows

**Questions to answer**

- What are the **top 3–5 tasks** while driving (e.g. resume playlist, skip, pick album from recents)? Time and steps per task?
- How do successful **music apps on Auto** structure **browse** (artists → albums → tracks) vs. **search** vs. **queue**?
- What belongs on **now playing** vs. deeper screens? When is **voice** the safer path?

**Activities**

- **Task analysis** (CTT or hierarchical task analysis) for each critical flow; note **eyes-busy** vs. **hands-busy** demands.
- **Pattern scan**: document patterns from a small set of mainstream music apps on Auto (behavior, not pixel copying)—with screenshots only if terms of service allow; prefer public marketing / doc material.
- Map flows to **your** features: Ampache library, playlists, offline, search.

**Artifacts**

- Task flow diagrams (mermaid or structured lists).
- **Music Auto UX pattern table**: flow, typical depth, primary risk, mitigation (e.g. voice, larger tap targets on stop).

---

## 6. Phase C — Safety and distraction

**Questions to answer**

- What does research say about **secondary task demand** (visual, manual, cognitive) for in-vehicle interfaces?
- What **quantitative or qualitative** thresholds are cited (e.g. interaction time, glance frequency)—and how do they translate to **design rules** (e.g. cap list length per screen)?
- What are **regional or industry** references (e.g. NHTSA visual-manual guidelines, EU dialogue on distraction) relevant to **consumer** apps?

**Activities**

- Systematic skim of **peer-reviewed** and **standards-body** sources; prioritize meta-analyses and recent reviews where possible.
- Produce a **design guardrails** list: max recommended taps to play, when to forbid typing, when to prefer voice—each tied to a source or marked “team assumption.”

**Artifacts**

- Distraction brief (executive summary + full citations).
- **Guardrails checklist** (reviewable before each milestone).

---

## 7. Phase D — Accessibility

**Questions to answer**

- For **phone UI**: WCAG-aligned targets for touch, contrast, motion; TalkBack labels for player and queue.
- For **Android Auto / projected**: What **speech** and **focus** models exist? What is **not** controllable by third-party apps?
- **Motor**: Rotary controllers vs. touch—how do list templates behave?
- **Low vision / color**: Night driving and glare—contrast choices in the car vs. on phone.

**Activities**

- Cross-walk **WCAG 2.x** (where applicable to mobile app) with **automotive HMI** accessibility notes from Google/OEM public docs.
- Document **gaps**: e.g. where TalkBack on phone is strong but Auto has no equivalent.

**Artifacts**

- **Accessibility matrix**: surface (phone / Auto) × requirement × status (supported / partial / N/A).
- Short **recommendations** for engineering (content descriptions, focus order on phone; template choices for car).

---

## 8. Phase E — Synthesis and handoff

**Activities**

- Reconcile contradictions (e.g. “show more metadata” vs. “minimize glance time”) with explicit tradeoffs.
- Produce **design implications** bullets mapped to: **Now playing**, **Browse**, **Search**, **Queue**, **Settings (phone-only)**.
- List **open questions** for the maintainer (e.g. offline badges in browse, explicit content).

**Artifacts**

- Single **Research synthesis** (PDF or markdown) with executive summary ≤ 1 page.
- **Handoff package** for mockup developer: prioritized scenarios + guardrails + open questions.

---

## 9. Methods summary

| Method | Use for |
|--------|---------|
| Desk research + citations | Guidelines, standards, academic work |
| Platform doc review | Hard constraints |
| Task analysis | Driver-critical flows |
| Comparative pattern scan | Music-on-Auto conventions (ethical, public sources) |
| Expert heuristic review | Optional pass using NHTSA-inspired or similar heuristics (document the rubric) |

**Optional (later):** Scripted usability sessions in **parked** or **simulator** settings—not a substitute for regulatory validation.

---

## 10. Deliverables checklist (aligned with `AGENTS.md`)

- [ ] Executive summary (1 page)
- [ ] Full briefs with **citations** (title, publisher, date, URL)
- [ ] Platform constraint sheet
- [ ] Task / flow analysis for critical music tasks
- [ ] Distraction & guardrails brief + checklist
- [ ] Accessibility matrix (phone vs. Auto)
- [ ] Design implications per major surface
- [ ] Gaps, contradictions, and open questions called out explicitly

---

## 11. Risks and mitigations

| Risk | Mitigation |
|------|------------|
| Research optimizes phone UI and ignores Auto limits | Phase A constraint sheet; no mockup without it |
| Over-citing outdated pre-smartphone studies | Prefer reviews from the last ~10–15 years; note vehicle-UI evolution |
| Accessibility conflated with “more text on screen” | Phase D matrix; cap metadata in motion per guardrails |
| No access to real car hardware | DHU + document limitations; flag “verify on road” items |

---

## 12. Suggested starting URLs (verify current paths)

- [Android for Cars — App quality](https://developer.android.com/training/cars/apps) (overview and quality)
- [Android for Cars — Design for cars](https://developer.android.com/training/cars/design) (if applicable to your API)
- [Test Android apps for cars](https://developer.android.com/training/cars/testing) (DHU)
- [Android Auto — Google](https://www.android.com/auto/) (product context)

Replace or extend with **Media3** / **MediaBrowserService** docs if the upstream app uses the media browser path instead of Car App Library.

---

*Last updated: created with this workspace; revise dates and “last reviewed” when you complete each phase.*
