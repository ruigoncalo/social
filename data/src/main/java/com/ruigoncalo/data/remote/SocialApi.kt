package com.ruigoncalo.data.remote

import com.ruigoncalo.data.model.PostRaw
import com.ruigoncalo.data.model.UserRaw
import io.reactivex.Single
import retrofit2.http.GET

interface SocialApi {

    @GET("posts")
    fun getPosts(): Single<List<PostRaw>>

    @GET("users")
    fun getUsers(): Single<List<UserRaw>>
}