package com.example.vknewsclient.ui

import com.example.vknewsclient.domain.entity.FeedPost

sealed class NewsFeedScreenState {

    data object Initial: NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>): NewsFeedScreenState()
}