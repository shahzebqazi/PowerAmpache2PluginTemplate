package luci.sixsixsix.powerampache2.plugin.auto

import org.junit.Assert.assertEquals
import org.junit.Test

/** JVM smoke tests for shuffle wiring (Player.Commands verified on device/DHU). */
class Pa2ShuffleCommandsTest {

    @Test
    fun minQueueSizeForShuffle_isTwo() {
        assertEquals(2, Pa2ShuffleCommands.MIN_QUEUE_SIZE_FOR_SHUFFLE)
    }
}
