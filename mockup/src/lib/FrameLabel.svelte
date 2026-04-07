<script lang="ts">
  /**
   * Phone: PA2 badge.
   * Auto: neutral badge (host-only chrome mental model).
   * Auto + PA2: teal badge — use for DHU targets where we show PA2 identity via session, artwork, and accent (real AA still host-rendered).
   */
  interface Props {
    label: string
    note?: string
    surface?: 'phone' | 'auto' | 'auto-pa2'
  }
  let { label, note = '', surface = 'phone' }: Props = $props()
</script>

<div
  class="frame-label"
  class:frame-label--auto={surface === 'auto'}
  class:frame-label--auto-pa2={surface === 'auto-pa2'}
  role="status"
>
  <span
    class="badge"
    class:badge--auto={surface === 'auto'}
    class:badge--auto-pa2={surface === 'auto-pa2'}>{label}</span>
  {#if note}
    <span class="note">{note}</span>
  {/if}
</div>

<style>
  .frame-label {
    display: flex;
    flex-wrap: wrap;
    align-items: baseline;
    gap: 0.5rem 0.75rem;
    padding: 0.5rem 0.75rem;
    background: rgba(0, 0, 0, 0.35);
    border-bottom: 1px solid var(--mock-chrome-border, #333);
  }

  .frame-label--auto {
    background: rgba(30, 30, 30, 0.85);
    border-bottom-color: #3a3a3a;
  }

  .frame-label--auto-pa2 {
    background: rgba(18, 24, 22, 0.92);
    border-bottom-color: var(--auto-hu-border, rgba(112, 204, 204, 0.25));
  }

  .badge {
    font-size: 0.7rem;
    font-weight: 700;
    letter-spacing: 0.06em;
    text-transform: uppercase;
    color: var(--pa2-primary, #70cccc);
  }

  .badge--auto {
    color: #b8bdc0;
  }

  .badge--auto-pa2 {
    color: var(--pa2-primary, #70cccc);
  }

  .note {
    font-size: 0.75rem;
    color: var(--pa2-on-surface-variant, #cbced1);
    max-width: 42rem;
    line-height: 1.35;
  }

  .frame-label--auto .note {
    color: #c8cdd0;
  }

  .frame-label--auto-pa2 .note {
    color: var(--pa2-on-surface-variant, #cbced1);
  }
</style>
