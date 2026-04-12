/**
 * Copyright (C) 2025  Antonio Tari
 *
 * This file is a part of Power Ampache 2
 * Ampache Android client application
 * @author Antonio Tari
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package luci.sixsixsix.powerampache2.plugin

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import luci.sixsixsix.powerampache2.plugin.presentation.SongListScreen
import luci.sixsixsix.powerampache2.ui.theme.PowerAmpache2Theme

/**
 * Launches the Power Ampache 2 host and finishes. Avoid registering OnBackPressedDispatcher
 * callbacks after finish() in the same onCreate — that has caused crashes on some devices
 * (launcher tap / phone UI overlay).
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openPowerAmpache2()

        // uncomment for testing, viewModel contains a few examples to get data
        // testContent()
    }

    private fun testContent() {
        setContent {
            PowerAmpache2Theme(
                darkTheme = true,
                dynamicColor = false
            ) {
                SongListScreen()
            }
        }
    }

    /**
     * Launch the Power Ampache host app (first installed variant wins).
     */
    private fun openPowerAmpache2() {
        val hostPackages = listOf(
            "luci.sixsixsix.powerampache2.fdroid.debug",
            "luci.sixsixsix.powerampache2.fdroid",
            "luci.sixsixsix.powerampache2.debug",
            "luci.sixsixsix.powerampache2",
        )
        for (pkg in hostPackages) {
            val launch = packageManager.getLaunchIntentForPackage(pkg)?.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            if (launch != null) {
                startActivity(launch)
                break
            }
        }
        finish()
    }
}
