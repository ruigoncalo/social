package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option
import polanski.option.OptionUnsafe
import javax.inject.Inject

class RetrievePostsUseCase @Inject constructor(
        private val repository: Repository<Int, Post>) : RetrievePostsInteractor {

    override fun retrievePosts(): Observable<List<Post>> {
        return repository.getAll()
                .flatMapSingle { fetchWhenNoneAndThenRetrieve(it) }
                .filter(Option<List<Post>>::isSome)
                .map { OptionUnsafe.getUnsafe(it) }
    }

    private fun fetchWhenNoneAndThenRetrieve(posts: Option<List<Post>>): Single<Option<List<Post>>> {
        return fetchWhenNone(posts).andThen(Single.just(posts))
    }

    private fun fetchWhenNone(posts: Option<List<Post>>): Completable {
        return if (posts.isNone) repository.fetchAll() else Completable.complete()
    }
}