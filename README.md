# Power Ampache 2 — Android Auto (design & research)

This **`mockups`** branch holds **documentation and design assets** — UX research, design-system notes, user stories, **static web mockups**, the **android-auto-agents** pack (scripts + MCP tool descriptors), and files under `mockups/`.

**Application code** (Kotlin, Gradle, `app/`, `data/`, `domain/`) lives on **`main`**, not here. The **browser mockup** under `mockups/web-mockup/` illustrates **browse / queue / error** patterns only — it does **not** represent a custom car-side player (the **head unit** renders now playing; the app supplies **Media3** data and session).

- **Docs index:** [docs/README.md](docs/README.md)
- **Contributor / AI rules:** [Agents.md](Agents.md)
- **Android Auto agents (DHU, Gradle, MCP stubs):** [android-auto-agents/README.md](android-auto-agents/README.md)
- **Browser mockup (Svelte):** [mockups/web-mockup/README.md](mockups/web-mockup/README.md)
- **Hero / DHU reference PNGs:** [mockups/assets/](mockups/assets/) (e.g. `hero-android-auto.png`)

---

*Branch: `mockups` — design & research; use `main` for implementation.*
