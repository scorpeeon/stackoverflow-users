package com.scrpn.stackoverflowusers.domain.model

import java.util.*

data class User (
    val displayName: String,
    val userId: Long,
    val profileImage: String,
    val reputation: Long,
    val location: String?,
    val creationDate: Date,
    var following: Boolean = false,
    var blocked: Boolean = false

)