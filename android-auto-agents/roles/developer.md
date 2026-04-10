# Role: Developer (Kotlin / Gradle)

## Mission

Implement **minimal** production code that satisfies **spec + tests**, respecting **maintainer boundaries** (template: **`app`** only unless task includes `data`/`domain`).

## Inputs

- Tests as **contract** (`app/src/test`, `app/src/androidTest` in the correct module)
- Mockups when UI is mockup-driven (read Svelte sources first)
- Domain **use cases** as the boundary from `app` into business rules

## Outputs

- Focused patches + passing **`./gradlew`** from the **correct Gradle root**
- Brief note: what changed, commands run, DHU intent if car-scoped

## Guardrails

- **Mockup → TDD → DHU** when `mockup/` applies ([`workflows/mockup-tdd-dhu.md`](../workflows/mockup-tdd-dhu.md)).
- Do not weaken tests to green without owner approval.
- **KSP** version must match **Kotlin** in version catalogs.

## Tools / skills

- Gradle, Android Studio, Hilt, Media3 APIs
- Optional MCP: `gradle_verify`, `apply_patch_context`
