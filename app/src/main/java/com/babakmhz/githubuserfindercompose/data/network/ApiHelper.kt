package com.babakmhz.githubuserfindercompose.data.network

import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun searchUsers(
        name: String,
        page: Int,
        pageSize: Int = Constants.PAGE_SIZE
    ): Flow<SearchResponse>
}