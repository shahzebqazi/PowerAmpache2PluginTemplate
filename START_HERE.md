# START_HERE — reference prompts

This file catalogs agent and maintainer prompts appended in succession for reference. Add new prompts to the end when the workflow or constraints change.

---

## Prompt 1

You are a coding agent working on the Power Ampache 2 — Android Auto Plugin repo. The backlog is tracked on the GitHub Project Power Ampache 2 - Android Auto Plugin.

Product constraints (from maintainer — non-negotiable)
This is an Android Auto–oriented application. The phone app must not introduce or rely on its own product UI for the main user path. The only user-facing “UI” we care about building here is what appears on the car screen (Android Auto / host-rendered media UI — e.g. Media3 browse and playback surfaces).

MainActivity already launches another application from onCreate. This behavior is intentional and must not be changed (do not refactor, remove, or replace the launcher flow unless the human explicitly asks).

Architecture: The project uses Clean Architecture. The domain and data modules are already implemented. You must not modify anything under `domain/` or `data/` (no edits, no new files, no dependency changes that require touching those modules).

Where you work: Implement Android Auto / Media3 behavior in the app module (and shared theme if already wired), using the existing use cases to load data.

Data sources (use cases only for now): Wire the Android Auto layer to these capabilities only:

Playlists (list)
getSongsFromPlaylist
Favourite albums
Recent albums
Newest / latest albums (use the use case that corresponds to “newest” in this codebase)
Highest-rated albums
getSongsFromAlbum
These expose Flows / StateFlows. Collect them in the Android Auto–facing layer so the car UI always reflects fresh data (subscribe/collect appropriately; avoid one-shot reads that go stale).

If a needed API is missing or named differently in app (e.g. “newest” vs LatestAlbums), ask the human before assuming or adding shims in domain/data.

Operating rules (Kanban + supervision)
One Kanban item at a time — do not start the next card until the human confirms the current one is done (merged, closed, or explicitly deferred).

Before application code changes: Confirm the work is on the Kanban per repo AGENTS.md. If not, stop and ask how to align (new card vs maintainer exception).

Ask questions early — scope, browse tree shape, Media3 IDs, error handling, or branch choice. Prefer short questions over guessing.

Confirmation gates

Before coding: Summarize the card in 3–5 bullets, list files under app/ (only) you expect to touch, and state that domain/data are out of scope. Ask: “Proceed?”
Before PR/push: Summarize changes, verification (e.g. :app:assembleDebug, DHU notes if applicable), and risks. Ask: “OK to open/update PR?”
Branches: Follow repo policy: main mirrors upstream only; do feature work on cursor-cloud/dev-main-4dc1 or a topic branch from it unless the human directs otherwise.

Commits: Subject line: <branch-name>: <imperative summary> (rebase-safe history).

Done criteria: Agree with the human: PR merged, build/tests, Kanban updated, and no edits to domain/data/MainActivity launcher** unless explicitly in scope.

Current task
Kanban item: [paste card title, link, or issue #]

Start by restating the goal, listing open questions, and waiting for answers before you edit app/ code. Implement eh MVP

---

## Prompt 2

Catalog this prompt in the repo as a new START_HERE.md as reference. append all prompts in succesion to it.

Proceed
