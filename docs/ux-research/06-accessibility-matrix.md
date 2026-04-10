# Accessibility — phone vs Android Auto

**Principle:** In a **moving car**, **more text on screen** is not automatically **more accessible**. On the phone, we can follow WCAG-style habits; on Auto, the **host** owns most of the chrome — the app still owes **clear titles**, **stable IDs**, and **voice** where Google supports it.

## Matrix

| Topic | Phone (PA2 Compose) | Android Auto / projected | Notes |
|-------|---------------------|---------------------------|-------|
| **Screen reader** | TalkBack: labels, headings, order | Third-party apps have **limited** control of the head unit UI | Strong **session** metadata still helps |
| **Touch targets** | Aim for **48dp** on interactive controls | Host-rendered; you influence **how many** rows and **how simple** they are | Keep phone player controls large |
| **Contrast** | Material 3 + PA2 theme | **Day/night** and **OEM** themes dominate | Brand colors matter most on **phone** |
| **Font scaling** | System font scale | Host controlled | Test phone at **largest** scale |
| **Motion** | Respect **reduced motion** where it applies | Little app control | Prefer stable artwork |
| **Rotary** | N/A | Knob navigation on some cars | **Logical** browse order in the tree |
| **Voice** | Assistant on device | Often the **safest** way to search / play in motion | Wire `MediaSession` voice actions |
| **Lyrics / captions** | Fine on phone | **Driving:** minimize reading; audio-first | Lyrics policy: often **parked** or phone-only |

**Status column:** We have not replaced qualitative notes with pass/fail test results yet — do that when you run structured sessions.

## Phone — practical recommendations

- **Content descriptions** on icon-only controls (player, queue, download).
- **Focus order** matches visual order on settings and sign-in.
- **Do not** use color alone for state (e.g. offline = icon + text).
- **Tokens** in [../design-system/01-brand-and-language.md](../design-system/01-brand-and-language.md).
- **Figma / static previews:** large enough tap targets on interactive bits; **reduced motion** on web previews; label frames so **host** vs **PA2** is obvious ([../agents/prototype-and-engineering.md](../agents/prototype-and-engineering.md)).

## Auto — practical recommendations

- **Titles and subtitles** on `MediaItem` rows — short and clear.
- **Stable** `MediaItem` IDs for resume and voice.
- **Errors:** follow [Errors in car media apps](https://developer.android.com/training/cars/media/errors).

## Gaps (honest)

- Exact TalkBack behaviour for **notification / media controls** vs in-app player — document separately.
- OEM **high-contrast** modes vs album art — mostly host territory.

## Related

- [01-platform-constraint-sheet.md](01-platform-constraint-sheet.md)  
- [WCAG 2.2](https://www.w3.org/TR/WCAG22/) — strong baseline for **phone** web and analogous native patterns

---

*Last reviewed: 2026-04-07*
