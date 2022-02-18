package com.babakmhz.githubuserfindercompose.data.network

import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun searchUsers(name: String, page: Int, pageSize: Int): Flow<SearchResponse> {
        return flow {
            emit(apiService.searchUsers(name, page, page))
        }
    }
}