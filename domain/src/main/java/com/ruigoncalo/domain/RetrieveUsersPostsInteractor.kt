package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.UserPost
import io.reactivex.Observable

interface RetrieveUsersPostsInteractor {

    fun retrieveUsersPosts(): Observable<List<UserPost>>

    fun retrieveUserPost(key: Int): Observable<UserPost>
}