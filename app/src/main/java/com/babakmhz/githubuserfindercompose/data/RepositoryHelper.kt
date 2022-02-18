package com.babakmhz.githubuserfindercompose.data

import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.model.UserDetails
import kotlinx.coroutines.flow.Flow

interface RepositoryHelper {
    suspend fun searchUsers(
        query: String, page: Int,
    ): Flow<List<User>>

    suspend fun getUserDetails(
        user:User
    ):User
}