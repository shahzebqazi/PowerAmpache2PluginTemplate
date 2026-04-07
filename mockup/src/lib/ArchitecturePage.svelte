<script lang="ts">
  import mermaid from 'mermaid'
  import type { Action } from 'svelte/action'
  import SiteNav from './SiteNav.svelte'
  import type { AppRoute } from './hash-routes'

  const route: AppRoute = { name: 'architecture' }

  /** System / deployment view — umbrella repo, nested plugin, car host. */
  const systemDiagram = `
flowchart TB
  subgraph umbrella["android-auto (umbrella repo)"]
    mockup["mockup/ — Svelte (this site)"]
    docs["docs/ + AGENTS.md"]
    agents["android-auto-agents/ — DHU, Gradle harness"]
  end

  subgraph nested["PowerAmpache2PluginTemplate (nested clone; PA2_PLUGIN_GRADLE_ROOT)"]
    app["app/ — UI + Media3"]
    domain["domain/ — models, mocks, MusicFetcher"]
    data["data/ — MusicFetcherImpl, PA2DataFetchService"]
    theme["PowerAmpache2Theme/ — submodule"]
  end

  subgraph device["Phone + USB"]
    gearhead["Android Auto (Gearhead)"]
    dhu["Desktop Head Unit (dev)"]
  end

  mockup -.->|"informs IA"| app
  app --> svc["PluginMediaLibraryService"]
  svc --> cat["MediaLibraryCatalog"]
  svc --> fact["MediaItemFactory"]
  cat --> domain
  data --> domain
  app --> theme
  gearhead -->|"MediaBrowser / Media3 session"| svc
  dhu --> gearhead
`

  /** UML-style class view — main plugin car stack (app module). */
  const classDiagram = `
classDiagram
  direction TB
  class MainActivity {
    +setContent Compose
  }
  class PluginHomeScreen {
    +browse MediaLibraryCatalog
  }
  class PluginMediaLibraryService {
    +MediaLibrarySession
    +ExoPlayer
    +onGetLibraryRoot()
    +onGetChildren()
  }
  class MediaLibraryCatalog {
    <<object>>
    +childrenFor(parentId)
    +browseLabels
  }
  class MediaItemFactory {
    <<object>>
    +fromCatalogEntry()
    +rootMediaItem()
  }
  class PA2DataFetchService {
    +Messenger IPC
  }

  MainActivity --> PluginHomeScreen : hosts
  PluginMediaLibraryService --> MediaLibraryCatalog : mock tree
  PluginMediaLibraryService --> MediaItemFactory : MediaItems
  MediaItemFactory ..> MediaLibraryCatalog : ids + mocks
  note for PluginMediaLibraryService "Host (Gearhead) binds here for Android Auto"
`

  let mermaidInited = false
  function initMermaid(): void {
    if (mermaidInited) return
    mermaid.initialize({
      startOnLoad: false,
      theme: 'dark',
      securityLevel: 'loose',
      fontFamily: 'ui-sans-serif, system-ui, sans-serif',
    })
    mermaidInited = true
  }

  /** Renders after the node exists — fixes Svelte 5 + bind:this timing vs onMount. */
  const mermaidChart: Action<HTMLElement, string> = (node, definition) => {
    initMermaid()
    const id = `mmd-${Math.random().toString(36).slice(2)}`
    node.textContent = 'Rendering diagram…'
    mermaid
      .render(id, definition.trim())
      .then(({ svg }) => {
        node.innerHTML = svg
      })
      .catch((err: unknown) => {
        const msg = err instanceof Error ? err.message : String(err)
        node.innerHTML = `<pre class="mermaid-error" role="alert">Diagram error: ${escapeHtml(msg)}</pre>`
      })
    return {}
  }

  function escapeHtml(s: string): string {
    return s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
  }
</script>

<div class="arch-page">
  <header class="arch-top">
    <SiteNav {route} />
  </header>

  <main class="arch-main">
    <h1 class="arch-title">Architecture (current stack)</h1>
    <p class="arch-lead">
      Umbrella repo <strong>android-auto</strong> ships docs, agent scripts, and this mockup. The car-capable APK is built
      from a nested <strong>PowerAmpache2PluginTemplate</strong> clone (see <code>AGENTS.md</code>). The host (Android Auto)
      renders browse and now playing; the plugin supplies <strong>Media3</strong> session + browsable <code>MediaItem</code>
      tree from domain mocks.
    </p>

    <section class="arch-section" aria-labelledby="sys-h">
      <h2 id="sys-h">System context</h2>
      <p class="arch-caption">Repositories, Gradle modules, and how Gearhead / DHU attach to the media service.</p>
      <div
        class="mermaid-wrap"
        use:mermaidChart={systemDiagram}
        role="img"
        aria-label="System context diagram"
      ></div>
    </section>

    <section class="arch-section" aria-labelledby="uml-h">
      <h2 id="uml-h">Plugin app — UML (simplified)</h2>
      <p class="arch-caption">
        Primary types in <code>app/</code> for phone shell + <code>PluginMediaLibraryService</code> for Android Auto. IPC
        via <code>PA2DataFetchService</code> when the host app feeds catalog data (product scope).
      </p>
      <div
        class="mermaid-wrap"
        use:mermaidChart={classDiagram}
        role="img"
        aria-label="UML class diagram"
      ></div>
    </section>
  </main>
</div>

<style>
  .arch-page {
    min-height: 100svh;
    max-width: 960px;
    margin: 0 auto;
    padding: 16px 18px 48px;
    box-sizing: border-box;
  }

  .arch-top {
    margin-bottom: 20px;
    padding-bottom: 14px;
    border-bottom: 1px solid var(--mock-chrome-border);
  }

  .arch-title {
    margin: 0 0 12px;
    font-size: 1.35rem;
    font-weight: 700;
    color: var(--pa2-on-surface);
  }

  .arch-lead {
    margin: 0 0 28px;
    font-size: 0.88rem;
    line-height: 1.55;
    color: var(--pa2-on-surface-variant);
  }

  .arch-lead strong {
    color: var(--pa2-primary);
    font-weight: 600;
  }

  .arch-lead code {
    font-size: 0.78rem;
    color: var(--pa2-on-surface-variant);
  }

  .arch-section {
    margin-bottom: 36px;
  }

  .arch-section h2 {
    margin: 0 0 8px;
    font-size: 1.05rem;
    font-weight: 700;
    color: var(--pa2-on-surface);
  }

  .arch-caption {
    margin: 0 0 14px;
    font-size: 0.8rem;
    line-height: 1.45;
    color: var(--pa2-on-surface-variant);
  }

  .arch-caption code {
    font-size: 0.72rem;
  }

  .mermaid-wrap {
    min-height: 120px;
    overflow-x: auto;
    padding: 16px 12px;
    border-radius: 12px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--pa2-surface-container);
    font-size: 0.75rem;
    color: var(--pa2-on-surface-variant);
  }

  .mermaid-wrap :global(svg) {
    max-width: 100%;
    height: auto;
  }

  .mermaid-wrap :global(pre.mermaid-error) {
    margin: 0;
    white-space: pre-wrap;
    word-break: break-word;
    color: var(--pa2-error, #f593ab);
    font-size: 0.78rem;
  }
</style>
