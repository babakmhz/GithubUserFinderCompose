package com.babakmhz.githubuserfindercompose.data.mapper

import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.model.UserDetails
import com.babakmhz.githubuserfindercompose.data.network.response.userDetails.UserDetailsNetworkResponse

class UserDetailsResponseToUserMapper : Mapper<UserDetailsNetworkResponse, User> {

    override fun mapToDomainModel(model: UserDetailsNetworkResponse): User {

        return User(
            avatar_url = model.avatar_url,
            events_url = model.events_url,
            followers_url = model.followers_url,
            following_url = model.following_url,
            gists_url = model.gists_url,
            gravatar_id = model.gravatar_id,
            html_url = model.html_url,
            id = model.id,
            username = model.login,
            node_id = model.node_id,
            organizations_url = model.organizations_url,
            received_events_url = model.received_events_url,
            repos_url = model.repos_url,
            site_admin = model.site_admin,
            starred_url = model.starred_url,
            subscriptions_url = model.subscriptions_url,
            type = model.type,
            url = model.url,
        ).apply {
            userDetails = UserDetails(
                bio = model.bio,
                blog = model.blog,
                company = model.company,
                created_at = model.created_at,
                email = model.email,
                followers = model.followers,
                following = model.following,
                hireable = model.hireable,
                location = model.location,
                name = model.name,
                public_gists = model.public_gists,
                public_repos = model.public_repos,
                twitter_username = model.twitter_username,
                updated_at = model.updated_at
            )
        }
    }

    override fun mapFromDomainModel(domainModel: User): UserDetailsNetworkResponse {
        TODO("not implemented")
    }


}