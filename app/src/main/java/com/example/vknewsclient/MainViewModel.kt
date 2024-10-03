package com.example.vknewsclient

import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.entity.StatisticItem
import com.example.vknewsclient.ui.theme.NavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }

    private val _feedPosts = MutableStateFlow<List<FeedPost>>(sourceList)
    val feedPosts: StateFlow<List<FeedPost>> = _feedPosts.asStateFlow()

    private val _selectedNavItem = MutableStateFlow<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: StateFlow<NavigationItem> = _selectedNavItem

    fun selectNavItem(item: NavigationItem) {
        _selectedNavItem.value = item
    }

    fun updateStatistics(feedPost: FeedPost, item: StatisticItem) {
        val oldPosts = _feedPosts.value.toMutableList()
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
        _feedPosts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id)
                    newFeedPost
                else it
            }
        }
    }

    fun remove(feedPost: FeedPost) {
        val oldPosts = _feedPosts.value.toMutableList()
        oldPosts.remove(feedPost)
        _feedPosts.value = oldPosts
    }
}