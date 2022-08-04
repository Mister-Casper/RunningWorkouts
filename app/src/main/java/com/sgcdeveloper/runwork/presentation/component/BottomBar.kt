package com.sgcdeveloper.runwork.presentation.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.screen.destinations.ActivityScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.DirectionDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.ProfileScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.ProgressScreenDestination

@Composable
fun BottomBar(
    currentDestination: String?,
    onDestinationChange: (direction: DirectionDestination) -> Unit
) {
    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.onSurface,
                selected = currentDestination == destination.direction.route,
                onClick = { onDestinationChange(destination.direction) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        stringResource(destination.label),
                        modifier = Modifier.size(32.dp)
                    )
                },
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}

enum class BottomBarDestination(
    val direction: DirectionDestination,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Progress(ProgressScreenDestination, R.drawable.icon_progress, R.string.main__bottom_navigation_progress),
    Activity(ActivityScreenDestination, R.drawable.icon_activity, R.string.main__bottom_navigation_activity),
    Profile(ProfileScreenDestination, R.drawable.icon_profile, R.string.main__bottom_navigation_profile)
}