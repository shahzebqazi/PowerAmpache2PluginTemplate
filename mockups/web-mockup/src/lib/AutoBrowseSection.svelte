<script lang="ts">
  import FrameLabel from './FrameLabel.svelte'
  import { browseConfig, type BrowseSectionKind } from './browse-section'

  interface Props {
    section: BrowseSectionKind
  }

  let { section }: Props = $props()

  const cfg = $derived(browseConfig(section))
</script>

<div class="auto-shell">
  <FrameLabel surface="auto-pa2" label={cfg.frameLabel} note={cfg.frameNote} />
  <div class="auto-screen">
    <aside class="dock" aria-label="App shortcuts (host chrome)">
      <span class="dock-dot active" title="Music"></span>
      <span class="dock-dot" title="Maps"></span>
      <span class="dock-dot" title="Assistant"></span>
    </aside>
    <div class="main">
      <header class="hu-bar">
        <span class="brand">Power Ampache</span>
        <button type="button" class="voice-btn" aria-label="Voice search">Voice</button>
      </header>

      <div
        class="np-strip"
        role="note"
        aria-label="Illustrative host mini-player strip — real now playing is drawn by Android Auto, not this app"
      >
        <span class="np-mini-art" aria-hidden="true"></span>
        <span class="np-mini-text">
          <span class="np-mini-title">Midnight City</span>
          <span class="np-mini-sub">M83 · session metadata (host chrome)</span>
        </span>
        <span class="np-mini-play" aria-hidden="true">▶</span>
      </div>

      <div class="browse">
        <h1 class="heading">{cfg.title}</h1>
        <ul class="list" role="list">
          {#each cfg.rows as row}
            <li>
              <button type="button" class="item" class:accent={row.accent}>
                <span class="row-text">
                  <span class="label">{row.title}</span>
                  <span class="sub">{row.sub}</span>
                </span>
                <span class="arrow" aria-hidden="true">›</span>
              </button>
            </li>
          {/each}
        </ul>
      </div>
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
    flex-direction: row;
    align-items: stretch;
    background: var(--auto-hu-bg);
    box-sizing: border-box;
    min-height: 0;
  }
  .dock {
    flex-shrink: 0;
    width: clamp(56px, 8vw, 72px);
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 16px;
    padding: 18px 8px;
    background: rgba(0, 0, 0, 0.35);
    border-right: 1px solid var(--auto-hu-border);
  }
  .dock-dot {
    width: var(--auto-min-hit);
    height: var(--auto-min-hit);
    min-width: var(--auto-min-hit);
    min-height: var(--auto-min-hit);
    border-radius: 14px;
    background: var(--auto-hu-surface-elevated);
    border: 1px solid var(--auto-hu-border);
    box-sizing: border-box;
  }
  .dock-dot.active {
    background: var(--auto-hu-accent-soft);
    border-color: var(--auto-hu-accent);
    box-shadow: 0 0 0 1px rgba(112, 204, 204, 0.35);
  }
  .main {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    overflow: hidden;
  }
  .hu-bar {
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
    padding: 10px 14px 8px;
    background: linear-gradient(180deg, rgba(112, 204, 204, 0.12) 0%, transparent 100%);
    border-bottom: 1px solid var(--auto-hu-border);
  }
  .brand {
    font-weight: 800;
    font-size: clamp(0.95rem, 1.8vw, 1.1rem);
    color: var(--auto-hu-accent);
    letter-spacing: 0.02em;
  }
  .voice-btn {
    min-height: 44px;
    padding: 8px 16px;
    border-radius: 12px;
    border: 1px solid var(--auto-hu-border);
    background: var(--auto-hu-surface-elevated);
    color: var(--auto-hu-text);
    font-weight: 700;
    font-size: 0.9rem;
    font-family: inherit;
  }
  .voice-btn:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  .np-strip {
    flex-shrink: 0;
    display: flex;
    align-items: center;
    gap: 12px;
    margin: 8px 12px 12px;
    padding: 10px 14px;
    border-radius: 14px;
    border: 1px solid var(--auto-hu-border);
    background: var(--auto-hu-surface);
    text-decoration: none;
    color: inherit;
    min-height: var(--auto-min-hit);
    box-sizing: border-box;
  }
  .np-strip:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  .np-mini-art {
    width: 44px;
    height: 44px;
    border-radius: 8px;
    flex-shrink: 0;
    background: linear-gradient(145deg, rgba(112, 204, 204, 0.35), var(--auto-hu-surface-elevated));
    border: 1px solid var(--auto-hu-border);
  }
  .np-mini-text {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    gap: 2px;
    text-align: left;
  }
  .np-mini-title {
    font-weight: 700;
    font-size: clamp(0.9rem, 1.6vw, 1rem);
    color: var(--auto-hu-text);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .np-mini-sub {
    font-size: 0.78rem;
    color: var(--auto-hu-muted);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .np-mini-play {
    flex-shrink: 0;
    width: 44px;
    height: 44px;
    display: grid;
    place-items: center;
    border-radius: 50%;
    background: var(--auto-hu-accent-soft);
    color: var(--auto-hu-accent);
    font-size: 1rem;
    border: 1px solid var(--auto-hu-border);
  }
  .browse {
    flex: 1;
    min-height: 0;
    display: flex;
    flex-direction: column;
    padding: 0 10px 12px;
    overflow: hidden;
  }
  .heading {
    flex-shrink: 0;
    margin: 0;
    padding: 4px 0 8px;
    font-size: clamp(1.35rem, 2.8vw, 1.6rem);
    font-weight: 800;
    color: var(--auto-hu-text);
    text-align: left;
  }
  .list {
    list-style: none;
    margin: 0;
    padding: 0 2px;
    overflow-y: auto;
    flex: 1;
    min-height: 0;
  }
  .item {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
    padding: 14px 16px;
    margin-bottom: 6px;
    border: 1px solid transparent;
    border-radius: 14px;
    background: var(--auto-hu-surface-elevated);
    color: var(--auto-hu-text);
    font-family: inherit;
    text-align: left;
    min-height: var(--auto-min-hit);
    box-sizing: border-box;
  }
  .item.accent {
    background: var(--auto-hu-accent-soft);
    border-color: var(--auto-hu-border);
  }
  .item:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  .row-text {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    gap: 2px;
  }
  .label {
    font-weight: 700;
    font-size: clamp(0.95rem, 1.9vw, 1.08rem);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .sub {
    font-size: 0.82rem;
    color: var(--auto-hu-muted);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .arrow {
    color: var(--auto-hu-muted);
    font-size: 1.25rem;
    flex-shrink: 0;
  }
  @container (max-width: 520px) {
    .auto-screen {
      aspect-ratio: unset;
      min-height: 420px;
    }
  }
</style>
