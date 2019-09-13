package com.scrpn.stackoverflowusers.network

import com.scrpn.stackoverflowusers.network.model.UsersResponse

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StackExchangeApi {
    @GET("/users")
    fun getUsers(
        @Query("pagesize") pageSize: String, @Query("order") order: String,
        @Query("sort") sort: String, @Query("site") site: String
    ): Observable<UsersResponse>
}
