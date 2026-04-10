# PRD backlog and workspace snapshot

**Single backlog owner:** keep this table aligned with checked-in behaviour. After large merges, the **coordinator** should reconcile rows with a fresh **`main`** checkout and this **`mockups`** branch.

**Portfolio / fork / icefields PR policy:** [`08-portfolio-and-pr-policy.md`](08-portfolio-and-pr-policy.md).

## Repository layout (single fork)

- **One repository:** **PowerAmpache2PluginTemplate** (your fork, e.g. **shahzebqazi/PowerAmpache2PluginTemplate**). **`origin`** → fork, **`upstream`** → **icefields/PowerAmpache2PluginTemplate** (canonical maintainer repo name).
- **`main`:** Kotlin, Gradle, **`app/`**, **`data/`**, **`domain/`**, **`PowerAmpache2Theme/`** — default integration branch for **implementation** and PRs toward **upstream**.
- **`mockups`:** This branch — **`docs/`**, **`mockups/`**, **`android-auto-agents/`**, MkDocs / Pages. **No** application source committed here.
- **Feature branches:** e.g. **`cursor-cloud/<topic>-1b3a`** — create from **`main`** for scoped work; open PRs to **`main`** on the fork, then to **upstream** when appropriate.
- **`PowerAmpache2PluginTemplateOld/`** (if present locally) — quarantined history. **Do not** cite as product truth without human review.
- **Boundaries:** default **plugin `app/` only** unless the task explicitly includes **`data`**, **`domain`**, or Gradle root changes.
- **Mockups:** handheld / full-player scenarios frame **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)** as the **phone app**. The **plugin** APK is **Android Auto / Media3** — **host-rendered** car UI; mocks illustrate **browse / IA / metadata**, not a custom car player (see [`03-mockups-and-design.md`](03-mockups-and-design.md)).

## Syncing `main` with upstream

```bash
git fetch upstream
git checkout main
git merge upstream/main   # or rebase, per team preference; resolve conflicts, then:
git push origin main
```

Open PRs **from your fork** to **icefields** when ready (typically from **`main`** or a feature branch off **`main`**).

**New machine:** `gh repo fork icefields/PowerAmpache2PluginTemplate --clone=true`, then:

`git remote add upstream git@github.com:icefields/PowerAmpache2PluginTemplate.git` (or HTTPS). Run **`git submodule update --init`** for **PowerAmpache2Theme**.

## PRD backlog (status — reset pass)

| ID | Task | Owner / tree | Status |
|----|------|----------------|--------|
| P0-0 | Reconcile **mockups** + **main** — PA2 phone vs plugin Auto (host media) | **`mockups/web-mockup/`**, **`docs/`**, **`main`** **`app/`** | **In progress** — align copy and frames with host-rendered Auto. |
| P0-1 | Media3 Auto checklist (browse, session, artwork, errors, DHU) | **Power-Ampache-2** | **Upstream-only** when that repo is not in tree. |
| P0-2 | Browsable `MediaItem` tree vs product | **Power-Ampache-2** + docs | **Upstream-only** for code. |
| P0-3 | Plugin: playback + browse + DHU | **`main`** **`app/`** (+ explicit scope only for **`data`/`domain`**) | **Reset** — verify against fresh **`upstream/main`** merge; do not assume **Old** behaviour. |
| P1+ | Prior P1–P3 rows from older AGENTS era | — | **Re-triage** after baseline stabilizes. |

## Workspace snapshot

Update when **`main`** + **`mockups`** stabilize.

| Area | Status |
|------|--------|
| **Policy / workflow** | [`08-portfolio-and-pr-policy.md`](08-portfolio-and-pr-policy.md). |
| **`mockups` branch** | Docs, **`mockups/web-mockup/`**, **`android-auto-agents/`** — **no** Gradle **`app/`** at this root. |
| **`main` branch** | Full template: **SongList** / plugin shell, **`domain`** mocks, **`MusicFetcher`**, **`AndroidAutoMediaLibraryService`**, theme submodule — verify paths after each **`upstream`** pull. |
| **DHU** | Build/install from a **`main`** checkout; set **`PA2_PLUGIN_GRADLE_ROOT`** to that tree when driving scripts from **`mockups`**. |

---

*Last updated: 2026-04-10 — second pass: single-repo `main` + `mockups` + feature branches.*
