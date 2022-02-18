package com.babakmhz.githubuserfindercompose.data.network

import com.babakmhz.githubuserfindercompose.data.network.ApiEndpoints.SEARCH
import com.babakmhz.githubuserfindercompose.data.network.Constants.PAGE_SIZE
import com.babakmhz.githubuserfindercompose.data.network.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(SEARCH)
    suspend fun searchUsers(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int = PAGE_SIZE
    ):SearchResponse
}