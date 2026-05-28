package luci.sixsixsix.powerampache2.plugin.auto

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import com.google.common.collect.ImmutableList
import luci.sixsixsix.powerampache2.plugin.R

object Pa2ShuffleCommands {

    const val MIN_QUEUE_SIZE_FOR_SHUFFLE = 2

    fun playerCommandsWithShuffle(base: Player.Commands): Player.Commands =
        base.buildUpon().add(Player.COMMAND_SET_SHUFFLE_MODE).build()

    fun buildShuffleMediaButtonPreferences(
        context: Context,
        shuffleEnabled: Boolean,
        queuePlayable: Boolean,
    ): ImmutableList<CommandButton> {
        if (!queuePlayable) {
            return ImmutableList.of()
        }
        val icon = if (shuffleEnabled) CommandButton.ICON_SHUFFLE_ON else CommandButton.ICON_SHUFFLE_OFF
        return ImmutableList.of(
            CommandButton.Builder(icon)
                .setPlayerCommand(Player.COMMAND_SET_SHUFFLE_MODE)
                .setDisplayName(context.getString(R.string.media_action_shuffle))
                .setSlots(CommandButton.SLOT_OVERFLOW)
                .build()
        )
    }

    fun canShuffle(player: Player): Boolean = player.mediaItemCount >= MIN_QUEUE_SIZE_FOR_SHUFFLE

    fun refreshSessionShuffleButtons(session: MediaSession?, context: Context, player: Player?) {
        val p = player ?: return
        session?.setMediaButtonPreferences(
            buildShuffleMediaButtonPreferences(context, p.shuffleModeEnabled, canShuffle(p))
        )
    }
}
