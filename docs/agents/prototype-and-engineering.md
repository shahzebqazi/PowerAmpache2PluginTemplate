# Prototype labels and engineering checklists

Use this alongside [docs/ux-research/08-prototype-handoff-package.md](../ux-research/08-prototype-handoff-package.md) (human-readable scenario priorities).

## Frame labels (Figma / static)

| Label | When to use |
|-------|-------------|
| **Phone — PA2 theme** | Handheld UI: colors and type from [docs/design-system/01-brand-and-language.md](../design-system/01-brand-and-language.md) |
| **Auto — host media (PA2)** | Android Auto: host-rendered lists and player; **pattern only**, not PA2-colored head units |
| **Phone — PA2 reference (Compose)** | This repo’s `app/` Compose shell — reference for the plugin |

- Use a **neutral badge** on car frames so previews are not read as OEM styling.
- Web previews: respect **`prefers-reduced-motion`**; keep car concepts **static** unless motion is essential.

## Prototypes vs shipping

- Wireframes prove **intent** — hierarchy, copy length, scenarios. They do **not** replace [Android app quality for cars](https://developer.android.com/docs/quality-guidelines/car-app-quality) or [distraction safeguards](https://developer.android.com/training/cars/media/distraction-safeguards).
- **Compliance** is validated in **Kotlin / Media3**, **DHU**, and **devices**.

## Engineering checklist — this repo (`app/` Android Auto)

On the **`mockups`** branch, verify against `AndroidAutoMediaLibraryService`, `MediaItemMapping`, `MediaLibraryIds`, and manifest registration. See [android-auto-media3.md](android-auto-media3.md).

## Engineering checklist — Power-Ampache-2 (upstream)

- [ ] `MediaItem` hierarchy matches agreed depth goals.
- [ ] `MediaSession` callbacks cover **transport** controls.
- [ ] **Artwork** size and aspect follow platform guidance.
- [ ] **Errors** follow [Errors in car media apps](https://developer.android.com/training/cars/media/errors).
- [ ] DHU smoke test: **browse**, **play**, **voice** (where available).

## Open product questions (track with maintainer)

| Topic | Workspace note | Decision | Date |
|-------|----------------|----------|------|
| Offline badge on Auto | Prefer session error strings first | TBD | — |
| Multi-account in car | Often **last-used** only; full UX on phone | TBD | — |
| Lyrics on Auto | Usually **off while driving** unless parked-only is explicit | TBD | — |

---

*Last updated: 2026-04-07*
