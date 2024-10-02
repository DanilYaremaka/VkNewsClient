@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
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
        val feedPosts = viewModel.feedPosts.collectAsState(listOf())

        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = feedPosts.value,
                key = { it.hashCode() }
            ) { feedPost ->
                val dismissBoxState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        when(it) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                return@rememberSwipeToDismissBoxState false
                            }
                            SwipeToDismissBoxValue.EndToStart -> {
                                viewModel.remove(feedPost)
                            }
                            SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                        }
                        return@rememberSwipeToDismissBoxState true
                    },
                    // positional threshold of 25%
                    positionalThreshold = { it * .70f }
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissBoxState,
                    backgroundContent = {},
                    enableDismissFromStartToEnd = false
                ) {
                    PostCard(
                        feedPost = feedPost,
                        onViewsClickListener = { item ->
                            viewModel.updateStatistics(feedPost, item)
                        },
                        onShareClickListener = { item ->
                            viewModel.updateStatistics(feedPost, item)
                        },
                        onCommentClickListener = { item ->
                            viewModel.updateStatistics(feedPost, item)
                        },
                        onLikeClickListener = { item ->
                            viewModel.updateStatistics(feedPost, item)
                        },
                    )
                }

            }
        }


    }
}