package luci.sixsixsix.powerampache2.plugin.auto

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import com.google.common.collect.ImmutableList
import luci.sixsixsix.powerampache2.plugin.R

/**
 * Playback shuffle for Android Auto / Media3 session controllers.
 *
 * Media3 **1.5.1** exposes shuffle as a standard [Player] command
 * ([Player.COMMAND_SET_SHUFFLE_MODE]), not a [androidx.media3.session.SessionCommand].
 * Android Auto shows it when the session publishes a [CommandButton] in
 * [MediaSession.setCustomLayout] / [MediaLibrarySession.Builder.setCustomLayout]
 * (see [Android Auto custom playback actions](https://developer.android.com/training/cars/media/enable-playback)).
 */
object Pa2ShuffleCommands {

    /** Minimum timeline size before shuffle changes skip order. */
    const val MIN_QUEUE_SIZE_FOR_SHUFFLE = 2

    /** Ensures shuffle is granted to automotive controllers (avoids addAllCommands on JVM tests). */
    fun playerCommandsWithShuffle(base: Player.Commands): Player.Commands =
        base.buildUpon().add(Player.COMMAND_SET_SHUFFLE_MODE).build()

    @OptIn(UnstableApi::class)
    fun buildShuffleCommandButton(context: Context, shuffleEnabled: Boolean, queuePlayable: Boolean): CommandButton {
        val icon =
            if (shuffleEnabled) CommandButton.ICON_SHUFFLE_ON
            else CommandButton.ICON_SHUFFLE_OFF
        return CommandButton.Builder(icon)
            .setPlayerCommand(Player.COMMAND_SET_SHUFFLE_MODE)
            .setDisplayName(context.getString(R.string.media_action_shuffle))
            .setEnabled(queuePlayable)
            .build()
    }

    @OptIn(UnstableApi::class)
    fun buildShuffleCustomLayout(
        context: Context,
        shuffleEnabled: Boolean,
        queuePlayable: Boolean,
    ): ImmutableList<CommandButton> =
        ImmutableList.of(buildShuffleCommandButton(context, shuffleEnabled, queuePlayable))

    fun canShuffle(player: Player): Boolean = player.mediaItemCount >= MIN_QUEUE_SIZE_FOR_SHUFFLE

    fun setShuffleEnabled(player: Player, enabled: Boolean) {
        if (!canShuffle(player)) {
            if (player.shuffleModeEnabled) {
                player.shuffleModeEnabled = false
            }
            return
        }
        player.shuffleModeEnabled = enabled
    }

    @OptIn(UnstableApi::class)
    fun refreshSessionShuffleLayout(session: MediaSession?, context: Context, player: Player?) {
        val p = player ?: return
        session?.setCustomLayout(
            buildShuffleCustomLayout(context, p.shuffleModeEnabled, canShuffle(p))
        )
    }
}
