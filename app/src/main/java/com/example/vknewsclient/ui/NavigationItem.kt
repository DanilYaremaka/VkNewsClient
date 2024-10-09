package com.example.vknewsclient.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsclient.R
import com.example.vknewsclient.navigation.Screen


sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) {

    data object Home: NavigationItem(
        screen = Screen.NewsFeed,
        titleResId = R.string.nav_item_main,
        icon = Icons.Outlined.Home
    )

    data object Favorite: NavigationItem(
        screen = Screen.Favourite,
        titleResId = R.string.nav_item_favorite,
        icon = Icons.Outlined.Favorite
    )

    data object Profile: NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.nav_item_profile,
        icon = Icons.Outlined.AccountCircle
    )
}