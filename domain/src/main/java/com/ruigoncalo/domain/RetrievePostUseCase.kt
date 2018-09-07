package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import io.reactivex.Single
import polanski.option.OptionUnsafe
import javax.inject.Inject

class RetrievePostUseCase @Inject constructor(
        private val repository: Repository<Int, Post>) : RetrievePostInteractor {

    override fun retrievePost(id: Int): Single<Post> {
        return repository.getSingular(id)
                .map { OptionUnsafe.getUnsafe(it) }
    }
}