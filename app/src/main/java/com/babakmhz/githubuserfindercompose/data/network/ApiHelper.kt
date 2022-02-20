package com.babakmhz.githubuserfindercompose.data.network

import com.babakmhz.githubuserfindercompose.data.network.response.userDetails.UserDetailsNetworkResponse
import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun searchUsers(
        name: String,
        page: Int,
        perPage: Int = Constants.PAGE_SIZE_CONFIG_FOR_API
    ): Flow<SearchResponse>

    suspend fun getUserDetails(
        username: String
    ): UserDetailsNetworkResponse
}