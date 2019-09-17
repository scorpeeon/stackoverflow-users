package com.scrpn.stackoverflowusers.ui.list

internal interface OnUserSelectedListener {
    fun onUserClicked(userId: Long)
    fun onSetUserFollowed(userId: Long, following: Boolean)
    fun onSetUserBlocked(userId: Long, blocked: Boolean)
}
