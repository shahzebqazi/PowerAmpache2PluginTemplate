package luci.sixsixsix.powerampache2.plugin

import org.junit.Assert.assertEquals
import org.junit.Test

class MainActivitySafetyOverlayTest {
    @Test
    fun driveSafeOverlayCopyWarnsAndEncouragesMusic() {
        assertEquals("Drive safe", DriveSafeOverlayCopy.title)
        assertEquals("Enjoy your music", DriveSafeOverlayCopy.subtitle)
        assertEquals(
            "Please keep your attention on the road while Power Ampache 2 handles the soundtrack.",
            DriveSafeOverlayCopy.body
        )
    }
}
