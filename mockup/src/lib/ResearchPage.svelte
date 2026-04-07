<script lang="ts">
  import './md-prose.css'
  import SiteNav from './SiteNav.svelte'
  import { researchSections } from './research-docs'
  import type { AppRoute } from './hash-routes'

  interface Props {
    route: AppRoute
  }

  let { route }: Props = $props()
</script>

<div class="page">
  <header class="page-header">
    <SiteNav {route} />
    <h1 class="page-title">UX research</h1>
    <p class="page-lead">
      Summaries and checklists for Android Auto behaviour, driver tasks, and how they map to Power Ampache 2. Expand a
      section to read it in full. Links between Markdown files resolve in the repo; if a link target is missing here,
      open the file under <code>docs/ux-research/</code> from the project root.
    </p>
  </header>

  <main class="page-main">
    <ul class="card-list">
      {#each researchSections as s (s.id)}
        <li class="card-item">
          <details class="card-details">
            <summary class="card-summary">
              <span class="summary-row">
                <span class="card-title">{s.title}</span>
                <span class="chevron" aria-hidden="true"></span>
              </span>
              <span class="card-blurb">{s.blurb}</span>
              <span class="source-path"><code>{s.sourcePath}</code></span>
            </summary>
            <div class="md-prose doc-html" role="region" aria-label="Full document: {s.title}">
              {@html s.html}
            </div>
          </details>
        </li>
      {/each}
    </ul>
    <p class="doc-hint">
      Index: <code>docs/ux-research/README.md</code> · Plan:
      <code>docs/android-auto-ux-research-plan.md</code> ·
      <a
        class="ext"
        href="https://developer.android.com/training/cars/media"
        rel="noreferrer"
        target="_blank">Create audio media apps (Android for Cars)</a>
    </p>
  </main>
</div>

<style>
  .page {
    min-height: 100svh;
    padding: 20px 20px 48px;
    max-width: 820px;
    margin: 0 auto;
  }

  .page-header {
    margin-bottom: 28px;
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
    margin: 0;
    font-size: 0.88rem;
    line-height: 1.5;
    color: var(--pa2-on-surface-variant);
  }

  .page-lead code {
    font-size: 0.78rem;
    color: var(--pa2-on-surface);
  }

  .page-main {
    display: flex;
    flex-direction: column;
    gap: 0;
  }

  .card-list {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    gap: 12px;
  }

  .card-item {
    margin: 0;
  }

  .card-details {
    border-radius: 12px;
    border: 1px solid var(--mock-chrome-border);
    background: var(--pa2-surface-container);
    overflow: hidden;
  }

  .card-summary {
    cursor: pointer;
    list-style: none;
    padding: 16px 18px;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    gap: 8px;
  }

  .card-summary::-webkit-details-marker {
    display: none;
  }

  .summary-row {
    display: flex;
    align-items: baseline;
    justify-content: space-between;
    gap: 12px;
  }

  .chevron {
    flex-shrink: 0;
    width: 1.25rem;
    text-align: center;
    font-size: 0.75rem;
    color: var(--pa2-primary);
    font-weight: 800;
  }

  .chevron::before {
    content: '+';
  }

  .card-details[open] .chevron::before {
    content: '−';
  }

  .card-title {
    font-size: 1rem;
    font-weight: 800;
    color: var(--pa2-primary);
  }

  .card-blurb {
    font-size: 0.86rem;
    line-height: 1.5;
    color: var(--pa2-on-surface-variant);
    margin: 0;
  }

  .source-path {
    font-size: 0.7rem;
    color: var(--pa2-on-surface-variant);
  }

  .source-path code {
    word-break: break-all;
    font-size: inherit;
    color: var(--pa2-on-surface);
  }

  .doc-html {
    padding: 0 18px 18px;
  }

  .doc-hint {
    margin: 24px 0 0;
    font-size: 0.78rem;
    line-height: 1.5;
    color: var(--pa2-on-surface-variant);
  }

  .doc-hint code {
    font-size: 0.72rem;
    color: var(--pa2-on-surface);
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

  .card-summary:focus-visible {
    outline: 2px solid var(--pa2-tertiary);
    outline-offset: -2px;
    border-radius: 10px;
  }
</style>
