# Android Auto — media app compliance (reference)

Use this alongside [Google’s car app quality guidelines](https://developer.android.com/docs/quality-guidelines/car-app-quality) (filter **Media**) and the [Create audio media apps](https://developer.android.com/training/cars/media) training path. This file maps **official expectations** to a **Media3 Android Auto plugin** `app` module in **your consumer tree** (verify manifest and services in the project you ship); it is not a substitute for Play pre-launch review or OEM testing.

## Platform integration (documentation alignment)

| Expectation | Source | Plugin `app` module (verify in tree) |
|-------------|--------|----------------------------------|
| Host discovers a **media browser** implementation | [Configure the manifest](https://developer.android.com/training/cars/media/configure-manifest) | **Media3** service declares `android.media.browse.MediaBrowserService`, `androidx.media3.session.MediaLibraryService`, and `MediaSessionService` intents (verify class names in your `AndroidManifest.xml`). |
| **Android Auto** app metadata | [Add support for Android Auto](https://developer.android.com/training/cars/media/auto) | `com.google.android.gms.car.application` → `automotive_app_desc.xml` with `<uses name="media"/>`. |
| **Launcher / tray** icon for media UI | [Launcher icon](https://developer.android.com/training/cars/media/configure-manifest#launcher-icon) | `android:icon` / `android:roundIcon` on `<service>` (and app `icon`/`roundIcon`). |
| **Attribution** (monochrome) icon | [Attribution icon](https://developer.android.com/training/cars/media/configure-manifest#attribution-icon) | `androidx.car.app.TintableAttributionIcon` → `@drawable/…` monochrome attribution resource (name per product). |
| **Display name** on car UI | [App label](https://developer.android.com/training/cars/media/configure-manifest) | `android:label` on service + `application` `android:label`. |
| **Voice**: `MEDIA_PLAY_FROM_SEARCH` | [Support voice actions](https://developer.android.com/training/cars/media/voice-actions) | Intent filter action `android.media.action.MEDIA_PLAY_FROM_SEARCH` on the media service (handler depth is product/upstream; declaring the filter satisfies discovery/lint expectations). |
| **Media3** session for playback/browse | [Enable playback controls](https://developer.android.com/training/cars/media/enable-playback) | `MediaLibraryService` + `MediaLibrarySession`; `onConnect` augments `Player` commands; playback engine wired per product (e.g. ExoPlayer). |
| **Errors** in session | [Errors in car media apps](https://developer.android.com/training/cars/media/errors) | Verify in code review + DHU: playback/API failures surface sensibly via session/queue (ongoing hardening per backlog). |
| **Foreground** media service where required | [Services overview](https://developer.android.com/develop/background-work/services) + FGS type | `FOREGROUND_SERVICE` + `FOREGROUND_SERVICE_MEDIA_PLAYBACK`; service `foregroundServiceType="mediaPlayback"`; `startForeground` with `FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK` on API 29+. |
| **Exported service** without a custom permission | Lint `ExportedService` | [Google notes](https://developer.android.com/training/cars/media/configure-manifest) this is often acceptable; optional [package validation](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy#package-validation) for stricter binding. |

## Google Play — car quality (media)

- Full, versioned criteria: [Car app quality](https://developer.android.com/docs/quality-guidelines/car-app-quality) (select **Media**).
- Validate on **DHU** and, where applicable, **Android Automotive** emulator per the same page’s “Test your app” section.
- This repo standardizes DHU via `./android-auto-agents/scripts/dhu-start.sh` from the repo root ([skill](../skills/dhu-usb-session.md)).

## Optional manifest enhancements

| Item | Source | Status |
|------|--------|--------|
| **Accent / theme** for in-car controls | [Accent color meta-data](https://developer.android.com/training/cars/media/configure-manifest) (`com.google.android.gms.car.application.theme`) | Optional; defaults come from app theme if omitted. |
| **HostValidator** / custom UI | [Car App Library](https://developer.android.com/training/cars/apps) | **N/A** for host-rendered **Media3** path; applies if you add `androidx.car.app` templates. |

## Phone product

Handheld **Power Ampache 2** UX lives in **[Power-Ampache-2](https://github.com/icefields/Power-Ampache-2)**. This umbrella repo has **no** root Compose module — use **`mockup/`** for phone + Auto hierarchy review.

---

**Review cadence:** Re-check when changing **`PowerAmpache2PluginTemplate`** `AndroidManifest.xml`, media browser / library services, or Media3 playback wiring; re-run `:app:lintDebug` and DHU flows before claiming release readiness.
