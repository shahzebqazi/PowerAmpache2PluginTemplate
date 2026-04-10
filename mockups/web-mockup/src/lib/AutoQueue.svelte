<script lang="ts">
  import FrameLabel from './FrameLabel.svelte'

  type Q = { title: string; artist: string; current?: boolean }
  const queue: Q[] = [
    { title: 'Midnight City', artist: 'M83', current: true },
    { title: 'Reunion', artist: 'M83' },
    { title: 'Wait', artist: 'M83' },
    { title: 'Steve McQueen', artist: 'M83' },
    { title: 'Raconte-moi une histoi…', artist: 'M83' },
  ]
</script>

<div class="auto-shell">
  <FrameLabel
    surface="auto-pa2"
    label="Auto — PA2 session (DHU)"
    note="Read-mostly queue in the host; large rows for glanceability. Reorder and bulk edit stay in the handheld app when the host exposes a simple queue only."
  />
  <div class="auto-screen">
    <div class="panel">
      <header class="top">
        <button type="button" class="back-btn" aria-label="Back">‹</button>
        <h1 class="title">Playing next</h1>
        <span class="spacer" aria-hidden="true"></span>
      </header>
      <ul class="list" role="list">
        {#each queue as row, i}
          <li>
            <button type="button" class="row" class:current={row.current} aria-current={row.current ? 'true' : undefined}>
              <span class="idx">{i + 1}</span>
              <span class="row-text">
                <span class="row-title">{row.title}</span>
                <span class="row-sub">{row.artist}</span>
              </span>
              {#if row.current}
                <span class="badge">Now</span>
              {/if}
            </button>
          </li>
        {/each}
      </ul>
    </div>
  </div>
</div>

<style>
  .auto-shell {
    container-type: inline-size;
    width: 100%;
    max-width: var(--car-display-max-w, 1120px);
    margin: 0 auto;
    border-radius: 12px;
    overflow: hidden;
    border: 1px solid var(--auto-hu-border);
    background: var(--auto-hu-bg);
    box-shadow: 0 16px 40px rgba(0, 0, 0, 0.5);
    font-family: 'Nunito', system-ui, sans-serif;
  }
  .auto-screen {
    width: 100%;
    aspect-ratio: var(--car-aspect-ratio, 16 / 9);
    display: flex;
    align-items: stretch;
    justify-content: center;
    padding: clamp(12px, 2vw, 20px);
    background: linear-gradient(180deg, rgba(112, 204, 204, 0.06) 0%, var(--auto-hu-bg) 50%);
    box-sizing: border-box;
  }
  .panel {
    width: 100%;
    max-width: min(560px, 100%);
    display: flex;
    flex-direction: column;
    min-height: 0;
    border-radius: 16px;
    border: 1px solid var(--auto-hu-border);
    background: var(--auto-hu-surface);
    overflow: hidden;
  }
  .top {
    flex-shrink: 0;
    display: grid;
    grid-template-columns: var(--auto-min-hit) 1fr var(--auto-min-hit);
    align-items: center;
    padding: 12px 8px;
    border-bottom: 1px solid var(--auto-hu-border);
    background: rgba(112, 204, 204, 0.06);
  }
  .back-btn {
    min-width: var(--auto-min-hit);
    min-height: var(--auto-min-hit);
    border: none;
    border-radius: 14px;
    background: transparent;
    color: var(--auto-hu-accent);
    font-size: 1.75rem;
    font-weight: 700;
    line-height: 1;
    font-family: inherit;
  }
  .back-btn:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  .title {
    margin: 0;
    text-align: center;
    font-size: clamp(1.1rem, 2.2vw, 1.35rem);
    font-weight: 800;
    color: var(--auto-hu-text);
  }
  .spacer {
    width: var(--auto-min-hit);
  }
  .list {
    list-style: none;
    margin: 0;
    padding: 10px 12px 14px;
    overflow-y: auto;
    flex: 1;
    min-height: 0;
  }
  .row {
    width: 100%;
    display: flex;
    align-items: center;
    gap: 14px;
    text-align: left;
    padding: 14px 16px;
    margin-bottom: 8px;
    border: 1px solid transparent;
    border-radius: 14px;
    background: var(--auto-hu-surface-elevated);
    color: inherit;
    min-height: var(--auto-min-hit);
    font-family: inherit;
    box-sizing: border-box;
  }
  .row.current {
    background: var(--auto-hu-accent-soft);
    border-color: var(--auto-hu-border);
  }
  .row:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  .idx {
    font-size: 0.85rem;
    font-variant-numeric: tabular-nums;
    color: var(--auto-hu-muted);
    width: 1.5rem;
    flex-shrink: 0;
    font-weight: 700;
  }
  .row-text {
    display: flex;
    flex-direction: column;
    gap: 2px;
    min-width: 0;
    flex: 1;
  }
  .row-title {
    font-weight: 700;
    font-size: clamp(0.95rem, 1.8vw, 1.05rem);
    color: var(--auto-hu-text);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .row-sub {
    font-size: 0.85rem;
    color: var(--auto-hu-muted);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .badge {
    flex-shrink: 0;
    font-size: 0.65rem;
    font-weight: 800;
    letter-spacing: 0.08em;
    text-transform: uppercase;
    color: var(--pa2-on-primary);
    background: var(--auto-hu-accent);
    padding: 6px 10px;
    border-radius: 8px;
  }
  @container (max-width: 520px) {
    .auto-screen {
      aspect-ratio: unset;
      min-height: 380px;
    }
  }
</style>
