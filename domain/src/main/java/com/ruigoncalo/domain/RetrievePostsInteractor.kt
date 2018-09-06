package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Posts
import io.reactivex.Observable

interface RetrievePostsInteractor {

    fun retrievePosts() : Observable<Posts>
}