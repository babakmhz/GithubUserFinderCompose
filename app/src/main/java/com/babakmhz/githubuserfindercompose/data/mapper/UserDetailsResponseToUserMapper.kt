package com.babakmhz.githubuserfindercompose.data.mapper

import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.model.UserDetails
import com.babakmhz.githubuserfindercompose.data.network.response.userDetails.UserDetailsNetworkResponse

class UserDetailsResponseToUserMapper : Mapper<UserDetailsNetworkResponse, User> {

    override fun mapToDomainModel(response: UserDetailsNetworkResponse): User {

        return User(
            avatar_url = response.avatar_url,
            events_url = response.events_url,
            followers_url = response.followers_url,
            following_url = response.following_url,
            gists_url = response.gists_url,
            gravatar_id = response.gravatar_id,
            html_url = response.html_url,
            id = response.id,
            login = response.login,
            node_id = response.node_id,
            organizations_url = response.organizations_url,
            received_events_url = response.received_events_url,
            repos_url = response.repos_url,
            site_admin = response.site_admin,
            starred_url = response.starred_url,
            subscriptions_url = response.subscriptions_url,
            type = response.type,
            url = response.url,
        ).apply {
            userDetails = UserDetails(
                bio = response.bio,
                blog = response.blog,
                company = response.company,
                created_at = response.created_at,
                email = response.email,
                followers = response.followers,
                following = response.following,
                hireable = response.hireable,
                location = response.location,
                name = response.name,
                public_gists = response.public_gists,
                public_repos = response.public_repos,
                twitter_username = response.twitter_username,
                updated_at = response.updated_at
            )
        }
    }

    override fun mapFromDomainModel(domainModel: User): UserDetailsNetworkResponse {
        TODO("not implemented")
    }


}