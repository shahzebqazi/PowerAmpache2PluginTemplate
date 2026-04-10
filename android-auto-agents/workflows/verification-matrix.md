# Verification matrix (quick reference)

Set **`PA2_PLUGIN_GRADLE_ROOT`** for rows that use `gradle-plugin-template.sh`.

| Change location | Assemble | Unit tests | Instrumented | DHU |
|-----------------|----------|------------|--------------|-----|
| Nested PA2 plugin `app/` | `./android-auto-agents/scripts/gradle-plugin-template.sh :app:assembleDebug` | same script `:app:testDebugUnitTest` | when UI contract requires | **Yes** when that APK registers **Media3** / car media |
| `Power-Ampache-2/` (when cloned) | that repo’s Gradle | that repo’s tests | when required | **Yes** for full-app car stack |

**Rule:** Paste **actual command output** when claiming green.
