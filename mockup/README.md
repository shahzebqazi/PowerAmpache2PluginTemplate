# Power Ampache 2 × Android Auto — UI mockup

**Svelte + Vite** browser preview for **P0–P3** flows and labels in [`docs/ux-research/08-mockup-handoff-package.md`](../docs/ux-research/08-mockup-handoff-package.md) (mockups + Media3 checklist). Naming matches root [`AGENTS.md`](../AGENTS.md).

## What this is

**Plugin / Android Auto only** — there are **no handheld phone mockup frames**. Full-app UX is **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)**.

| Area | Styling |
|------|---------|
| **Browse** | Separate frames: **Playlists**, **Recommended**, **Recents**, **Frequently played**, **Newly added** — host-like lists with **`AutoBrowseSection.svelte`** (PA2 accents, ~56px rows). |
| **Now playing, queue, error** | **`AutoNowPlaying`**, **`AutoQueue`**, **`AutoErrorState`** — same **`FrameLabel`** **`surface="auto-pa2"`** pattern. |
| **Auto — host-only (neutral badge)** | **`surface="auto"`** when a frame should stay strictly grey-badge / host-chrome mental model. |

This repo’s **umbrella** has **no** root Compose module — **DHU** uses a **Media3** APK (**plugin template** and/or PA2). Optional **`public/dhu/*.png`** shots support the **home** hero.

**Typography:** **`public/fonts/Nunito-Variable.ttf`** (OFL in `public/fonts/OFL.txt`); no Google Fonts CDN in `index.html`.

**Research (`#/research`):** Four `<details>` sections render the same markdown as `docs/ux-research/00-executive-summary.md`, `07-…`, `02-…`, and `08-mockup-handoff-package.md` (built with [marked](https://github.com/markedjs/marked); Mermaid blocks appear as fenced code).

**Auto design (`#/design`):** Puts **Android Auto** integration first (full **platform constraint sheet** + **design-system index**). **PowerAmpache2Theme** swatches and Nunito sit under **Phone reference** (handheld app only) — they are not head-unit chrome.

**Architecture (`#/architecture`):** Mermaid **UML-style** diagrams (system context + simplified plugin class diagram) for the umbrella repo + nested **PowerAmpache2PluginTemplate** stack — deployed with the same GitHub Pages build as other routes.

## Hardening and stakeholder expectations

- **`FrameLabel`:** **`surface="auto-pa2"`** on DHU car frames uses a **teal badge** and documents that PA2 accents illustrate **session/metadata identity**; **`surface="auto"`** keeps a **neutral** badge for strict host-only messaging.
- **Motion:** Decorative CSS animations/transitions respect **`prefers-reduced-motion`** on player mockups and home nav buttons where implemented.
- **Compliance:** These frames **do not** replace [Play car quality](https://developer.android.com/docs/quality-guidelines/car-app-quality) or [distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards) verification — see [`docs/ux-research/05-design-guardrails-checklist.md`](../docs/ux-research/05-design-guardrails-checklist.md) and **Browser mockups vs shipping verification** there.

## Run the mockup (browser)

```bash
cd mockup
npm install   # first time
npm run dev
```

Open the URL Vite prints (usually `http://localhost:5173`). Use the left rail to switch screens.

## Desktop Head Unit (DHU)

The **DHU does not load this web UI**. It connects to **Android Auto on a device or emulator** and shows whatever the **installed car-capable app** projects.

This umbrella repo has **no** root Android module — **DHU** uses a **Media3** APK. To exercise **Android Auto** / **Media3** in DHU:

1. Install **Android Auto Desktop Head Unit** via Android Studio → **SDK Manager** → **SDK Tools** (see [Test Android apps for cars](https://developer.android.com/training/cars/testing)).
2. Create/start an **AVD** or plug in a **physical device** with developer options and USB debugging.
3. Build and install a **media** app that registers on the head unit (e.g. clone **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)** and install its debug APK, or another maintainer-directed build).
4. Start the **Desktop Head Unit** from Android Studio (**Tools** → **Android Auto** → **Desktop Head Unit**) or run the `desktop-head-unit` binary from your SDK extras path.
5. On the device, enable Android Auto developer mode if prompted; connect DHU to the phone/emulator per Google’s testing doc.

**Power Ampache 2 production** Auto UI is **host-rendered media** (Media3) — confirm browse/session behaviour in upstream before treating DHU output as shipping PA2.

### DHU screenshots for this mockup

1. Connect a device or emulator with **Android Auto** dev options and a **car-capable** media app installed (not the root **phone-only** `app`).
2. Forward the head-unit port (from [DHU testing](https://developer.android.com/training/cars/testing/dhu)):  
   `adb forward tcp:5277 tcp:5277`
3. Run the DHU binary (e.g. `$ANDROID_HOME/extras/google/auto/desktop-head-unit` on macOS/Linux).
4. In the **terminal where DHU is running**, save frames:  
   `screenshot /tmp/dhu-browse.png` — navigate UI, then `screenshot /tmp/dhu-now-playing.png` (or any paths you prefer).
5. Copy captures into **`mockup/public/dhu/`** (e.g. `dhu-now-playing.png` for the home hero) and adjust paths if needed.

## Related docs

- [`docs/design-system/00-design-system-index.md`](../docs/design-system/00-design-system-index.md)
- [`docs/ux-research/05-design-guardrails-checklist.md`](../docs/ux-research/05-design-guardrails-checklist.md)
