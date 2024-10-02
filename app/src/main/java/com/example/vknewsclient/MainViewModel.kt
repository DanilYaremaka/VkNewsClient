package com.example.vknewsclient

import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.entity.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {

    private val _feedPost = MutableStateFlow(FeedPost())
    val feedPost: StateFlow<FeedPost> = _feedPost

    fun updateStatistics(item: StatisticItem) {
        val oldStatistics = feedPost.value.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        _feedPost.value = feedPost.value.copy(statistics = newStatistics)
    }
}