package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Posts
import io.reactivex.Completable
import io.reactivex.Observable
import polanski.option.Option

interface Repository {

    fun getPosts(): Observable<Option<Posts>>

    fun fetchPosts(): Completable
}