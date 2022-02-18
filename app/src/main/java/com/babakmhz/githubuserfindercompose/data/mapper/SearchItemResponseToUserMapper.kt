package com.babakmhz.githubuserfindercompose.data.mapper

import com.babakmhz.githubuserfindercompose.data.model.User
import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.Item
import com.babakmhz.githubuserfindercompose.data.network.response.userSearch.SearchResponse

class SearchItemResponseToUserMapper : Mapper<Item, User> {

    override fun mapToDomainModel(model: Item): User {

        return User(
            avatar_url = model.avatar_url,
            events_url = model.events_url,
            followers_url = model.followers_url,
            following_url = model.following_url,
            gists_url = model.gists_url,
            gravatar_id = model.gravatar_id,
            html_url = model.html_url,
            id = model.id,
            login = model.login,
            node_id = model.node_id,
            organizations_url = model.organizations_url,
            received_events_url = model.received_events_url,
            repos_url = model.repos_url,
            score = model.score,
            site_admin = model.site_admin,
            starred_url = model.starred_url,
            subscriptions_url = model.subscriptions_url,
            type = model.type,
            url = model.url
        )
    }

    override fun mapFromDomainModel(domainModel: User): Item {
        return Item(
            avatar_url = domainModel.avatar_url,
            events_url = domainModel.events_url,
            followers_url = domainModel.followers_url,
            following_url = domainModel.following_url,
            gists_url = domainModel.gists_url,
            gravatar_id = domainModel.gravatar_id,
            html_url = domainModel.html_url,
            id = domainModel.id,
            login = domainModel.login,
            node_id = domainModel.node_id,
            organizations_url = domainModel.organizations_url,
            received_events_url = domainModel.received_events_url,
            repos_url = domainModel.repos_url,
            score = domainModel.score?:0.0,
            site_admin = domainModel.site_admin,
            starred_url = domainModel.starred_url,
            subscriptions_url = domainModel.subscriptions_url,
            type = domainModel.type,
            url = domainModel.url
        )
    }

    fun mapListItemsToListUsers(networkResponse: SearchResponse):List<User> {
        return networkResponse.items.map {
            mapToDomainModel(it).apply {
                totalSearchResultsCount = networkResponse.total_count
            }
        }.toList()

    }


}