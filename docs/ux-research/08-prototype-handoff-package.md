# Android Auto — prototypes and what to test first

**Who this is for:** Engineers shipping Media3 on Power Ampache 2, and anyone making **wireframes** or **Figma** for the car.

**Before you draw car screens:** Read [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md). On Android Auto you **do not** theme the head unit like the phone — you supply **content and session behaviour** (tree, metadata, artwork, errors, voice).

**Verify in code:** Scenarios **P1–P2** assume a **browsable `MediaItem` tree** on the head unit, per [Create audio media apps](https://developer.android.com/training/cars/media). Confirm in **`dev`** / **Power-Ampache-2** before prototypes are treated as **final** — this branch has no source.

**Frame labels and review checklists:** [../agents/prototype-and-engineering.md](../agents/prototype-and-engineering.md).

## Fidelity and honesty

- **Illustration vs proof:** Prototypes show **information hierarchy**, **copy length**, and **which scenarios** you cover (P0–P3). They do **not** satisfy [Android app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality) or [distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards) by themselves — proof stays in **Kotlin / Media3**, **DHU**, and **devices**.
- **Car frames:** Use **host-like** neutral chrome. Add a **neutral badge** so previews are not mistaken for PA2-branded head units.
- **Motion:** Respect **`prefers-reduced-motion`** in web previews; keep car concepts **static** unless motion is essential.

## 1. Scenarios — build in this order

| Priority | Scenario | Success criteria |
|----------|----------|------------------|
| **P0** | **Now playing** — play/pause, next/prev, scrub if the host allows | Readable at a glance; matches session metadata |
| **P0** | **Resume** last playback | ≤1 browse step from root |
| **P1** | **Root browse** — playlists + recents | ≤2 steps to start playback on the happy path |
| **P1** | **Voice play** (“Play playlist X”) | `MediaSession` voice path works |
| **P2** | **Artist → album → track** | Works but **not** the default driving path |
| **P2** | **Error** — offline / server unreachable | Short copy; recovery without a deep menu |
| **P3** | **Queue** view | Read-mostly; minimal edit |

## 2. Guardrails in prototypes

Pull from [05-design-guardrails-checklist.md](05-design-guardrails-checklist.md) — especially **G3–G7**, **G10**.

## 3. What prototypes should include

- Wireframes or high-fidelity frames for **P0–P1** at minimum.
- **Mermaid** or numbered flows that match [02-task-analysis-and-flows.md](02-task-analysis-and-flows.md).
- A short **note** if the design diverges from media-browser constraints.

---

*Last reviewed: 2026-04-07*
