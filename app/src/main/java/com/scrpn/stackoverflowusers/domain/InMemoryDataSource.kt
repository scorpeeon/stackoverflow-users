package com.scrpn.stackoverflowusers.domain

import com.scrpn.stackoverflowusers.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryDataSource @Inject constructor() {
    var users: List<User> = emptyList()
}
