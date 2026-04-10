# Appendix — Power Ampache 2 theme inventory

**Purpose:** Single inventory of where visual tokens live for **phone UI**. **Android Auto** projected media UI is **host-rendered**; it does **not** use PA2 Compose/XML theme tokens (see [00-design-system-index.md](00-design-system-index.md)).

**Sources (local clone paths):**

| Location | Role |
|----------|------|
| `Power-Ampache-2/PowerAmpache2Theme/` | **Canonical theme module** (Git submodule in upstream). Compose `PowerAmpache2Theme`, `Color.kt`, `Type.kt`, `AdditionalColours.kt`, shared `colors.xml`. |
| `Power-Ampache-2/app/src/main/res/values/colors.xml` | App-specific colours (login, placeholders); references `@color/*` from theme module. |
| `Power-Ampache-2/app/src/main/res/values/themes.xml` | `Theme.PowerAmpache2` → `android:Theme.Material.Light.NoActionBar` (shell for Compose). |
| `Power-Ampache-2/app/src/main/res/values-v35/themes.xml` | API 35: `windowOptOutEdgeToEdgeEnforcement`. |
| `Power-Ampache-2/app/src/main/res/values/dimen.xml` | Spacing, type sizes for lists, player, home, settings (phone layouts). |
| `Power-Ampache-2/app/src/main/res/values-night/colors.xml` | Empty in main app; night overrides live in **PowerAmpache2Theme** `values-night/colors.xml`. |
| Flavour `res/values/colors.xml` (PlayStore, FDroid, Github, debug, release) | Build-variant tweaks (e.g. launcher / debug). |

**Semantic mapping — Material 3 color roles (PowerAmpache2Theme)**

| Token / role | Typical hex (from `PowerAmpache2Theme/.../values/colors.xml`) | Used for (semantic) |
|--------------|----------------------------------------------------------------|---------------------|
| `primaryDark` | `#FF70CCCC` | Dark scheme primary (teal/cyan accent). |
| `onPrimaryDark` | `#FF122E2E` | Text/icons on primary (dark). |
| `surfaceDark` | `#FF1B1F1D` | Main dark surface. |
| `surfaceContainerDark` | `#FF242927` | Elevated/grouped surfaces. |
| `surfaceContainerHighDark` | `#FF282E2C` | Higher elevation; queue handle (additional). |
| `onSurfaceDark` / `onBackgroundDark` | `#FFDFE5E3` | Primary text on dark surfaces. |
| `onSurfaceVariantDark` | `#FFCBCED1` | Secondary text. |
| `surfaceVariantDark` | `#FF4E5359` | Variant surfaces. |
| `secondaryContainerDark` / `onSecondaryContainerDark` | `#FF41444C` / `#FFCFD8E5` | Secondary containers. |
| `tertiaryDark` / `onTertiaryDark` | Warm accent pair | Tertiary actions (e.g. demo/login accents). |
| `errorDark` | `#FFF593AB` | Error state. |
| `outlineDark` | `#FFA5A9AD` | Borders/dividers. |
| Light scheme | `surfaceLight`, `backgroundLight`, `onBackgroundLight`, `tertiaryLight`, container colours | Light + Material You dynamic path. |

**Hard-coded in `Theme.kt` (not in XML):** Several `Color(0xFF...)` literals complete the M3 `darkColorScheme` / `lightColorScheme` (e.g. `background` dark `#FF282E2C`, `secondary`, `inverseSurface`, `scrim`). Document these in [01-brand-and-language.md](01-brand-and-language.md) token table as “Compose-only”.

**AdditionalColours extension:** `AdditionalColours.kt` adds `surfaceDim`, `surfaceBright`, `surfaceContainerLowest` … `surfaceContainerHighest`, `shadow`, `queueHandle` — used for queue UI and layered surfaces beyond stock M3.

**Typography:** `Type.kt` — **Nunito** family (`nunito_regular`, `nunito_bold`, `nunito_semibold`, `nunito_light`, `nunito_black`, `nunito_medium`, `nunito_condensed_italic`) mapped to all Material 3 `Typography` roles. Comment in file labels object `AppFont` but uses Nunito assets.

**Dynamic color:** When user selects Material You themes and API ≥ 31, `PowerAmpache2Theme` uses `dynamicDarkColorScheme` / `dynamicLightColorScheme` with the same `AdditionalDarkColours` / `AdditionalLightColours` attached.

**Login XML bridge:** `app/.../values/colors.xml` maps login screen slots to `surfaceDark`, `primaryDark`, `outlineDark`, etc., keeping XML views aligned with Compose theme.

**Maintenance:** On upstream theme changes, diff `PowerAmpache2Theme/src/main/java/.../theme/*.kt` and `.../res/values/colors.xml` first, then `app/.../dimen.xml` for layout rhythm.
