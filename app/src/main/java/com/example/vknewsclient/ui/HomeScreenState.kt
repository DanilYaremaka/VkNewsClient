package com.example.vknewsclient.ui

import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.domain.entity.PostComment

sealed class HomeScreenState {

    data object Initial: HomeScreenState()

    data class Posts(val posts: List<FeedPost>): HomeScreenState()

    data class Comments(val feedPost: FeedPost, val comments: List<PostComment>): HomeScreenState()
}