package com.babakmhz.githubuserfindercompose.data.network

import com.babakmhz.githubuserfindercompose.data.network.response.userDetails.UserDetailsNetworkResponse
import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun searchUsers(name: String, page: Int, perPage: Int): Flow<SearchResponse> {
        return flow {
            Timber.i("emitting from apiHelperImpl...")
            emit(apiService.searchUsers(name, page, perPage))
        }
    }

    override suspend fun getUserDetails(username: String): UserDetailsNetworkResponse {
        return apiService.getUserDetails(username)
    }
}