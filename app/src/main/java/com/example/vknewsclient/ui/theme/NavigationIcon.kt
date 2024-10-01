package com.example.vknewsclient.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsclient.R

sealed class NavigationIcon(
    val titleResId: Int,
    val icon: ImageVector
) {

    data object Home: NavigationIcon(
        titleResId = R.string.nav_item_main,
        icon = Icons.Outlined.Home
    )

    data object Favorite: NavigationIcon(
        titleResId = R.string.nav_item_favorite,
        icon = Icons.Outlined.Favorite
    )

    data object Profile: NavigationIcon(
        titleResId = R.string.nav_item_profile,
        icon = Icons.Outlined.AccountCircle
    )
}