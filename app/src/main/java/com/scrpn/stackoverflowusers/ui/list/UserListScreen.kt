package com.scrpn.stackoverflowusers.ui.list

import com.scrpn.stackoverflowusers.network.model.User

interface UserListScreen {
    fun onConnectionAvailabilityChanged(available: Boolean)
    fun showLoading(loading: Boolean)
    fun onUsersLoaded(users: List<User>?)
    fun onLoadFailed()
}