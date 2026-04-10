<script lang="ts">
  import type { AppRoute } from './hash-routes'
  import { SANDBOX_REPO_URL } from './repo-url'

  interface Props {
    route: AppRoute
  }

  let { route }: Props = $props()

  function navClass(page: AppRoute['name']): string {
    const active = route.name === page
    return active ? 'nav-link active' : 'nav-link'
  }
</script>

<nav class="site-nav" aria-label="Site sections">
  <a class={navClass('home')} href="#/">Home</a>
  <span class="sep" aria-hidden="true">·</span>
  <a class={navClass('design')} href="#/design">Auto design</a>
  <span class="sep" aria-hidden="true">·</span>
  <a class={navClass('research')} href="#/research">Research</a>
  <span class="sep" aria-hidden="true">·</span>
  <a class={navClass('architecture')} href="#/architecture">Architecture</a>
  <span class="sep" aria-hidden="true">·</span>
  <a class={navClass('mockup')} href="#/mockup">Mockups</a>
  <span class="sep" aria-hidden="true">·</span>
  <a class="nav-link nav-external" href={SANDBOX_REPO_URL} target="_blank" rel="noreferrer">GitHub</a>
</nav>

<style>
  .site-nav {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: 6px 10px;
    font-size: 0.8rem;
    font-weight: 700;
  }

  .nav-link {
    color: var(--pa2-on-surface-variant);
    text-decoration: none;
    border-radius: 6px;
    padding: 4px 6px;
    margin: -4px -6px;
  }

  .nav-link:hover {
    color: var(--pa2-on-surface);
  }

  .nav-link.active {
    color: var(--pa2-primary);
  }

  .nav-link:focus-visible {
    outline: 2px solid var(--pa2-tertiary);
    outline-offset: 2px;
  }

  .sep {
    color: var(--mock-chrome-border);
    user-select: none;
  }

  .nav-external {
    color: var(--pa2-tertiary);
  }

  .nav-external:hover {
    color: var(--pa2-on-surface);
    text-decoration: underline;
    text-underline-offset: 2px;
  }
</style>
