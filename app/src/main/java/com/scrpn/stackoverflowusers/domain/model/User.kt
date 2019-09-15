package com.scrpn.stackoverflowusers.domain.model

data class User (
    val displayName: String,
    val userId: Long,
    val profileImage: String,
    val reputation: Long,
    val location: String?,
    val creationDate: Long,
    var following: Boolean = false,
    var blocked: Boolean = false

)