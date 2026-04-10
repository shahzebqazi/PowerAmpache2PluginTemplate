# Plugin template — Gradle and code map (fresh `main`)

Refresh this file after major **`git pull`** / branch switches. Describes the **same repo** on **`main`** (Gradle at **repository root**), not **`PowerAmpache2PluginTemplateOld/`** if you keep a local copy.

## Git (fork)

| Remote | Typical URL |
|--------|-------------|
| **`origin`** | Your fork — **push** feature branches and **`main`** here. |
| **`upstream`** | **icefields/PowerAmpache2PluginTemplate** (**fetch** maintainer **`main`**). |

**Submodule:** after clone, **`git submodule update --init`** for **PowerAmpache2Theme**.

## Gradle

- **Direct:** from the **repo root on `main`** — **`./gradlew :app:assembleDebug`**, **`:app:testDebugUnitTest`**, **`:app:installDebug`**.
- **Via harness from `mockups` checkout:** **`export PA2_PLUGIN_GRADLE_ROOT=/absolute/path/to/a/main-branch-clone`** then **`./android-auto-agents/scripts/gradle-plugin-template.sh`** / **`adb-plugin-install-debug.sh`** (see **`android-auto-agents/README.md`**).
- **Theme submodule:** **`PowerAmpache2Theme/`** — init with **`git submodule update --init`** when cloning.

## Maintainer scope

- **Default:** edit **`app/`** only.
- **`data`/`domain`:** only when the **task or maintainer** explicitly includes them.

## Credentials (when added by upstream)

Debug **`local.properties`** keys such as **`pa2.test.url`**, **`pa2.test.user`**, **`pa2.test.password`** may be merged from **repo root** then **template** (template keys often win). Rebuild after changes for **`BuildConfig`**.

## Domain boundary

- **Use cases** live under **`domain/src/main/java/.../usecase/`** — prefer **`operator fun invoke`** style when extending **with explicit scope**.
- **Mock data** for early UI: **`domain/.../model/mocks/`** (**`AlbumsMock`**, **`SongsMock`**, etc.) — align presentation work with these until real **`MusicFetcher`** / API wiring is specified.

## Typical modules (upstream starter)

| Module | Notes |
|--------|--------|
| **`app`** | **`MainActivity`**, **`SongListScreen`**, **`SongListViewModel`**, theme from **PowerAmpache2Theme**. |
| **`data`** | **`MusicFetcherImpl`**, Hilt **`InjectionModule`** / **`AbstractModule`**, **`PA2DataFetchService`**. |
| **`domain`** | **`MusicFetcher`** interface, models, use cases, mocks. |

## Android Auto

Car-specific services (e.g. **`AndroidAutoMediaLibraryService`**) appear **only** when implemented on your branch — verify **`AndroidManifest.xml`** after each upstream update.
