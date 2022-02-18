package com.babakmhz.githubuserfindercompose.data.model

data class User(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val username: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val score: Double? = null,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
) {
    var totalSearchResultsCount = 0
    var userDetails: UserDetails? = null
}

data class UserDetails(
    val bio: String,
    val blog: String,
    val company: Any,
    val created_at: String,
    val email: Any,
    val followers: Int,
    val following: Int,
    val hireable: Boolean,
    val location: String,
    val name: String,
    val public_gists: Int,
    val public_repos: Int,
    val twitter_username: String?,
    val updated_at: String,
)