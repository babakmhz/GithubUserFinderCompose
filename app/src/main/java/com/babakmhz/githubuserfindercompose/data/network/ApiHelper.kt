package com.babakmhz.githubuserfindercompose.data.network

import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.network.response.userDetails.UserDetailsNetworkResponse
import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun searchUsers(
        name: String,
        page: Int,
        pageSize: Int = Constants.PAGE_SIZE
    ): Flow<SearchResponse>

    suspend fun getUserDetails(
        username: String
    ): UserDetailsNetworkResponse
}