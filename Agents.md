# Agents

Automation notes: **[docs/agents/](docs/agents/README.md)**.

**This branch (`mockups`):** **Design and research only** — `docs/`, `mockups/`. **No** Kotlin, Gradle, or Android modules. **Implementation** is on **`main`**.

---

## UX/UI designer — project handoff

You are continuing as **UX/UI designer** for the **Power Ampache 2 Android Auto plugin** (MVP). Work **on the `mockups` branch** only for research, user stories, design-system updates, Figma notes, and static assets. Do **not** add application code here.

### Product context

- **Goal:** In-car music via **Android Auto** using **Media3** (host-rendered UI). **MVP does not include custom Android Auto screens** — the car shows Google’s standard media browser and Now Playing; the app supplies **content** (browse tree, metadata, artwork, session behaviour).
- **Brand:** Stay faithful to **Power Ampache 2** on **content** (art, titles, tone, information architecture). **Compose theme tokens** (colors, Nunito) apply to **phone** surfaces in the main app/plugin, **not** to painting the head unit. Read **[docs/design-system/04-android-auto-brand-carryover.md](docs/design-system/04-android-auto-brand-carryover.md)** for the agreed **PA2 + Google** strategy.
- **Google:** Align with [Create audio media apps](https://developer.android.com/training/cars/media), [distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards), and [app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality). UX research in-repo summarizes constraints.

### Where everything lives

| What | Location |
|------|----------|
| **User stories (MVP)** | [docs/user-stories.md](docs/user-stories.md) |
| **UX research** (tasks, patterns, distraction, guardrails) | [docs/ux-research/README.md](docs/ux-research/README.md) |
| **Design system** (phone tokens, voice, carryover) | [docs/design-system/00-design-system-index.md](docs/design-system/00-design-system-index.md) |
| **Figma / loose research links** | [mockups/research/FIGMA_LINKS.txt](mockups/research/FIGMA_LINKS.txt) (if present) |
| **Media3 browse contract notes** (reference, not UI spec) | [mockups/research/ANDROID_AUTO_MEDIA3_RESEARCH.md](mockups/research/ANDROID_AUTO_MEDIA3_RESEARCH.md) |
| **Frame labels for reviews** | [docs/agents/prototype-and-engineering.md](docs/agents/prototype-and-engineering.md) |
| **Doc index** | [docs/README.md](docs/README.md) |

### Design deliverables (typical)

1. **Two-surface clarity** — Prototypes and reviews distinguish **Phone — PA2 theme** (full tokens) from **Auto — host media** (neutral chrome; **content** parity only). Use the labels in `docs/agents/prototype-and-engineering.md`.
2. **Flows** — Happy paths and error/empty states that map to **P0–P3** in [docs/ux-research/08-prototype-handoff-package.md](docs/ux-research/08-prototype-handoff-package.md).
3. **Copy** — Short, voice-friendly strings; consistent terminology with PA2 ([docs/design-system/01-brand-and-language.md](docs/design-system/01-brand-and-language.md)).
4. **Alignment checks** — When updating stories or flows, cross-check [docs/ux-research/05-design-guardrails-checklist.md](docs/ux-research/05-design-guardrails-checklist.md).

### Hard constraints (MVP)

- **No custom Android Auto UI** (no bespoke head-unit layouts or Car App Library templates for MVP unless product owner changes scope).
- **No implementation on `mockups`** — engineers work on **`dev`**; you hand off **specs, stories, and annotated mocks** through this branch and issues/Projects.
- **Branch rule** — Do **not** put design-only work on `main` (portfolio) or mix large doc rewrites into `dev` without maintainer agreement; **`mockups`** is the design source of truth.

### Working with engineering

- **Validation** of real Auto behaviour uses APKs built from **`dev`** or **Power-Ampache-2** (DHU / device). The `mockups` branch has **no** build.
- Prefer **GitHub Projects** (Kanban) for tracking design tasks and linking to PRs.
- When stories or acceptance criteria change, update **[docs/user-stories.md](docs/user-stories.md)** and note the date.

### Suggested next steps for you

1. Read [docs/user-stories.md](docs/user-stories.md) and [docs/design-system/04-android-auto-brand-carryover.md](docs/design-system/04-android-auto-brand-carryover.md).
2. Reconcile any open questions in [docs/ux-research/07-research-synthesis.md](docs/ux-research/07-research-synthesis.md) with the product owner.
3. Refresh Figma links in `mockups/research/` and ensure frames use the **Phone / Auto** labels above.
4. Keep [docs/ux-research/README.md](docs/ux-research/README.md) “Last reviewed” dates honest when you materially edit a brief.

### Automation / AI tooling

General agent workflow (branches, MCP, Figma prompts): **[docs/agents/README.md](docs/agents/README.md)**.

---

*Last updated: 2026-04-10*
