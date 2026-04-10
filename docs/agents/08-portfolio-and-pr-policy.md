# Fork workflow and PR policy (maintainer-aligned)

Normative for **this fork** when coordinating **`main`** (implementation), **`mockups`** (design / research / agent scripts on this branch), and **icefields** **upstream**. Does not replace maintainer rules in **icefields/PowerAmpache2PluginTemplate** — it constrains **what we push** and **how agents behave**.

---

## Single repository, two integration branches

| Branch | Role |
|--------|------|
| **`main`** | **Gradle project at repo root** — Kotlin, **`app/`**, **`data/`**, **`domain/`**, **`PowerAmpache2Theme/`**. PRs toward **upstream** and day-to-day plugin work. |
| **`mockups`** | **Docs + design assets only** — `docs/`, `mockups/`, `android-auto-agents/`, MkDocs. **No** duplicate nested clone; no **`app/`** source on this branch. |

**Feature branches** (e.g. **`cursor-cloud/<topic>-1b3a`**) branch from **`main`** for scoped implementation; merge to **`main`** first, then upstream when ready.

**`upstream`** is a **remote** — typically **`icefields/PowerAmpache2PluginTemplate`**. Sync with **`git fetch upstream`** and merge/rebase **`upstream/main`** into your **`main`**.

---

## What goes to icefields (when you open a PR)

- **In scope:** **`app/`** presentation layer — Auto / Media3, mocks, tests that belong with that module — per maintainer boundaries.
- **Out of scope for upstream PRs by default:** **`mockups`**-only assets (MkDocs site, **`mockups/web-mockup`**, portfolio docs, MCP descriptor packaging) unless the maintainer explicitly asks — **do not** land those as part of “plugin UI” PRs unless coordinated.

---

## Agent and automation rules (owner)

| Rule | Detail |
|------|--------|
| **`data`/`domain`** | **Do not** change unless **you or the maintainer** explicitly scopes it. Use **mocks** and **`domain/.../model/mocks/`** for presentation. |
| **GitHub comments** | **No** new comments on **your** or **icefields** issues **without your prior yes** — especially if the issue body does not already mention the topic. |
| **DHU / Gradle** | On **`mockups`**, there is **no** **`./gradlew`** at root — build from a **`main`** checkout. **`PA2_PLUGIN_GRADLE_ROOT`** may point at that path when running **`android-auto-agents/scripts/`** from **`mockups`**. |
| **Tests** | Define **integration vs acceptance** before broad automation; **scripted** steps (install, **`dhu-start.sh`**, adb) via **`android-auto-agents/`**; subjective car UX remains **human acceptance** unless you add explicit checks. |
| **PA2-Theme** | **Read-only** upstream submodule — consume tokens; **no** stray commits inside a fresh **PowerAmpache2Theme** clone unless maintainer process says otherwise. |

---

## Salvage and cleanup

- Prefer **UI/UX** in **`app/`** on **`main`**; legacy **`data`/`domain`** experiments may remain on **old branches** for history — not default **upstream** direction unless adopted.
- If you maintain **two local checkouts** (one on **`main`**, one on **`mockups`**) or use **git worktree**, keep **secrets** out of both; verify **no** credentials in tracked files.

---

## Related

- [`01-prd-and-backlog.md`](01-prd-and-backlog.md) — backlog table.
- [`05-integrity-and-tests.md`](05-integrity-and-tests.md) — proof and test honesty.

---

*Last updated: 2026-04-10 — second pass: removed umbrella / nested-clone / `dev` / `plugin/AndroidAuto` model.*
