# Annotated bibliography — Android Auto UX research

Access dates reflect when sources were captured for this workspace (2026-03-30). Re-verify URLs before release milestones.

## Platform and quality (normative for implementation)

| Title | Publisher | Last accessed | URL | Notes |
|-------|-----------|---------------|-----|--------|
| *Create audio media apps* (Cars — Media) | Google Android Developers | 2026-03-30 | https://developer.android.com/training/cars/media | Two paths: `MediaBrowserService` + `MediaSession` vs Cars App Library templated media; distraction caution. |
| *Configure the manifest file* | Google Android Developers | 2026-03-30 | https://developer.android.com/training/cars/media/configure-manifest | `MediaLibraryService` / browser service wiring for Auto/AAOS. |
| *Implement distraction safeguards* | Google Android Developers | 2026-03-30 | https://developer.android.com/training/cars/media/distraction-safeguards | Host-enforced limits for media apps. |
| *Android app quality for cars* | Google Android Developers | 2026-03-30 | https://developer.android.com/docs/quality-guidelines/car-app-quality | Play listing requirements; media category. |
| *Test Android apps for cars* | Google Android Developers | 2026-03-30 | https://developer.android.com/training/cars/testing | DHU, validation. |
| *Design for cars* (Create media apps) | Google for Developers (Cars design) | 2026-03-30 | https://developers.google.com/cars/design/create-apps/app-types/media | HMI-level guidance for media app type. |
| *Build templated media apps* | Google Android Developers | 2026-03-30 | https://developer.android.com/training/cars/apps/media | Car App Library media templates (Android Auto only). |
| *Android for Cars — Apps* | Google Android Developers | 2026-03-30 | https://developer.android.com/training/cars/apps | Overview of templated apps vs media browser path. |

## Distraction and safety (research + guidelines)

| Title | Publisher | Last accessed | URL | Notes |
|-------|-----------|---------------|-----|--------|
| NHTSA distracted driving (research hub) | NHTSA (US DOT) | 2026-03-30 | https://www.nhtsa.gov/research-data/distracted-driving | **Portal**, not a single PDF. Locate the current **visual-manual** guideline document and revision from linked publications before citing metrics in briefs. |
| EU Road Safety (Mobility & Transport) | European Commission | 2026-03-30 | https://road-safety.transport.ec.europa.eu/index_en | Policy context; use for **background** only—not a substitute for Google Play car requirements. |
| Brooker et al., systematic reviews on IVIS / driver distraction | Academic (various) | — | (add DOIs per [04-distraction-brief.md](04-distraction-brief.md)) | Use meta-analyses for cognitive/visual demand; prefer post-2010 work where possible. |

## Accessibility

| Title | Publisher | Last accessed | URL | Notes |
|-------|-----------|---------------|-----|--------|
| *Web Content Accessibility Guidelines (WCAG) 2.2* | W3C WAI | 2026-03-30 | https://www.w3.org/TR/WCAG22/ | Applies to **phone** web/companion patterns; map principles to native where analogous. |
| *Accessibility on Android* | Google Android Developers | 2026-03-30 | https://developer.android.com/guide/topics/ui/accessibility | TalkBack, labels, focus (phone app). |

## Product / upstream

| Title | Publisher | Last accessed | URL | Notes |
|-------|-----------|---------------|-----|--------|
| Power Ampache 2 (source) | icefields / community | 2026-03-30 | https://github.com/icefields/Power-Ampache-2 | `SimpleMediaService` (`MediaSessionService`), Media3 `MediaSession`, manifest media-browser intent filters. |
| PowerAmpache2Theme (submodule) | icefields | 2026-03-30 | https://github.com/icefields/PowerAmpache2Theme | Compose theme module. |

## Design systems methodology

| Title | Publisher | Last accessed | URL | Notes |
|-------|-----------|---------------|-----|--------|
| *What are design systems?* (checklist: branding, layout, components) | 99designs Blog | 2026-03-30 | https://99designs.com/blog/tips/design-systems/ | Structure for [design system docs](../design-system/00-design-system-index.md). |
