# Android Auto — what the platform allows

Use this when you scope **behaviour** on the head unit: who draws the screen, what the app must supply, and what Google says no to.

## How Power Ampache 2 fits in today

The **main** Power Ampache 2 codebase uses **Media3**: a `MediaSession` built in DI and hosted by **`SimpleMediaService`** (`MediaSessionService`). The manifest exposes the legacy **media browser** intents so Android can treat the app as a **browse endpoint** next to the session.

**Caveat:** Whether **full** `MediaLibraryService` / `MediaLibrarySession` browse exists should be **verified in code** on **`dev`** or **Power-Ampache-2**. This **`mockups`** branch has **no** source tree — docs only. See [Create audio media apps](https://developer.android.com/training/cars/media) for expectations.

**Standard** Android Auto media is **not** the Cars App Library template path unless you deliberately switch.

| Topic | Main PA2 app | Plugin template (`dev` branch) |
|--------|----------------|--------------------------------|
| Media3 session service + media browser intents | Yes | When implemented in `app/` |
| Full browse tree on Auto | Confirm in upstream | Verify in code on `dev` |
| Car App Library (custom templates) | Not the default player path | Not the default |

**Takeaway:** UX notes about “Auto surfaces” mean **host-rendered** media UI. Phone branding still comes from **PowerAmpache2Theme** ([../design-system/00-design-system-index.md](../design-system/00-design-system-index.md)).

## What you can do (media app on Android Auto)

- Offer a **browsable / playable** tree with `MediaItem` flags.
- Drive **playback** through `MediaSession` — play, pause, skip, seek when the platform allows.
- Support **voice actions** where Google documents them ([Voice actions](https://developer.android.com/training/cars/media/voice-actions)).
- Ship **metadata and artwork** the way the media app architecture describes.
- Meet **[Android app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality)** for the media category if you want Play distribution.

## What gets awkward or host-limited

- **Painting your own** full-screen car layouts on the **standard browser path** — the **host** renders browse and now playing; you feed **data**.
- **Very long** scrolling lists without structure — bad for glance time; shallow roots and recents help.
- **Trusting only the DHU** — latency, input (touch vs knob), and day/night differ on real head units ([Testing](https://developer.android.com/training/cars/testing)).

## What to avoid (platform + policy)

- Flows that break Google’s **[distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards)** for media.
- **Video** or non-media tasks while driving outside allowed app types.
- **Typing** as the main way to do primary playback tasks when voice or shallow browse is expected — treat as a **guardrail** ([05-design-guardrails-checklist.md](05-design-guardrails-checklist.md)).
- Shipping to Play **without** meeting the current **car quality** checklist for your category.

## Optional: Car App Library

If a product moved to [templated media apps](https://developer.android.com/training/cars/apps/media), you’d use **Google’s templates** — different constraints, not the default for PA2’s current player service.

## Testing

- **DHU** for development; write down where it differs from a real car (touch vs rotary, screen shape, performance).
- **Host validation** against Play’s car quality expectations before release.

## Still to verify on a milestone

- [ ] How **complete** browse is in production PA2 on Auto (full tree vs queue-heavy).
- [ ] **AAOS**-specific needs if you target embedded Automotive OS.
- [ ] Exact **row / depth** limits from the current distraction and quality docs — quote them into this sheet when locked.

---

*Last reviewed: 2026-04-07*
