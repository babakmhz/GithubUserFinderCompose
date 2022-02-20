package com.babakmhz.githubuserfindercompose.data.network

object ApiEndpoints{
    const val SEARCH = "search/users"
    const val USER_DETAIL = "/users/{${Constants.DETAILS_USERNAME_PATH_VALUE}}"
}

object Constants {
    const val PAGE_SIZE_CONFIG_FOR_API = 30
    const val DETAILS_USERNAME_PATH_VALUE = "username"
}