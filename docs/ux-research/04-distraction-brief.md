# Distraction brief — secondary tasks and in-vehicle music UI

**Purpose:** Summarize evidence and **normative platform** requirements relevant to **consumer** music apps on **Android Auto**, and relate them to Power Ampache 2. Full citations live in [bibliography.md](bibliography.md); add peer-reviewed DOIs as the literature pass deepens.

---

## Executive summary

- **Secondary tasks** (visual, manual, cognitive) compete with driving; music apps are lower risk than visual search or messaging but **browse depth** and **manual entry** still matter.
- **Google** enforces **distraction safeguards** and **Play quality** rules for car media apps; these are **hard constraints** for shipping ([Implement distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards), [App quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality)).
- **NHTSA** publishes **driver-distraction** research and (separately) **visual-manual** task guidance; use the **specific guideline revision** you cite—do not treat the general [distracted-driving hub](https://www.nhtsa.gov/research-data/distracted-driving) as if it were one numbered rule set. NHTSA material is **not** a substitute for OEM certification or Google Play review.
- **Design response:** Prefer **shallow hierarchies**, **voice** for search, **predictable now-playing controls**, and **minimal metadata** while moving ([android-auto-ux-research-plan.md](../android-auto-ux-research-plan.md), design response section).

---

## Secondary task demand (framework)

| Channel | Examples in music apps | Typical concern |
|---------|------------------------|-----------------|
| **Visual** | Reading titles, album lists, queue | Glance time, frequency |
| **Manual** | Taps, scrolls, rotary nudges | Time off wheel, sequence length |
| **Cognitive** | Choosing among many browse nodes | Working memory, decision time |

**Research direction:** Prefer systematic reviews on **in-vehicle information systems** and **music selection**; note vehicle and UI generation (pre-touchscreen studies may not generalize). Add 3–5 anchored citations in the next revision.

---

## Regulatory and industry context (consumer app)

- **United States:** Use NHTSA’s **specific** visual-manual / distraction publications (linked from their hub) for heuristic targets; the hub URL alone is not a citation for a particular threshold.
- **European Union:** High-level road-safety policy (e.g. [EU Road Safety](https://road-safety.transport.ec.europa.eu/index_en)) informs **ethos** only—not compliance sign-off for Android Auto.
- **Google Play:** **Android app quality for cars** is the practical gate for Auto distribution.

---

## Platform-specific: Android Auto media

Google states that driver distraction is taken seriously and apps must meet **design requirements** for Play listing ([Cars media overview](https://developer.android.com/training/cars/media)). The **media browser** model moves much UI to the **host**, which **reduces** arbitrary custom layouts but **does not** remove browse/scroll risk.

---

## Implications for Power Ampache 2

| Area | Implication |
|------|-------------|
| Browse tree | Favor **wide roots** (recents, playlists) over deep taxonomies as default car entry. |
| Search | **Voice-first**; discourage keyboard-heavy flows for moving vehicle. |
| Metadata | Short **title / artist** strings; avoid long descriptions on browse rows. |
| Errors | Short, actionable messages; avoid multi-step recovery in motion. |
| Queue | Treat as **secondary**; complex edits on phone. |

---

## Gaps and hypotheses

- **Gap:** Peer-reviewed citations not yet inlined in this brief (placeholder for systematic pass).
- **Hypothesis:** Users accept **fewer features** on Auto if **T1–T2** (play/pause/skip) are flawless.
- **Contradiction risk:** “Rich library browsing” vs “shallow hierarchy” — resolve per [07-research-synthesis.md](07-research-synthesis.md).

---

## Related artifacts

- [05-design-guardrails-checklist.md](05-design-guardrails-checklist.md) — operational rules.
- [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md) — allowed/forbidden summary.
