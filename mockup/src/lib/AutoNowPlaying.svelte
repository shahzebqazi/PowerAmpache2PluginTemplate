<script lang="ts">
  import FrameLabel from './FrameLabel.svelte'
</script>

<div class="auto-shell">
  <FrameLabel
    surface="auto-pa2"
    label="Auto — PA2 session (DHU)"
    note="Large transport targets (≥56px) and glanceable metadata. Host sets final layout; Media3 supplies artwork, title, actions, and position. Wave behind the scrubber is optional; off when prefers-reduced-motion."
  />
  <div class="auto-screen">
    <div class="np">
      <div class="np-art" role="img" aria-label="Album artwork"></div>
      <div class="np-maincol">
        <div class="np-center">
          <p class="np-kicker">Now playing</p>
          <p class="np-title">Midnight City</p>
          <p class="np-sub">M83 · Hurry Up, We're Dreaming</p>
        </div>
        <div class="np-scrub-block">
          <div
            class="np-seek hit"
            role="slider"
            aria-label="Playback position"
            aria-valuemin={0}
            aria-valuemax={243}
            aria-valuenow={84}
            aria-valuetext="1 minute, 24 seconds of 4 minutes, 3 seconds total"
            tabindex="0"
          >
            <div class="np-wave-viz" aria-hidden="true">
              <svg class="np-wave-svg" viewBox="0 0 400 48" preserveAspectRatio="none">
                <path
                  class="np-wave-back"
                  d="M0,28 Q25,8 50,28 T100,28 T150,20 T200,32 T250,14 T300,30 T350,18 T400,26 L400,48 L0,48 Z"
                />
                <path
                  class="np-wave-front"
                  d="M0,30 Q30,12 60,30 T120,22 T180,34 T240,16 T300,28 T360,20 T400,24 L400,48 L0,48 Z"
                />
              </svg>
              <div class="np-wave-progress" style="width: 32%"></div>
            </div>
            <div class="np-progress" aria-hidden="true">
              <span class="np-time">1:24</span>
              <div class="np-bar">
                <div class="np-fill"></div>
                <div class="np-knob" style="left: 32%"></div>
              </div>
              <span class="np-time">4:03</span>
            </div>
          </div>
        </div>
        <div class="np-secondary" aria-label="Extra controls">
          <button type="button" class="np-aux" aria-label="Shuffle off">🔀</button>
          <button type="button" class="np-aux" aria-label="Repeat all">🔁</button>
        </div>
      </div>
      <div class="np-controls">
        <button type="button" class="np-btn" aria-label="Previous">|◀</button>
        <button type="button" class="np-btn np-main" aria-label="Pause">❚❚</button>
        <button type="button" class="np-btn" aria-label="Next">▶|</button>
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
    padding: clamp(12px, 2.5vw, 24px) clamp(16px, 3vw, 36px);
    background: linear-gradient(165deg, rgba(112, 204, 204, 0.08) 0%, var(--auto-hu-bg) 45%, #0e1010 100%);
    display: flex;
    align-items: center;
    box-sizing: border-box;
  }
  .np {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    gap: clamp(16px, 4vw, 40px);
    width: 100%;
    min-height: 0;
  }
  .np-art {
    flex-shrink: 0;
    width: clamp(104px, 18vw, 220px);
    aspect-ratio: 1;
    max-height: 100%;
    border-radius: 14px;
    background: linear-gradient(145deg, rgba(112, 204, 204, 0.25), var(--auto-hu-surface-elevated));
    border: 1px solid var(--auto-hu-border);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.35);
  }
  .np-maincol {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    gap: clamp(8px, 1.5vw, 14px);
    justify-content: center;
  }
  .np-center {
    text-align: left;
  }
  .np-kicker {
    margin: 0 0 4px;
    font-size: 0.72rem;
    font-weight: 800;
    letter-spacing: 0.12em;
    text-transform: uppercase;
    color: var(--auto-hu-accent);
  }
  .np-title {
    margin: 0;
    font-size: clamp(1.15rem, 2.8vw, 1.75rem);
    font-weight: 800;
    color: var(--auto-hu-text);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .np-sub {
    margin: 6px 0 0;
    font-size: clamp(0.85rem, 1.9vw, 1.05rem);
    color: var(--auto-hu-muted);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .np-scrub-block {
    max-width: 100%;
  }
  .np-seek.hit {
    display: flex;
    flex-direction: column;
    gap: 8px;
    padding: 10px 12px;
    border-radius: 14px;
    border: 1px solid var(--auto-hu-border);
    background: rgba(0, 0, 0, 0.25);
    cursor: pointer;
  }
  .np-seek:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  .np-wave-viz {
    position: relative;
    height: clamp(36px, 5vw, 44px);
    border-radius: 8px;
    overflow: hidden;
    background: rgba(0, 0, 0, 0.35);
    border: 1px solid var(--auto-hu-border);
  }
  .np-wave-svg {
    display: block;
    width: 100%;
    height: 100%;
  }
  .np-wave-back {
    fill: rgba(112, 204, 204, 0.12);
  }
  .np-wave-front {
    fill: rgba(112, 204, 204, 0.22);
  }
  .np-wave-progress {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    background: linear-gradient(90deg, rgba(112, 204, 204, 0.35), transparent);
    pointer-events: none;
    border-right: 3px solid var(--auto-hu-accent);
    box-sizing: border-box;
  }
  @media (prefers-reduced-motion: no-preference) {
    .np-wave-svg {
      animation: np-wave-pan 14s ease-in-out infinite alternate;
    }
  }
  @media (prefers-reduced-motion: reduce) {
    .np-wave-svg {
      animation: none;
    }
  }
  @keyframes np-wave-pan {
    from {
      transform: translateX(-4%);
    }
    to {
      transform: translateX(4%);
    }
  }
  .np-progress {
    display: flex;
    align-items: center;
    gap: 12px;
    max-width: 100%;
  }
  .np-time {
    font-size: clamp(0.75rem, 1.4vw, 0.88rem);
    font-variant-numeric: tabular-nums;
    color: var(--auto-hu-muted);
    flex-shrink: 0;
  }
  .np-bar {
    position: relative;
    flex: 1;
    height: 6px;
    border-radius: 4px;
    background: rgba(255, 255, 255, 0.12);
    overflow: visible;
    min-width: 64px;
  }
  .np-fill {
    width: 32%;
    height: 100%;
    background: linear-gradient(90deg, var(--auto-hu-accent), #8fd9d9);
    border-radius: 4px;
  }
  .np-knob {
    position: absolute;
    top: 50%;
    width: 16px;
    height: 16px;
    margin-left: -8px;
    margin-top: -8px;
    border-radius: 50%;
    background: var(--pa2-on-primary);
    border: 2px solid var(--auto-hu-accent);
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.45);
  }
  .np-secondary {
    display: flex;
    align-items: center;
    gap: 10px;
  }
  .np-aux {
    min-width: var(--auto-min-hit);
    min-height: 48px;
    padding: 0 16px;
    border-radius: 12px;
    border: 1px solid var(--auto-hu-border);
    background: var(--auto-hu-surface-elevated);
    color: var(--auto-hu-text);
    font-size: 1.1rem;
    font-family: inherit;
  }
  .np-aux:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  .np-controls {
    display: flex;
    flex-shrink: 0;
    gap: clamp(10px, 1.8vw, 18px);
    align-items: center;
  }
  .np-btn {
    min-width: var(--auto-min-hit);
    min-height: var(--auto-min-hit);
    padding: 0 16px;
    border: 1px solid var(--auto-hu-border);
    border-radius: 14px;
    background: var(--auto-hu-surface-elevated);
    color: var(--auto-hu-text);
    font-size: 0.95rem;
    font-weight: 700;
    font-family: inherit;
  }
  .np-btn.np-main {
    min-width: 72px;
    min-height: 72px;
    border-radius: 18px;
    background: var(--auto-hu-accent);
    color: var(--pa2-on-primary);
    border-color: rgba(112, 204, 204, 0.65);
    font-size: 1.05rem;
    box-shadow: 0 8px 20px rgba(112, 204, 204, 0.2);
  }
  .np-btn:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 3px;
  }
  @container (max-width: 520px) {
    .auto-screen {
      aspect-ratio: unset;
      min-height: 360px;
    }
    .np {
      flex-wrap: wrap;
      justify-content: center;
    }
    .np-maincol {
      flex: 1 1 100%;
      order: 2;
      text-align: center;
    }
    .np-center {
      text-align: center;
    }
    .np-art {
      order: 1;
    }
    .np-controls {
      order: 3;
      width: 100%;
      justify-content: center;
    }
    .np-secondary {
      justify-content: center;
    }
  }
</style>
