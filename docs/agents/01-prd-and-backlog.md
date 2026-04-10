# PRD backlog and workspace snapshot

**Single backlog owner:** keep this table aligned with checked-in behaviour. After large merges, the **coordinator** should reconcile rows with **`PowerAmpache2PluginTemplate/`** (fresh clone) and **`mockup/`**.

**Portfolio / fork / icefields PR policy** (umbrella vs nested plugin, `upstream` remotes, GitHub comment gate, PA2-Theme read-only): [`08-portfolio-and-pr-policy.md`](08-portfolio-and-pr-policy.md).

## Tech-lead reset (this workspace)

- **`PowerAmpache2PluginTemplate/`** — **your fork** cloned locally (**`origin`** = fork, **`upstream`** = **icefields/PowerAmpache2PluginTemplate**). Integration branch for this workspace: **`origin/plugin/AndroidAuto`** (merge **`upstream/main`** into it when you want maintainer `main`).
- **`PowerAmpache2PluginTemplateOld/`** — quarantined local history (AI-expanded **`data`/`domain`**, divergent Auto work). **Do not** cite it as product truth or copy architecture from it without human review.
- **Boundaries:** default **plugin template `app/` only** unless the task explicitly includes **`data`**, **`domain`**, or Gradle. Do not “helpfully” extend **`data`/`domain`** to unblock UI.
- **Mockups:** phone/full-handheld scenarios must frame **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)** as the **phone app**. The **plugin** is not a replacement phone shell in mocks; show **Android Auto / host media** (and plugin-specific car entry) where relevant.

## Fork + branch (`plugin/AndroidAuto`)

**Remotes:** **`origin`** → your fork (e.g. **shahzebqazi/PowerAmpache2PluginTemplate**). **`upstream`** → **icefields/PowerAmpache2PluginTemplate**.

**Daily work on the integration branch:**

```bash
cd PowerAmpache2PluginTemplate
git fetch upstream
git checkout plugin/AndroidAuto
git merge upstream/main   # bring maintainer main into your branch; resolve conflicts, then:
git push origin plugin/AndroidAuto
```

Open PRs **from your fork** to **icefields** when ready (source branch is often **`plugin/AndroidAuto`** or a feature branch off it).

**New machine:** fork on GitHub (or `gh repo fork icefields/PowerAmpache2PluginTemplate --clone=true`), then ensure **`upstream`** exists:  
`git remote add upstream git@github.com:icefields/PowerAmpache2PluginTemplate.git` (or HTTPS).

## PRD backlog (status — reset pass)

| ID | Task | Owner / tree | Status |
|----|------|----------------|--------|
| P0-0 | Reconcile mocks + UI with **PA2 phone** vs **plugin Auto** after fresh template | **`mockup/`**, **`docs/`**, template **`app`** | **In progress** — home + copy aligned to umbrella (no root Compose); plugin **`app/`** uses mocks per boundaries. |
| P0-1 | Media3 Auto checklist (browse, session, artwork, errors, DHU) | **Power-Ampache-2** | **Upstream-only** when that repo is not in tree. |
| P0-2 | Browsable `MediaItem` tree vs product | **Power-Ampache-2** + docs | **Upstream-only** for code. |
| P0-3 | Plugin template: playback + browse + DHU | Template **`app`** (+ explicit scope only for **`data`/`domain`**) | **Reset** — verify against **fresh** `main` / **`plugin/AndroidAuto`**; do not assume **Old** behaviour. |
| P1+ | Prior P1–P3 rows from the previous **AGENTS** era | — | **Re-triage** after mockup + template baseline. |

## Workspace implementation snapshot

Update when the fresh template + mockups stabilize.

| Area | Status (post-reset) |
|------|----------------------|
| **Policy / workflow** | Owner constraints (PR scope to icefields, no `data`/`domain` without scope): [`08-portfolio-and-pr-policy.md`](08-portfolio-and-pr-policy.md). |
| **Root Android module** | **Removed** — umbrella repo is mockup + docs + agents; Gradle only under nested **`PowerAmpache2PluginTemplate/`** (see root **README**). |
| **`PowerAmpache2PluginTemplate/`** | Upstream **main**: starter **SongList** flow, **`domain`** mock models (`domain/.../model/mocks/`), **`MusicFetcher`** / **`MusicFetcherImpl`**, theme submodule. **No** assumption of **Old** catalog/Auto stack until re-implemented under boundaries. |
| **`mockup/`** | **Needs rebuild** — replace “plugin as full phone app” framing with **PA2** handheld + **plugin/Auto** slices. |
