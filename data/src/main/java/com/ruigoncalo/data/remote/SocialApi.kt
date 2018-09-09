package com.ruigoncalo.data.remote

import com.ruigoncalo.data.model.CommentRaw
import com.ruigoncalo.data.model.PostRaw
import com.ruigoncalo.data.model.UserRaw
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SocialApi {

    @GET("posts")
    fun getPosts(): Single<List<PostRaw>>

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Single<PostRaw>

    @GET("users")
    fun getUsers(): Single<List<UserRaw>>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Single<UserRaw>

    @GET("comments")
    fun getComments(): Single<List<CommentRaw>>

    @GET("posts/{id}/comments")
    fun getCommentsByPost(@Path("id") id: Int): Single<List<CommentRaw>>
}