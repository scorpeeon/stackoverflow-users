package com.scrpn.stackoverflowusers.network.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val items: List<User>?,
    val has_more: Boolean,
    @SerializedName("Response")
    val response: String
)