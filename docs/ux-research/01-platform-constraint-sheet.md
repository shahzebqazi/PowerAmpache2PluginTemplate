# Platform constraint sheet — Android Auto + Power Ampache 2

Use this sheet when scoping **Android Auto** behaviour: what the **head unit** controls, what the **app** must supply, and what is **out of scope** for a standard media-browser app. Pair with [bibliography.md](bibliography.md) for citations.

## Integration path (upstream Power Ampache 2)

**Confirmed from `Power-Ampache-2` (code review):**

- **`androidx.media3`**: `MediaSession` built in `ServiceModule` and hosted by `SimpleMediaService`, which extends **`MediaSessionService`** (not `MediaBrowserService` / not `MediaLibraryService` as a class name).
- **Manifest** (`AndroidManifest.xml`): `SimpleMediaService` declares intent filters for `android.media.browse.MediaBrowserService` and `android.support.v4.media.MediaBrowserCompat` so the system can treat it as a **media browser endpoint** alongside the session token.

**Code-scan caveat (avoid overstating shipped behaviour):** A **`MediaLibraryService`** / **`MediaLibrarySession`** implementation with browse callbacks (e.g. `onGetChildren`) was **not found** under `Power-Ampache-2/**/*.kt` in this workspace snapshot. Android Auto **library browsing** still requires meeting Google’s media-for-cars expectations (see [Create audio media apps](https://developer.android.com/training/cars/media) and [Configure the manifest](https://developer.android.com/training/cars/media/configure-manifest)). **Treat “browse tree on Auto” as target UX and engineering verification**, not something this doc proves from code alone.

**Conclusion:** PA2 is aligned with the **Media3 session service** integration style Google documents for car media apps. The **Cars App Library** templated path is **not** used in PA2’s player service. This template’s **`app/`** module is a **Compose** reference for the plugin; full PA2 phone UI is **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)**; standard Auto media is **not** `androidx.car.app`.

| Path | PA2 upstream | This umbrella repo |
|------|----------------|---------------------|
| Media3 **`MediaSessionService`** + **`MediaSession`** + manifest **media-browser** intents | Yes | **`app/`** plugin module + docs — **Media3** in plugin / PA2 clone |
| **Full library browse** on Auto (`MediaLibraryService` / `MediaLibrarySession` pattern) | **Verify in upstream** (not confirmed in this doc’s code scan) | Plugin template on your branch when present |
| Car App Library (custom templates) | Not in PA2 player integration | N/A in this repo |

**Implication:** UX research and design-system “Auto surface” sections apply to **host-rendered media UI** for the **full car stack** in **Power-Ampache-2** and any **plugin** that contributes **Media3** session/browse behavior. This repo **does** include a **`app/`** Compose module (plugin shell); **phone** branding uses **PowerAmpache2Theme** — see [../design-system/00-design-system-index.md](../design-system/00-design-system-index.md).

---

## Allowed (media app on Android Auto / AAOS)

- Expose a **browsable/playable** tree via `MediaItem` (`FLAG_BROWSABLE`, `FLAG_PLAYABLE`).
- Provide **playback controls** through `MediaSession` (play, pause, skip, seek where supported).
- Support **voice actions** where documented ([Voice actions](https://developer.android.com/training/cars/media/voice-actions)).
- Surface **metadata and artwork** per media app architecture guidelines.
- Meet **Google Play car quality** requirements for the **media** category before distribution listing.

---

## Discouraged / host-limited

- **Deep custom layouts** on Auto for media apps using the browser path: the **host** renders browse and now-playing; PA2 controls **data**, not pixel layout.
- **Long lists** without pagination: poor glanceability; prefer shallow trees and recents (product decision, aligns with distraction guidance).
- **Relying on DHU alone** for sign-off: latency, input mode, and day/night may differ on OEM head units ([Testing](https://developer.android.com/training/cars/testing)).

---

## Forbidden / must not (platform + policy)

- **Secondary tasks** that violate Google’s **distraction safeguards** for media apps ([Implement distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards)).
- **In-app video** or non-media interactions while driving outside allowed app types ([Cars media overview](https://developer.android.com/training/cars/media)).
- **Free-form typing** for primary driving flows where voice or shallow browse is required by policy (treat as **guardrail** in [05-design-guardrails-checklist.md](05-design-guardrails-checklist.md)).
- Play listing **without** meeting **Android app quality for cars** for the relevant category.

---

## Optional path: Car App Library (custom templates)

If a product **migrates** or prototypes with [Build templated media apps](https://developer.android.com/training/cars/apps/media):

- Supported **only on Android Auto** (not the same as full AAOS templated surface mix — verify current docs).
- Use **provided templates** for lists, grids, and actions; custom UI is constrained by the library and host validation.

This repo’s **`app/`** module is **not** the Cars App Library path; **standard** media-browser / **Media3** integration remains the **default** for Auto unless the maintainer chooses otherwise.

---

## Testing

- **Desktop Head Unit (DHU)** for development; document gaps vs production HU (touch vs rotary, screen geometry, performance).
- **Host validation** / quality guidelines for store submission.

---

## Open items (verify on milestone)

- [ ] **Browse implementation:** Confirm whether production PA2 exposes a **full `MediaItem` browse tree** on Android Auto (vs now-playing / queue only), and whether `MediaLibraryService` / `MediaLibrarySession` should be added per current Media3 guidance.
- [ ] **AAOS**-specific behaviour if PA2 targets embedded Automotive OS builds.
- [ ] Current **row count / browse depth** limits from [distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards) and Play quality docs (copy **quoted** limits into this sheet when confirmed).
