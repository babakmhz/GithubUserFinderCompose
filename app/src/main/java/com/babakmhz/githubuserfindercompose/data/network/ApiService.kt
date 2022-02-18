package com.babakmhz.githubuserfindercompose.data.network

import com.babakmhz.githubuserfindercompose.data.network.ApiEndpoints.SEARCH
import com.babakmhz.githubuserfindercompose.data.network.ApiEndpoints.USER_DETAIL
import com.babakmhz.githubuserfindercompose.data.network.response.userDetails.UserDetailsNetworkResponse
import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET(SEARCH)
    suspend fun searchUsers(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): SearchResponse

    @GET(USER_DETAIL)
    suspend fun getUserDetails(@Path(Constants.DETAILS_USERNAME_PATH_VALUE) username:String): UserDetailsNetworkResponse
}