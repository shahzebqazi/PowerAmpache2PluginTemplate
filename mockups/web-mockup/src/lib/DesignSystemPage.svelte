<script lang="ts">
  import './md-prose.css'
  import SiteNav from './SiteNav.svelte'
  import type { AppRoute } from './hash-routes'
  import { renderMarkdown } from './renderMarkdown'
  import designIndex from '../../../docs/design-system/00-design-system-index.md?raw'
  import platformSheet from '../../../docs/ux-research/01-platform-constraint-sheet.md?raw'

  interface Props {
    route: AppRoute
  }

  let { route }: Props = $props()

  const platformHtml = renderMarkdown(platformSheet)
  const designIndexHtml = renderMarkdown(designIndex)

  const swatches: { name: string; var: string }[] = [
    { name: 'Primary', var: '--pa2-primary' },
    { name: 'On primary', var: '--pa2-on-primary' },
    { name: 'Surface', var: '--pa2-surface' },
    { name: 'Background', var: '--pa2-background' },
    { name: 'Surface container', var: '--pa2-surface-container' },
    { name: 'On surface', var: '--pa2-on-surface' },
    { name: 'On surface variant', var: '--pa2-on-surface-variant' },
    { name: 'Tertiary', var: '--pa2-tertiary' },
    { name: 'Error', var: '--pa2-error' },
    { name: 'Outline', var: '--pa2-outline' },
  ]
</script>

