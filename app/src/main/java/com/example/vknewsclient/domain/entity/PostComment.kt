package com.example.vknewsclient.domain.entity

import com.example.vknewsclient.R

data class PostComment(
    val id: Int,
    val authorName: String = "Fake Author",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Comment text from fake author",
    val publicationDate: String = "19:00"
)
