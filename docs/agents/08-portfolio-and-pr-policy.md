# Portfolio repo, fork workflow, and PR policy (maintainer-aligned)

Normative for **this workspace’s owner** when coordinating **`pa2-auto`** (umbrella), **`PowerAmpache2PluginTemplate`** (fork), and **icefields** upstream. Does not replace maintainer rules in **`icefields/PowerAmpache2PluginTemplate`** — it constrains **what we push** and **how agents behave**.

---

## Two-repo mental model

| Repo / tree | Role |
|-------------|------|
| **Umbrella** (e.g. **`pa2-auto`**, or this root without nested clones committed) | Portfolio: **`mockup/`**, **`docs/`**, **`android-auto-agents/`**, scripts, CV-facing **`main`**. |
| **Plugin fork** (nested **`PowerAmpache2PluginTemplate/`** → **`origin`**) | Real **Gradle** project: Android Auto / Media3 work; branch **`plugin/AndroidAuto`** (and/or **`dev`**) for integration and **PRs to icefields**. |

**`upstream`** is a **remote URL** — typically **`icefields/PowerAmpache2PluginTemplate`**. It does **not** “point at” a filesystem path. Sync using **branch names** (e.g. **`plugin/AndroidAuto`**, **`main`**) via `git fetch upstream` and merge/rebase the branch you intend to track.

---

## What goes to icefields (when you open a PR)

- **In scope:** **`PowerAmpache2PluginTemplate/app/`** presentation layer — Auto / Media3 UI, mocks, tests that belong with that module — per maintainer boundaries.
- **Out of scope for upstream PRs:** Umbrella-only assets (**mockup** as a web app, **`pa2-auto`** portfolio-only docs, MCP packaging) unless the maintainer explicitly asks — **do not** land those as part of “plugin UI” PRs by default.

---

## Agent and automation rules (owner)

| Rule | Detail |
|------|--------|
| **`data`/`domain`** | **Do not** change unless **you or the maintainer** explicitly scopes it. Use **mocks** and **`domain/.../model/mocks/`** for presentation. |
| **GitHub comments** | **No** new comments on **your** or **icefields** issues **without your prior yes** — especially if the issue body does not already mention the topic. |
| **Root Android module** | **Removed** from this umbrella repo — **DHU** targets the **nested plugin** or **PA2** APK; Gradle only under **`PowerAmpache2PluginTemplate/`** via **`PA2_PLUGIN_GRADLE_ROOT`**. |
| **Tests** | Define an **integration vs acceptance matrix** before adding broad automation; **scripted** steps (install, **`dhu-start.sh`**, adb) may run via **`android-auto-agents/`**; **subjective car UX** remains **human acceptance** unless you add explicit instrumented checks. |
| **PA2-Theme** | **Read-only** — consume tokens (copy/import); **no** commits inside a fresh **PA2-Theme** upstream tree. |
| **Mockup rebuild** | After **GitHub umbrella/fork merges** are in a good state (owner workflow). |

---

## Salvage and cleanup (later)

- Prefer **UI/UX** in **`app/`**; **legacy `data`/`domain`** experiments may stay **documented on the fork** (e.g. **`dev`** branch) for history — not as default **upstream** direction unless the maintainer adopts them.
- Merging **`pa2-auto`** (umbrella) **into your fork** for a single clone is allowed **for your workflow**; verify **no secrets** before push; remove sensitive local copies **after** **`origin`** is safe **and** you have confirmed no credential leaks.

---

## Related

- [`01-prd-and-backlog.md`](01-prd-and-backlog.md) — backlog table; update when branching story changes.
- [`05-integrity-and-tests.md`](05-integrity-and-tests.md) — proof and test honesty.