<div class="page">
  <header class="page-header">
    <SiteNav {route} />
    <h1 class="page-title">Android Auto — design and implementation</h1>
    <p class="page-lead">
      <strong>Power Ampache 2</strong> does not apply phone theming on <strong>Android Auto</strong>. The head unit
      renders browse lists, now playing, and transport controls. Car integration means a correct
      <strong>Media3</strong> <code>MediaSession</code>, a coherent <code>MediaItem</code> tree, reliable metadata
      and artwork, voice actions, and error reporting, following
      <a
        class="ext inline"
        href="https://developer.android.com/training/cars/media"
        rel="noreferrer"
        target="_blank">Google’s car media documentation</a>
      and distraction safeguards. <strong>Nunito</strong> and PA2 color tokens apply to the <strong>handheld</strong>
      app only; see <em>Phone reference</em> below.
    </p>
    <ul class="quick-links">
      <li>
        <a
          class="ext"
          href="https://developer.android.com/training/cars/media"
          rel="noreferrer"
          target="_blank">Audio media apps for cars</a>
      </li>
      <li>
        <a
          class="ext"
          href="https://developer.android.com/training/cars/media/distraction-safeguards"
          rel="noreferrer"
          target="_blank">Distraction safeguards</a>
      </li>
      <li>
        <a
          class="ext"
          href="https://developer.android.com/training/cars/media/errors"
          rel="noreferrer"
          target="_blank">Errors in car media apps</a>
      </li>
    </ul>
  </header>

  <main class="page-main">
    <section class="intro-panel" aria-labelledby="impl-heading">
      <h2 id="impl-heading" class="h2">Car integration (engineering focus)</h2>
      <ul class="bullet-list">
        <li>Browsable and playable <code>MediaItem</code> hierarchy (shallow root: resume, recents, playlists).</li>
        <li>
          <code>MediaSession</code> transport (play, pause, skip, seek where supported) and accurate
          <code>PlaybackState</code>.
        </li>
        <li>Track metadata and artwork the host can show without custom layouts.</li>
        <li>Voice actions and error surfacing per Google’s car media docs.</li>
        <li>DHU smoke tests; expect OEM head units to look different from any mockup.</li>
      </ul>
    </section>

    <details class="doc-block" open>
      <summary class="doc-summary">
        <span class="doc-summary-title">Platform constraint sheet</span>
        <span class="chevron" aria-hidden="true"></span>
      </summary>
      <p class="doc-meta"><code>docs/ux-research/01-platform-constraint-sheet.md</code></p>
      <div class="md-prose doc-html">{@html platformHtml}</div>
    </details>

    <details class="doc-block">
      <summary class="doc-summary">
        <span class="doc-summary-title">Design system index (two surfaces)</span>
        <span class="chevron" aria-hidden="true"></span>
      </summary>
      <p class="doc-meta"><code>docs/design-system/00-design-system-index.md</code></p>
      <div class="md-prose doc-html">{@html designIndexHtml}</div>
    </details>

    <details class="doc-block phone-ref">
      <summary class="doc-summary">
        <span class="doc-summary-title">Phone reference — PowerAmpache2Theme only</span>
        <span class="chevron" aria-hidden="true"></span>
      </summary>
      <p class="phone-lead">
        Use for the handheld app in <strong>Power-Ampache-2</strong>. Not for projected Android Auto chrome (mockups here
        are Auto / plugin only).
      </p>
      <div class="type-sample">
        <h3 class="type-label">Typography</h3>
        <p class="type-display">Nunito</p>
        <p class="type-body">
          Variable font in <code>public/fonts/</code> (OFL). Material 3 roles in the theme module map to these faces.
        </p>
      </div>
      <h3 class="swatch-heading">Color tokens (dark baseline)</h3>
      <ul class="swatches">
        {#each swatches as s}
          <li class="swatch">
            <span class="chip" style:background="var({s.var})"></span>
            <span class="swatch-name">{s.name}</span>
            <span class="swatch-var">{s.var}</span>
          </li>
        {/each}
      </ul>
      <p class="doc-hint">
        Brand &amp; language: <code>docs/design-system/01-brand-and-language.md</code> · Inventory:
        <code>docs/design-system/appendix-pa2-theme-inventory.md</code>
      </p>
    </details>
  </main>
</div>

<style>
  .page {
    min-height: 100svh;
    padding: 20px 20px 48px;
    max-width: 920px;
    margin: 0 auto;
  }

  .page-header {
    margin-bottom: 24px;
    padding-bottom: 20px;
    border-bottom: 1px solid var(--mock-chrome-border);
  }

  .page-title {
    margin: 16px 0 10px;
    font-size: clamp(1.25rem, 3vw, 1.5rem);
    font-weight: 800;
    color: var(--pa2-on-surface);
    line-height: 1.25;
  }

  .page-lead {
    margin: 0 0 14px;
    font-size: 0.88rem;
    line-height: 1.55;
    color: var(--pa2-on-surface-variant);
  }

  .page-lead strong {
    color: var(--pa2-on-surface);
  }

  .quick-links {
    margin: 0;
    padding-left: 1.2em;
    font-size: 0.84rem;
    line-height: 1.6;
    color: var(--pa2-on-surface-variant);
  }

  .ext {
    color: var(--pa2-tertiary);
    text-decoration: underline;
    text-underline-offset: 2px;
  }

  .ext:focus-visible {
    outline: 2px solid var(--pa2-tertiary);
    outline-offset: 2px;
  }

  .ext.inline {
    font-weight: 600;
  }

  .page-main {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .intro-panel {
    padding: 18px 20px;
    border-radius: 12px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--pa2-surface-container);
  }

  .h2 {
    margin: 0 0 10px;
    font-size: 1rem;
    font-weight: 800;
    color: var(--pa2-on-surface);
  }

  .bullet-list {
    margin: 0;
    padding-left: 1.2em;
    font-size: 0.86rem;
    line-height: 1.55;
    color: var(--pa2-on-surface-variant);
  }

  .bullet-list li {
    margin: 0.35em 0;
  }

  .bullet-list code {
    font-size: 0.82em;
    color: var(--pa2-on-surface);
  }

  .doc-block {
    border-radius: 12px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--mock-chrome-bg);
    overflow: hidden;
  }

  .doc-block.phone-ref {
    background: var(--pa2-surface-container);
  }

  .doc-summary {
    cursor: pointer;
    list-style: none;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
    padding: 14px 18px;
    font-weight: 800;
    font-size: 0.95rem;
    color: var(--pa2-primary);
  }

  .doc-summary::-webkit-details-marker {
    display: none;
  }

  .doc-summary-title {
    flex: 1;
  }

  .chevron {
    flex-shrink: 0;
    width: 1.25rem;
    text-align: center;
    font-size: 0.85rem;
    font-weight: 800;
    color: var(--pa2-primary);
  }

  .chevron::before {
    content: '+';
  }

  .doc-block[open] .chevron::before {
    content: '−';
  }

  .doc-meta {
    margin: 0 18px 0;
    font-size: 0.72rem;
    color: var(--pa2-on-surface-variant);
  }

  .doc-meta code {
    word-break: break-all;
    color: var(--pa2-on-surface);
  }

  .doc-html {
    padding: 0 18px 18px;
  }

  .phone-lead {
    margin: 0 18px 14px;
    font-size: 0.86rem;
    line-height: 1.5;
    color: var(--pa2-on-surface-variant);
  }

  .phone-lead strong {
    color: var(--pa2-on-surface);
  }

  .type-sample {
    margin: 0 18px 16px;
    padding: 16px;
    border-radius: 10px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--mock-chrome-bg);
  }

  .type-label {
    margin: 0 0 6px;
    font-size: 0.72rem;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.04em;
    color: var(--pa2-primary);
  }

  .type-display {
    margin: 0 0 6px;
    font-size: 1.2rem;
    font-weight: 800;
    color: var(--pa2-on-surface);
  }

  .type-body {
    margin: 0;
    font-size: 0.84rem;
    line-height: 1.5;
    color: var(--pa2-on-surface-variant);
  }

  .swatch-heading {
    margin: 0 18px 10px;
    font-size: 0.88rem;
    font-weight: 800;
    color: var(--pa2-on-surface);
  }

  .swatches {
    list-style: none;
    margin: 0 18px 12px;
    padding: 0;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
    gap: 12px;
  }

  .swatch {
    display: flex;
    flex-direction: column;
    gap: 6px;
    padding: 10px;
    border-radius: 10px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--mock-chrome-bg);
  }

  .swatch .chip {
    width: 100%;
    height: 36px;
    border-radius: 6px;
    border: 1px solid rgba(255, 255, 255, 0.12);
  }

  .swatch-name {
    font-size: 0.78rem;
    font-weight: 700;
    color: var(--pa2-on-surface);
  }

  .swatch-var {
    font-size: 0.65rem;
    color: var(--pa2-on-surface-variant);
    font-family: ui-monospace, monospace;
  }

  .doc-hint {
    margin: 0 18px 18px;
    font-size: 0.78rem;
    line-height: 1.5;
    color: var(--pa2-on-surface-variant);
  }

  .doc-hint code {
    font-size: 0.72rem;
    color: var(--pa2-on-surface);
  }

  .doc-summary:focus-visible {
    outline: 2px solid var(--pa2-tertiary);
    outline-offset: -2px;
  }
</style>
