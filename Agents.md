# Agents — Power Ampache 2 plugin (this fork)

This document orients AI and human contributors. Update it when workflows change.

## Repository and branches

- **Upstream template (maintainer):** `icefields/PA2PluginTemplate` on GitHub. Use it as the source of truth for template updates. If `git fetch` fails with “repository not found,” the repo is likely private: authenticate (HTTPS token or SSH) or ask the maintainer for access.
- **This remote:** `origin` — develop feature work here unless directed otherwise.
- **`main`:** Reserved as the **portfolio / GitHub Pages** presentation branch. Do not treat it as the day-to-day integration branch unless the maintainer says otherwise.
- **`dev`:** Primary **development** branch for app implementation. **Do not delete** historical or topic branches; keep them for reference.
- **Other branches:** e.g. `plugin/AndroidAuto` — preserve; do not discard without explicit maintainer approval.

## Scope and boundaries

- **Implementation scope:** Respect agreed boundaries; for this project, **stay within `app/`** unless the maintainer expands scope.
- **Architecture:** Agents **assist with coding** but **do not make architectural decisions** without explicit human approval. Propose options; the human chooses.
- **Theme / UI:** Use **PA2Theme** (`PowerAmpache2Theme` submodule) for UI and mockups. Align Figma and implementation with those tokens and components.
- **Code freeze:** **Do not write or land application code** until the product owner explicitly says to implement. Requirements and design come first.

## Requirements and design process

1. Elicit **user stories**, **functional requirements**, and **non-functional requirements** (see prompts in maintainer comms or project docs).
2. Iterate until the product owner is satisfied.
3. Produce an **executive summary** that confirms alignment before build-out.
4. **Use cases:** Prior agents may have relied on **example** or placeholder flows. Treat maintainer-provided **real** use cases as authoritative. Do **not** implement features solely because examples show them; **remove or replace** misleading examples when implementation begins, in favor of the real use cases.

## Tracking

- **GitHub Projects:** A Kanban board exists for all agents — keep issues/cards in sync with actual work.

## Testing builds

- A **developer-supplied APK** is available for manual testing; use it to validate behavior without blocking on local builds when appropriate.

## Tooling: Android Auto and MCP

- **Android Auto dev MCP:** There is **no** Android Auto–specific MCP configured in this workspace by default. Available MCP integrations (when enabled in Cursor) include Convex, Figma, Hugging Face, Svelte, tldraw, etc. For Android Auto, rely on **Android Studio**, **official Android for Cars** docs, and the **dev APK**. If an Android Auto MCP is added later, document its server name and purpose here.

## Figma and mockups

- Mockups should be **live-demo style** (interactive or clearly navigable flows) and driven by **approved user stories**, using **PA2Theme** visual language.
- A separate prompt is maintained for a **Figma MCP coding agent** to generate those mockups after requirements stabilize.

---

*Last updated: 2026-04-07*
