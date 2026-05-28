# plugin/auto-shuffle — playback shuffle (Android Auto)

## Media3 1.5.1 finding (item 3)

There is **no** shuffle `SessionCommand`. Shuffle is a **standard Player command**:

- `Player.COMMAND_SET_SHUFFLE_MODE`
- `Player.setShuffleModeEnabled(boolean)` / `shuffleModeEnabled`

Android Auto shows shuffle when the session publishes a **`CommandButton`** with `setPlayerCommand(COMMAND_SET_SHUFFLE_MODE)` via **`MediaLibrarySession.Builder.setCustomLayout`** (custom playback action slot per [Enable playback control](https://developer.android.com/training/cars/media/enable-playback)).

Implementation: `Pa2ShuffleCommands.kt`, wired in `Pa2MediaLibraryService`.

## Scope (v1)

- **ExoPlayer only** — no PA2 host IPC for shuffle.
- **Song queue** — shuffle affects playback order when the mirrored queue has ≥ 2 tracks.
- **Removed** favourite-album browse `.shuffled()` (that randomized album *listing*, not song shuffle).

## DHU verification

1. Install debug APK; open PA2 plugin path so queue populates.
2. Play a playlist/album with 3+ songs from Android Auto.
3. Open Now Playing → overflow / transport → **Shuffle** (icon toggles on/off).
4. Skip next several times — order should differ when shuffle is on.
5. Optional: `adb shell dumpsys media_session | grep -i shuffle`

## PR

Target upstream `icefields/PowerAmpache2PluginTemplate` branch `plugin/auto` after DHU sign-off.
