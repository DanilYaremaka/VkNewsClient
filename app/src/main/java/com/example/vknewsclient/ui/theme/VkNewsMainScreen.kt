package com.example.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val selectedItemPosition = remember { mutableIntStateOf(0) }
                val items = listOf(
                    NavigationIcon.Home,
                    NavigationIcon.Favorite,
                    NavigationIcon.Profile
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.intValue == index,
                        onClick = {
                            selectedItemPosition.intValue = index
                        },
                        icon = { Icon(imageVector = item.icon, contentDescription = null) },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                    )
                }
            }
        },
    ) {

        val feedPost = viewModel.feedPost.collectAsState()

        PostCard(
            modifier = Modifier.padding(8.dp),
            feedPost = feedPost.value,
            onViewsClickListener = viewModel::updateStatistics,
            onShareClickListener = viewModel::updateStatistics,
            onCommentClickListener = viewModel::updateStatistics,
            onLikeClickListener = viewModel::updateStatistics,
        )
    }
}