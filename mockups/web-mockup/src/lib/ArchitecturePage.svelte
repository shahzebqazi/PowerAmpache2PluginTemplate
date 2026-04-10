<script lang="ts">
  import mermaid from 'mermaid'
  import type { Action } from 'svelte/action'
  import SiteNav from './SiteNav.svelte'
  import type { AppRoute } from './hash-routes'

  const route: AppRoute = { name: 'architecture' }

  /** System / deployment view — docs + mockups branch, plugin Gradle module, car host. */
  const systemDiagram = `
flowchart TB
  subgraph design["PowerAmpache2PluginTemplate — mockups branch"]
    mockup["mockups/web-mockup — Svelte (this site)"]
    docs["docs/ + MkDocs site"]
    agents["android-auto-agents/ — DHU, Gradle harness"]
  end

  subgraph plugin["PowerAmpache2PluginTemplate — main (Gradle)"]
    app["app/ — Compose shell + Media3"]
    domain["domain/ — MusicFetcher, use cases"]
    data["data/ — MusicFetcherImpl"]
    theme["PowerAmpache2Theme/"]
  end

  subgraph device["Phone + USB"]
    gearhead["Android Auto host"]
    dhu["DHU (dev)"]
  end

  mockup -.->|"informs IA"| app
  app --> svc["AndroidAutoMediaLibraryService"]
  svc --> domain
  data --> domain
  app --> theme
  gearhead -->|"host media browser"| svc
  dhu --> gearhead
`

  /** UML-style class view — plugin Media3 surface (simplified). */
  const classDiagram = `
classDiagram
  direction TB
  class MainActivity {
    +setContent Compose
  }
  class AndroidAutoMediaLibraryService {
    +MediaLibrarySession
    +ExoPlayer
    +onGetLibraryRoot()
    +onGetChildren()
  }
  class MusicFetcher {
    <<interface>>
    +albums, playlists
  }

  MainActivity --> AndroidAutoMediaLibraryService : starts
  AndroidAutoMediaLibraryService --> MusicFetcher : library data
  note for AndroidAutoMediaLibraryService "Head unit renders browse/now playing; app supplies MediaItems + session"
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
      This page documents the <strong>PowerAmpache2PluginTemplate</strong> stack: the <strong>mockups</strong> branch (this
      site + research) and the <strong>main</strong> branch (Gradle plugin). The Android Auto <strong>host</strong> renders
      browse and now playing; the plugin supplies a <strong>Media3</strong> <code>MediaLibraryService</code> and
      <code>MediaItem</code> tree — not custom car UI.
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
        Primary types in <code>app/</code> for the Compose shell + <code>AndroidAutoMediaLibraryService</code> for Android
        Auto. IPC via <code>PA2DataFetchService</code> when the host app feeds catalog data (product scope).
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
