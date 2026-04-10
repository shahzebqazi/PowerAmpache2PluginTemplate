# Accessibility matrix — phone vs Android Auto (projected)

**Principle:** More **density** is not more accessible in a **moving vehicle**; pair WCAG-aligned **phone** patterns with **car** realism ([android-auto-ux-research-plan.md](../android-auto-ux-research-plan.md), goals and accessibility sections).

## Matrix — surface × topic

| Topic | Phone (PA2 Compose) | Android Auto / projected | Notes |
|-------|---------------------|----------------------------|--------|
| **Screen reader** | TalkBack: labels, headings, focus order | **Limited third-party control**; host owns much UI | Document queue/player **session** metadata for clarity where exposed |
| **Touch targets** | Follow Android **48dp** guidance for interactive elements | Host-rendered; app influences **item count** and **row simplicity** | Avoid tiny hit targets on **phone** player |
| **Color contrast** | WCAG-oriented choices via Material 3 + PA2 theme | **Day/night** and **OEM** themes dominate | PA2 brand colors apply mainly to **phone** |
| **Dynamic type / scaling** | System font scale on phone | Host controlled | Test phone at **largest** scale |
| **Motion** | Respect **reduced motion** where applicable | Limited app control | Prefer static artwork states |
| **Rotary input** | N/A | List navigation via OEM controller | Ensure **logical browse order** in media tree |
| **Voice** | Assistant integration on device | **Primary** safe path for search/play | MediaSession voice actions |
| **Captions / lyrics** | On-phone readability | **Driving:** minimize visual reading; audio-first | Lyrics **parked** or passenger-only policy |

**Status key (for future refinement):** Supported / Partial / N/A — currently qualitative; replace with test results.

---

## Phone — engineering recommendations

- **Content descriptions** on icon-only controls (player, queue, download).
- **Focus order** matches visual order on settings and auth.
- **Color:** Do not rely on **color alone** for state (e.g. offline = icon + text).
- **Theme:** Document tokens in [../design-system/01-brand-and-language.md](../design-system/01-brand-and-language.md).
- **Design prototypes (Figma / static frames):** Use **≥48 CSS px** or **dp-equivalent** touch targets on interactive controls where feasible; gate **decorative transitions** on **`prefers-reduced-motion`** in web previews. Label frames **host-only** vs **PA2-accented** so reviewers do not confuse preview chrome with OEM styling ([08-prototype-handoff-package.md](08-prototype-handoff-package.md)).

---

## Auto — engineering recommendations

- Expose **clear** `MediaItem` titles and subtitles (artist/album) within length limits.
- Use **stable** `MediaItem` IDs for resume and voice.
- **Errors:** Surface via supported media error patterns ([Handle errors](https://developer.android.com/training/cars/media/errors)).

---

## Gaps

- **Gap:** Exact TalkBack behaviour for **notification / media controls** vs in-app player (document separately).
- **Gap:** OEM-specific **high-contrast** modes interaction with album art (hypothesis: host handles).

---

## Related

- [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md)
- [WCAG 2.2](https://www.w3.org/TR/WCAG22/) (phone-oriented baseline)
