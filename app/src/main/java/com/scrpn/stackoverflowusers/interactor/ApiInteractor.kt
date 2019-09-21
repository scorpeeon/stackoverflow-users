package com.scrpn.stackoverflowusers.interactor

import com.scrpn.stackoverflowusers.domain.InMemoryDataSource
import com.scrpn.stackoverflowusers.domain.model.User
import com.scrpn.stackoverflowusers.network.NetworkDataSource
import io.reactivex.Observable
import javax.inject.Inject

class ApiInteractor @Inject constructor(
    var networkDataSource: NetworkDataSource,
    var inMemoryDataSource: InMemoryDataSource
) {

    fun getUsers(): Observable<List<User>> {
        return networkDataSource.getUsers()
            .map { users: List<com.scrpn.stackoverflowusers.network.model.User> ->
                val updatedUsers = users.map { user ->
                    val existingUser = inMemoryDataSource.users.find { it.userId == user.userId }
                    val followingExisting = existingUser?.following ?: false
                    val blockedExisting = existingUser?.blocked ?: false
                    User(
                        displayName = user.displayName,
                        userId = user.userId,
                        profileImage = user.profileImage,
                        reputation = user.reputation,
                        location = user.location,
                        creationDate = user.creationDate,
                        following = followingExisting,
                        blocked = blockedExisting
                    )
                }
                setLocalUsers(updatedUsers)
                updatedUsers
            }
    }

    fun getLocalUsers(): List<User> {
        return inMemoryDataSource.users
    }

    private fun setLocalUsers(users : List<User>) {
        inMemoryDataSource.users = users
    }
}
