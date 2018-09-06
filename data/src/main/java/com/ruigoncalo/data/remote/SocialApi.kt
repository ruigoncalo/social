package com.ruigoncalo.data.remote

import com.ruigoncalo.data.model.PostRaw
import io.reactivex.Single
import retrofit2.http.GET

interface SocialApi {

    @GET("posts")
    fun getPosts(): Single<List<PostRaw>>
}