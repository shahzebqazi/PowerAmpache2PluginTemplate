# Plugin Template for Power Ampache 2 (beta).

**Upstream:** Please create a branch named **`PluginAndroidAuto`** on this repository for Android Auto–related plugin work and PRs.

**Android Auto implementation (summary):** The plugin targets **Media3** (`MediaLibraryService` / session) with a browse tree wired through **`MusicFetcher`** only. The **Power Ampache 2 host** must bind **`PA2DataFetchService`** and supply library data over the existing Messenger IPC; **library search** in the car UI is not implemented yet (browse and playback path first).
