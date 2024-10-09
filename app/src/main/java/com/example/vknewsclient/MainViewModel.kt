package com.example.vknewsclient

import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.entity.PostComment
import com.example.vknewsclient.domain.entity.StatisticItem
import com.example.vknewsclient.ui.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }

    private val comments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(PostComment(id = it))
        }
    }

    private val initialState = HomeScreenState.Posts(sourceList)

    private val _screenState = MutableStateFlow<HomeScreenState>(initialState)
    val screenState: StateFlow<HomeScreenState> = _screenState.asStateFlow()

    private var savedState: HomeScreenState = initialState

    fun updateStatistics(feedPost: FeedPost, item: StatisticItem) {
        val state = screenState.value

        if (state !is HomeScreenState.Posts) return

        val oldPosts = state.posts.toMutableList()
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id)
                    newFeedPost
                else it
            }
        }
        _screenState.value = HomeScreenState.Posts(posts = newPosts)
    }

    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost = feedPost, comments = comments)
    }

    fun closeComments() {
        _screenState.value = savedState
    }

    fun remove(feedPost: FeedPost) {
        val state = screenState.value

        if (state !is HomeScreenState.Posts) return

        val oldPosts = state.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(posts = oldPosts)
    }
}