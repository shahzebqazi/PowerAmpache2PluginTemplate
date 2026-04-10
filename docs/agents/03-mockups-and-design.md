# Mockups and design (post tech-lead)

## Product framing

- **Power Ampache 2** (**[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)**) is the **primary phone / handheld music app** in scenarios that show a full player, library, or queue on a phone.
- **Power Ampache 2 plugin** (**[PowerAmpache2PluginTemplate](https://github.com/icefields/PowerAmpache2PluginTemplate)**) is a **separate APK** focused on **plugin + Android Auto / Media3** responsibilities — **not** a stand-in for the entire PA2 phone product in stakeholder mocks.

**Do not** rebuild mockups that imply the plugin is the main phone shell unless the product owner explicitly asks for that fiction.

## Frame labelling (`FrameLabel` / handoff package)

- **Phone — Power Ampache 2** (or **Phone — PA2 theme** where tied to **PowerAmpache2Theme**): handheld UI that belongs to the **full app**.
- **Auto — host media**: OEM-rendered **Media3** surface; hierarchy and density, not pixel-perfect OEM chrome.
- **Plugin / AA entry** (optional third label when needed): flows that only exist on the **plugin APK** (e.g. car launcher label **Auto Plugin for Power Ampache 2**).

Update [`docs/ux-research/08-mockup-handoff-package.md`](../../ux-research/08-mockup-handoff-package.md) when scenarios change.

## Implementation alignment (until real integration)

- Prefer **mock use cases** and **`domain/.../model/mocks/`** (and **`app`** test fakes) for presentation work — **do not** expand **`data`/`domain`** without an explicit task.
- This repo has **no** root Compose app — **mockup/** and **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)** carry phone reference; the **plugin** is DHU media when its manifest registers **Media3** / Auto.

## Phone companion — player design direction

Applies to **PA2** full-screen / now-playing patterns (mockup + eventual **Power-Ampache-2** or shared UI):

- Blurred **album art** backdrop (cover + scrim), reduced-motion path.
- Gestures and seek region per design-system touch targets.
- **Android Auto** playback chrome stays **host-controlled**.

## Car — now-playing

Focus on **metadata, artwork, actions, errors** the app supplies to **Media3**; do not treat **`AutoNowPlaying.svelte`** as an OEM pixel spec.

## Mockup site quality

- Clear **H1** + lead per route; hash routes in **`mockup/src/lib/hash-routes.ts`** stay in sync.
- After substantive UI changes: **`npm run build`** and **`npm run check`** under **`mockup/`**.

## References

- [`docs/design-system/00-design-system-index.md`](../../design-system/00-design-system-index.md)
- [`docs/ux-research/01-platform-constraint-sheet.md`](../../ux-research/01-platform-constraint-sheet.md)
- [`docs/ux-research/05-design-guardrails-checklist.md`](../../ux-research/05-design-guardrails-checklist.md)
