package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import io.reactivex.Single

interface RetrievePostInteractor {

    fun retrievePost(id: Int): Single<Post>
}