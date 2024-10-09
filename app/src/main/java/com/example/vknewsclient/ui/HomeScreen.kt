package com.example.vknewsclient.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.MainViewModel
import com.example.vknewsclient.domain.entity.FeedPost

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {

    val screenState = viewModel.screenState.collectAsState(HomeScreenState.Initial)

    when (val state = screenState.value) {
        is HomeScreenState.Comments -> {
            CommentsScreen(
                feedPost = state.feedPost,
                comments = state.comments,
                onBackPressed = {
                    viewModel.closeComments()
                }
            )
            BackHandler {
                viewModel.closeComments()
            }
        }

        is HomeScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = state.posts
            )
        }

        HomeScreenState.Initial -> {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedPosts(
    viewModel: MainViewModel,
    posts: List<FeedPost>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { it.hashCode() }
        ) { feedPost ->
            val dismissBoxState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when (it) {
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
                positionalThreshold = { it * .70f }
            )
            SwipeToDismissBox(
                modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
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
                    onCommentClickListener = {
                        viewModel.showComments(feedPost)
                    },
                    onLikeClickListener = { item ->
                        viewModel.updateStatistics(feedPost, item)
                    },
                )
            }

        }
    }
}