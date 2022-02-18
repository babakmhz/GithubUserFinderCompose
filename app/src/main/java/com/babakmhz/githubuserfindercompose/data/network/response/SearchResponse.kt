package com.babakmhz.githubuserfindercompose.data.network.response

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)