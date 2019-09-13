package com.scrpn.stackoverflowusers.network.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("profile_image")
    val profileImage: String,
    val reputation: Long,
    val location: String?,
    @SerializedName("creation_date")
    val creationDate: Long

)