# Android Auto — PA2 brand carryover and Google alignment

This document defines a **single strategy** for the plugin MVP: stay **recognizably Power Ampache 2** while staying inside **Google’s media-for-cars rules**. It does **not** require custom Android Auto UI.

## Principles (both must hold)

1. **Host renders chrome; app renders content** — On the standard **Media3** path, the **head unit** controls layout, fonts, and colors. PA2 **does not** apply `PowerAmpache2Theme` to those pixels. Fidelity to PA2 on Auto is expressed through **what** you put in the session, not **how** the OEM draws chrome.

2. **Faithful where PA2 is authoritative** — **Artwork**, **catalog-backed titles**, **product language**, and **browse structure** should match the main app’s intent: music-first, same library, same visual identity **in the art and words** users see on rows and Now Playing.

3. **Google is authoritative on safety and eligibility** — [Create audio media apps](https://developer.android.com/training/cars/media), [distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards), [app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality), and [errors in car media](https://developer.android.com/training/cars/media/errors) bound **depth**, **interaction**, and **error** behaviour. When in tension, **safety and platform rules win**; PA2 expresses brand **within** those limits.

---

## Carryover matrix — what transfers to Auto (MVP)

| PA2 design element | Phone (Compose) | Android Auto (Media3) | Google alignment note |
|--------------------|-----------------|------------------------|------------------------|
| **Color tokens** (`primaryDark`, surfaces, etc.) | Full `PowerAmpache2Theme` | **Not** applied to OEM chrome | N/A — host theme |
| **Typography (Nunito)** | Full Material 3 type scale | **Not** chosen by app for projected UI | Host font |
| **Album / playlist / track artwork** | Lists, player, placeholders | **`MediaItem` / metadata artwork URIs** — same pipeline as PA2 | Rich metadata encouraged; size/aspect per [media app guidance](https://developer.android.com/guide/topics/media-apps/media-apps-overview) |
| **Title / artist / album strings** | UI labels | **`MediaMetadata`** (title, artist, album, display fields) | Short, glanceable strings; match distraction intent |
| **Voice-friendly copy** | Product copy | Same **wording** rules in metadata (avoid TTS-hostile characters where possible) | Supports voice actions |
| **Terminology** (library, playlist, smartlist, …) | Consistent across app | **Browse node labels** and error strings | Clear, short; no jargon on every row |
| **Music-first clarity** | Layout and IA | **Shallow root**, limited top-level nodes, predictable ordering | Shallow browse aligns with [UX research](../ux-research/02-task-analysis-and-flows.md) and safeguard **intent** |
| **Tone** (direct, neutral errors) | Toasts, screens | **`PlaybackException` / error messaging** exposed per [errors](https://developer.android.com/training/cars/media/errors) | Short, actionable |
| **Brand philosophy** | Full design system | **Information architecture** + **metadata completeness**, not custom pixels | Content parity, not chrome parity |

**Summary:** On Auto, PA2 “design fidelity” = **same art + same words + same IA intent**, implemented as **Media3 data**. **Not** = teal bars and Nunito on the head unit.

---

## Google alignment — concrete practices

Use this as a checklist alongside [../ux-research/05-design-guardrails-checklist.md](../ux-research/05-design-guardrails-checklist.md).

1. **Browsable / playable tree** — Expose a clear `MediaItem` hierarchy; no fake custom surfaces for core browse ([Create audio media apps](https://developer.android.com/training/cars/media)).

2. **Metadata** — Populate **title**, **artist**, **album** (and artwork) so Now Playing and browse rows are **complete**; avoid empty or misleading fields. Prefer **short** primary lines for glance time.

3. **Shallow roots** — Align PA2’s “music-first” IA with **few** top-level choices and recents/playlists near the top — matches both PA2 priorities and **low cognitive load** in motion.

4. **Errors** — Use platform-appropriate error surfacing; copy stays **brief** and **neutral** (PA2 tone), without multi-step recovery while driving.

5. **Voice** — Wire **voice actions** where applicable; metadata quality directly affects recognition and display.

6. **No competing chrome** — Do not add **custom** Android Auto layouts for MVP; avoids conflict with host templates and review expectations.

---

## Resolving tension (PA2 vs Google)

| Tension | Resolution |
|---------|------------|
| PA2 shows **rich** metadata on phone | Auto: **primary line + secondary** only; extra detail on **phone** or when parked |
| PA2 **deep** library (artists → albums → …) | Auto: **default** path stays **shallow**; deep paths exist but are not the primary driving flow |
| **Brand** wants visible **teal** | Auto: brand shows in **artwork** and **app icon** on device; **not** by forcing colors into host UI |
| **Long** track / album names | **Ellipsize** or shorten for browse rows per [01-brand-and-language.md](01-brand-and-language.md) voice rules; full strings where the platform allows detail views |

---

## MVP scope statement

- **In scope:** Media3 session, browse tree, metadata, artwork, errors, voice — **PA2-aligned content and IA**, **Google-compliant** behaviour.
- **Out of scope for MVP:** Custom Car App Library templates, bespoke head-unit UI, applying **Compose color/typography tokens** to projected Auto chrome.

---

## Related

- [00-design-system-index.md](00-design-system-index.md) — two surfaces  
- [01-brand-and-language.md](01-brand-and-language.md) — tokens (phone) + voice  
- [../ux-research/01-platform-constraint-sheet.md](../ux-research/01-platform-constraint-sheet.md) — allowed / discouraged  
- [../user-stories.md](../user-stories.md) — MVP stories  

---

*Last updated: 2026-04-10*
