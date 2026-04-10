<script lang="ts">
  import FrameLabel from './FrameLabel.svelte'
</script>

<div class="auto-shell">
  <FrameLabel
    surface="auto-pa2"
    label="Auto — PA2 session (DHU)"
    note="Short copy + one primary action at driving scale. Secondary path points to the phone plugin when the host cannot fix credentials or server URL."
  />
  <div class="auto-screen">
    <div class="panel">
      <p class="icon" aria-hidden="true">⚠</p>
      <p class="message">Can’t reach your Ampache server. Check network or open the plugin on your phone to verify the URL.</p>
      <div class="actions">
        <button type="button" class="retry primary">Retry</button>
        <button type="button" class="retry secondary">Open plugin on phone</button>
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
    align-items: center;
    justify-content: center;
    padding: clamp(16px, 3vw, 32px);
    background: linear-gradient(165deg, rgba(245, 147, 171, 0.06) 0%, var(--auto-hu-bg) 40%);
    box-sizing: border-box;
  }
  .panel {
    max-width: min(480px, 92%);
    padding: clamp(20px, 3vw, 28px);
    border-radius: 16px;
    background: var(--auto-hu-surface-elevated);
    border: 1px solid var(--auto-hu-border);
  }
  .icon {
    margin: 0 0 8px;
    font-size: 1.5rem;
    line-height: 1;
    color: var(--pa2-error);
  }
  .message {
    margin: 0 0 20px;
    font-size: clamp(1rem, 2.1vw, 1.15rem);
    line-height: 1.45;
    font-weight: 600;
    color: var(--auto-hu-text);
  }
  .actions {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  .retry {
    width: 100%;
    min-height: var(--auto-min-hit);
    padding: 14px 18px;
    border-radius: 14px;
    font-family: inherit;
    font-weight: 800;
    font-size: 1rem;
    cursor: pointer;
  }
  .retry.primary {
    border: 1px solid rgba(112, 204, 204, 0.5);
    background: var(--auto-hu-accent);
    color: var(--pa2-on-primary);
  }
  .retry.secondary {
    border: 1px solid var(--auto-hu-border);
    background: transparent;
    color: var(--auto-hu-text);
  }
  .retry:focus-visible {
    outline: 2px solid var(--auto-hu-focus);
    outline-offset: 2px;
  }
  @container (max-width: 520px) {
    .auto-screen {
      aspect-ratio: unset;
      min-height: 280px;
    }
  }
</style>
