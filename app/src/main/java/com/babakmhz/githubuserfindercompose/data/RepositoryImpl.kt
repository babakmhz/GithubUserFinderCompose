package com.babakmhz.githubuserfindercompose.data

import com.babakmhz.githubuserfindercompose.data.mapper.SearchItemResponseToUserMapper
import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.network.ApiHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val searchItemResponseToUserMapper: SearchItemResponseToUserMapper
) : RepositoryHelper {

    override suspend fun searchUsers(query: String, page: Int): Flow<List<User>> {
        return apiHelper.searchUsers(name = query, page = page).map {
            searchItemResponseToUserMapper.mapListItemsToListUsers(it)
        }
    }

    override suspend fun getUserDetails(username:String): User {
        TODO()
    }


}