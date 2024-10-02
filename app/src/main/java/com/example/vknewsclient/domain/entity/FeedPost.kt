package com.example.vknewsclient.domain.entity

import com.example.vknewsclient.R

data class FeedPost(
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 909),
        StatisticItem(type = StatisticType.SHARES, count = 15),
        StatisticItem(type = StatisticType.COMMENTS, count = 56),
        StatisticItem(type = StatisticType.LIKES, count = 105),
    )
)
