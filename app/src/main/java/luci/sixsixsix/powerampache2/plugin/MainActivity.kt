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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import luci.sixsixsix.powerampache2.plugin.presentation.delegates.BackPressHandler
import luci.sixsixsix.powerampache2.plugin.presentation.delegates.BackPressHandlerImpl
import luci.sixsixsix.powerampache2.ui.theme.PowerAmpache2Theme

object DriveSafeOverlayCopy {
    const val title = "Drive safe"
    const val subtitle = "Enjoy your music"
    const val body = "Please keep your attention on the road while Power Ampache 2 handles the soundtrack."
    const val primaryAction = "Open Power Ampache 2"
}

@AndroidEntryPoint
class MainActivity : FragmentActivity(), BackPressHandler by BackPressHandlerImpl() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleOnBackPressed(this) // prevent the activity from being destroyed on back-press

        setContent {
            PowerAmpache2Theme(
                darkTheme = true,
                dynamicColor = false
            ) {
                DriveSafeOverlayScreen(
                    onOpenPowerAmpache = ::launchPowerAmpache2
                )
            }
        }
    }

    /**
     * Launch another app by package name
     */
    private fun launchPowerAmpache2() {
        // TODO: add all possible package names
        openPowerAmpache2()
        finish()
    }
}

@Composable
private fun DriveSafeOverlayScreen(
    onOpenPowerAmpache: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = DriveSafeOverlayCopy.title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = DriveSafeOverlayCopy.subtitle,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = DriveSafeOverlayCopy.body,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                Button(onClick = onOpenPowerAmpache) {
                    Text(DriveSafeOverlayCopy.primaryAction)
                }
            }
        }
    }
}
