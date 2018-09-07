package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import io.reactivex.Observable

interface RetrievePostsInteractor {

    fun retrievePosts(): Observable<List<Post>>
}