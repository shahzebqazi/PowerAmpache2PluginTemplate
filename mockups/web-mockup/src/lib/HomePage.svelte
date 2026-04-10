<script lang="ts">
  import { publicUrl } from './public-url'
  import { SANDBOX_REPO_URL } from './repo-url'

  let heroFailed = $state(false)

  const heroSrc = publicUrl('dhu/dhu-now-playing.png')
</script>

<div class="home">
  <main class="main">
    <div class="hero-card">
      {#if !heroFailed}
        <img
          src={heroSrc}
          alt="Android Auto now playing from the Desktop Head Unit — install a Media3 car-capable APK (e.g. PowerAmpache2PluginTemplate or Power-Ampache-2)"
          class="hero-img"
          width="1920"
          height="1080"
          decoding="async"
          fetchpriority="high"
          onerror={() => (heroFailed = true)}
        />
      {:else}
        <div class="hero-fallback">
          <p>
            Drop a capture at <code>mockups/web-mockup/public/dhu/dhu-now-playing.png</code> if you want the photo here
            (from the DHU <code>screenshot</code> command — see <code>mockups/web-mockup/README.md</code>).
          </p>
        </div>
      {/if}
    </div>

    <h1 class="title">Power Ampache 2 × Android Auto</h1>

    <p class="overview">
      UX research and <strong>browser mockups</strong> for the <strong>Android Auto plugin</strong>
      (<code>PowerAmpache2PluginTemplate</code>): <strong>Media3</strong> browse — playlists, recommended, recents,
      frequently played, newly added — plus session, queue, and errors for the <strong>host</strong>. The full handheld app is
      <strong>Power-Ampache-2</strong> — not mocked here. Validate car behaviour on a device with <strong>DHU</strong>.
      The <a class="overview-link" href="#/design">Auto design</a> page covers platform rules and PA2 identity notes.
    </p>

    <nav class="nav" aria-label="Site sections">
      <a class="nav-btn primary" href="#/mockup">Mockups</a>
      <a class="nav-btn" href="#/architecture">Architecture</a>
      <a class="nav-btn" href="#/design">Auto design</a>
      <a class="nav-btn" href="#/research">UX research</a>
      <a
        class="nav-btn nav-external"
        href={SANDBOX_REPO_URL}
        target="_blank"
        rel="noreferrer">GitHub repo</a>
    </nav>
  </main>
</div>

<style>
  .home {
    min-height: 100svh;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: clamp(20px, 4vw, 40px) clamp(16px, 3vw, 28px);
    box-sizing: border-box;
    background: var(--mock-chrome-bg);
  }

  .main {
    width: 100%;
    max-width: 560px;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    gap: clamp(16px, 3vw, 22px);
  }

  .hero-card {
    width: 100%;
    max-width: min(520px, 100%);
    aspect-ratio: 16 / 9;
    border-radius: 14px;
    overflow: hidden;
    background: #0a0c0b;
    border: 1px solid var(--mock-chrome-border);
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.45);
    flex-shrink: 0;
  }

  .hero-img {
    width: 100%;
    height: 100%;
    display: block;
    object-fit: contain;
    object-position: center center;
  }

  .hero-fallback {
    width: 100%;
    height: 100%;
    min-height: 140px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 16px;
    color: var(--pa2-on-surface-variant);
    font-size: 0.85rem;
    line-height: 1.5;
  }

  .hero-fallback code {
    font-size: 0.78rem;
    color: var(--pa2-on-surface);
  }

  .title {
    margin: 4px 0 0;
    font-size: clamp(1.35rem, 4vw, 1.65rem);
    font-weight: 800;
    line-height: 1.2;
    color: var(--pa2-on-surface);
    font-family: 'Nunito', system-ui, sans-serif;
  }

  .overview {
    margin: 0;
    font-size: 0.9rem;
    line-height: 1.55;
    color: var(--pa2-on-surface-variant);
    max-width: 48ch;
  }

  .overview strong {
    color: var(--pa2-on-surface);
    font-weight: 600;
  }

  .overview-link {
    color: var(--pa2-tertiary);
    font-weight: 600;
    text-decoration: underline;
    text-underline-offset: 2px;
  }

  .overview-link:hover {
    color: var(--pa2-primary);
  }

  .overview-link:focus-visible {
    outline: 2px solid var(--pa2-tertiary);
    outline-offset: 2px;
    border-radius: 4px;
  }

  .nav {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
    gap: 10px 12px;
    margin-top: 6px;
  }

  .nav-btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 12px 20px;
    border-radius: 12px;
    font-size: 0.88rem;
    font-weight: 700;
    font-family: 'Nunito', system-ui, sans-serif;
    text-decoration: none;
    border: 1px solid var(--mock-chrome-border);
    background: var(--pa2-surface-container);
    color: var(--pa2-on-surface);
  }

  @media (prefers-reduced-motion: no-preference) {
    .nav-btn {
      transition: border-color 0.15s ease, color 0.15s ease, background 0.15s ease;
    }
  }

  .nav-btn:hover {
    border-color: var(--pa2-primary);
    color: var(--pa2-primary);
  }

  .nav-btn.primary {
    border-color: var(--pa2-primary);
    background: rgba(112, 204, 204, 0.16);
    color: var(--pa2-primary);
  }

  .nav-btn.primary:hover {
    background: rgba(112, 204, 204, 0.26);
    color: var(--pa2-on-surface);
  }

  .nav-btn:focus-visible {
    outline: 2px solid var(--pa2-tertiary);
    outline-offset: 3px;
  }

  .nav-external {
    border-style: dashed;
  }

  .nav-external:hover {
    border-color: var(--pa2-tertiary);
    color: var(--pa2-tertiary);
  }
</style>
