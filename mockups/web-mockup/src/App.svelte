<script lang="ts">
  import AutoBrowseSection from './lib/AutoBrowseSection.svelte'
  import AutoQueue from './lib/AutoQueue.svelte'
  import AutoErrorState from './lib/AutoErrorState.svelte'
  import HomePage from './lib/HomePage.svelte'
  import DesignSystemPage from './lib/DesignSystemPage.svelte'
  import ResearchPage from './lib/ResearchPage.svelte'
  import ArchitecturePage from './lib/ArchitecturePage.svelte'
  import SiteNav from './lib/SiteNav.svelte'
  import { parseHash, type AppRoute } from './lib/hash-routes'
  import { browseSectionFromFrame, isAutoBrowseFrameId } from './lib/browse-section'
  import type { MockupFrameId } from './lib/screen-ids'

  const frames: { id: MockupFrameId; label: string }[] = [
    { id: 'auto-browse-playlists', label: 'Browse · Playlists' },
    { id: 'auto-browse-recommended', label: 'Browse · Recommended' },
    { id: 'auto-browse-recents', label: 'Browse · Recents' },
    { id: 'auto-browse-frequent', label: 'Browse · Frequently played' },
    { id: 'auto-browse-new', label: 'Browse · Newly added' },
    { id: 'auto-queue', label: 'Up next (queue)' },
    { id: 'auto-error', label: 'Can’t connect (error)' },
  ]

  let route = $state<AppRoute>(parseHash(typeof window !== 'undefined' ? window.location.hash : ''))

  $effect(() => {
    if (typeof window === 'undefined') return
    const sync = () => {
      route = parseHash(window.location.hash)
    }
    sync()
    window.addEventListener('hashchange', sync)
    return () => window.removeEventListener('hashchange', sync)
  })

  function setMockupFrame(id: MockupFrameId) {
    window.location.hash = `#/mockup/${id}`
  }
</script>

{#if route.name === 'home'}
  <HomePage />
{:else if route.name === 'design'}
  <DesignSystemPage {route} />
{:else if route.name === 'research'}
  <ResearchPage {route} />
{:else if route.name === 'architecture'}
  <ArchitecturePage />
{:else if route.name === 'mockup'}
  <div class="mockup-page">
    <header class="mockup-top">
      <SiteNav {route} />
    </header>

    <div class="mockup-body">
      <aside class="mockup-meta" aria-label="Mockup list">
        <h1 class="app-title">Android Auto mockups</h1>
        <p class="app-lead">
          <strong>Plugin / Media3</strong> — we do <strong>not</strong> implement a custom Android Auto player UI. The
          <strong>head unit</strong> draws browse, now playing, and transport; the app supplies a
          <code>MediaItem</code> tree, session, and metadata. Handheld UI lives in
          <strong>Power-Ampache-2</strong>. Frames below are <strong>stakeholder illustrations</strong> only (browse
          patterns, queue, errors) — not shipping car chrome.
        </p>
        <nav class="chips" aria-label="Mockup frames">
          {#each frames as f}
            <button
              type="button"
              class="chip"
              class:active={route.name === 'mockup' && route.frame === f.id}
              onclick={() => setMockupFrame(f.id)}
            >
              {f.label}
            </button>
          {/each}
        </nav>
        <p class="dhu-note">
          Install the <strong>plugin</strong> debug APK for real DHU sessions (see repo <code>AGENTS.md</code> on
          <code>main</code>). This page is a browser mockup only. The home hero image is optional; see
          <code>mockups/web-mockup/README.md</code> to refresh it.
        </p>
      </aside>

      <main class="mockup-stage" aria-label="Selected mockup">
        <div class="mockup-viewport mockup-viewport--wide">
          {#if isAutoBrowseFrameId(route.frame)}
            <AutoBrowseSection section={browseSectionFromFrame(route.frame)} />
          {:else if route.frame === 'auto-queue'}
            <AutoQueue />
          {:else if route.frame === 'auto-error'}
            <AutoErrorState />
          {/if}
        </div>
      </main>
    </div>
  </div>
{/if}

<style>
  .mockup-page {
    min-height: 100svh;
    max-width: 1040px;
    margin: 0 auto;
    padding: 16px 18px 32px;
    box-sizing: border-box;
  }

  .mockup-top {
    margin-bottom: 18px;
    padding-bottom: 14px;
    border-bottom: 1px solid var(--mock-chrome-border);
  }

  .mockup-body {
    display: grid;
    gap: 24px 32px;
    align-items: start;
  }

  @media (max-width: 879px) {
    .mockup-stage {
      order: 1;
    }
    .mockup-meta {
      order: 2;
    }
  }

  @media (min-width: 880px) {
    .mockup-body {
      grid-template-columns: minmax(240px, 300px) 1fr;
    }
  }

  .mockup-meta {
    padding: 0;
    background: transparent;
  }

  @media (max-width: 879px) {
    .mockup-meta {
      padding-bottom: 8px;
      border-bottom: 1px solid var(--mock-chrome-border);
    }
  }

  .mockup-stage {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: min(360px, 50svh);
  }

  @media (min-width: 880px) {
    .mockup-stage {
      min-height: min(640px, calc(100svh - 120px));
      position: sticky;
      top: 16px;
    }
  }

  .mockup-viewport {
    --mockup-slot-w: min(100%, 380px);
    width: var(--mockup-slot-w);
    max-width: var(--mockup-slot-w);
    max-height: min(72svh, 520px);
    overflow: auto;
    margin-inline: auto;
    padding: 12px;
    border-radius: 16px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--mock-chrome-bg);
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
    --car-display-max-w: 100%;
  }

  .mockup-viewport--wide {
    --mockup-slot-w: min(100%, 520px);
    max-height: min(78svh, 580px);
  }

  .mockup-viewport :global(.auto-shell) {
    width: 100%;
    max-width: 100% !important;
    margin-inline: auto;
    flex-shrink: 0;
  }

  .app-title {
    margin: 0 0 8px;
    font-size: 1.1rem;
    font-weight: 700;
    color: var(--pa2-on-surface);
    line-height: 1.3;
  }
  .app-lead {
    margin: 0 0 16px;
    font-size: 0.82rem;
    line-height: 1.45;
    color: var(--pa2-on-surface-variant);
  }
  .app-lead strong {
    color: var(--pa2-primary);
    font-weight: 600;
  }
  .app-lead code {
    font-size: 0.72rem;
    word-break: break-all;
    color: var(--pa2-on-surface-variant);
  }
  .chips {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  .chip {
    text-align: left;
    padding: 10px 12px;
    border-radius: 10px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--pa2-surface-container);
    color: var(--pa2-on-surface);
    font-size: 0.78rem;
    font-weight: 600;
    line-height: 1.35;
  }
  .chip.active {
    border-color: var(--pa2-primary);
    background: rgba(112, 204, 204, 0.15);
    color: var(--pa2-primary);
  }
  .chip:focus-visible {
    outline: 2px solid var(--pa2-tertiary);
    outline-offset: 2px;
  }
  .dhu-note {
    margin: 20px 0 0;
    font-size: 0.75rem;
    line-height: 1.45;
    color: var(--pa2-on-surface-variant);
  }
  .dhu-note code {
    font-size: 0.68rem;
    color: var(--pa2-on-surface-variant);
  }
  .dhu-note strong {
    color: var(--pa2-on-surface);
  }
</style>
