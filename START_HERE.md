# Start here

## Repository

- **Upstream template:** `icefields/PowerAmpache2PluginTemplate` — branch **`main`** tracks **`upstream/main`** only (no feature work there).
- **This fork:** development happens on **`cursor-cloud/dev-main-4dc1`** and topic branches under **`cursor-cloud/`**.
- **Modules:** `domain`, `data`, `app`, `PowerAmpache2Theme` — respect Clean Architecture boundaries unless scope expands.

## Agents

- **Branch policy, commit message format, and module notes:** see [`AGENTS.md`](AGENTS.md).
- **Commit subjects** on dev branches: `<branch-name>: <short imperative summary>` (use the full current branch name from `git branch --show-current`).

## Perpetual coding agents

Autonomous agents should **not** block on Kanban or a “Proceed?” gate unless the task explicitly requires it. Use **sensible defaults** when product links or UI details are missing; record assumptions in the PR.

### Android Auto–visible MVP (Media3) — agent brief

Use this as the task spec when implementing a minimal car/host-visible browse and play flow.

**Mission:** Deliver a minimal **Android Auto–visible** experience using **Media3** (typically `MediaLibraryService` + `MediaSession` / `MediaBrowser`) so the car/host UI can **browse and play** content.

**Architecture rules (non-negotiable):**

- Wire only through existing **app-layer, domain-facing APIs** (e.g. `MusicFetcher` / use cases exposing `Flow` / `StateFlow`): playlists (list), favourite albums, recent albums, **newest/latest albums** (`LatestAlbumsStateFlow` / `MusicFetcher.latestAlbumsFlow` matches “newest” here), highest-rated albums, `getSongsFromPlaylist`, `getSongsFromAlbum`.
- **Collect/subscribe** so exposed lists stay fresh.
- **Do not** change `MainActivity`’s launcher flow.
- **Do not** touch **`domain/`** or **`data/`** (including `PA2DataFetchService`).

**Dependencies:** Prefer **Media3** (and **ExoPlayer** if needed) **only in `app/build.gradle.kts`** — no domain/data changes.

**Kanban / tracking:** `AGENTS.md` does not name a GitHub Project. If the task includes a card/issue URL, link it in the PR; otherwise proceed and use the PR as the record.

**Defaults** (override only if the task says otherwise):

| Topic | Default |
|--------|--------|
| Newest | `LatestAlbumsStateFlow` / `MusicFetcher.latestAlbumsFlow` — no new domain/data shims |
| Browse tree | Root → sections (Playlists, Favourite albums, Recent, Newest, Highest-rated) → items → songs |
| Media3 IDs | Propose a stable scheme (e.g. `root`, `section/...`, `playlist/{id}`, `album/{id}`) and document it in the PR |
| Playback | MVP = browse + queue/play via `MediaSession` with ExoPlayer in the service when URIs exist; fail gracefully without touching domain/data |
| Branch | Topic branch off `cursor-cloud/dev-main-4dc1`, e.g. `cursor-cloud/android-auto-media3-mvp-e77e` (or another `cursor-cloud/...-e77e` name if specified) |

**Git / delivery:** Commit and push on the topic branch; open or update a **draft PR** to **`cursor-cloud/dev-main-4dc1`** with constraints, ID scheme, and files touched.
