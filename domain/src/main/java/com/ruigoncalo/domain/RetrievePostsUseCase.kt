package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Posts
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option
import polanski.option.OptionUnsafe
import javax.inject.Inject

class RetrievePostsUseCase @Inject constructor(private val repository: Repository) : RetrievePostsInteractor {

    override fun retrievePosts(): Observable<Posts> {
        return repository.getPosts()
                .flatMapSingle { fetchWhenNoneAndThenRetrieve(it) }
                .filter(Option<Posts>::isSome)
                .map { OptionUnsafe.getUnsafe(it) }
    }

    private fun fetchWhenNoneAndThenRetrieve(posts: Option<Posts>): Single<Option<Posts>> {
        return fetchWhenNone(posts).andThen(Single.just(posts))
    }

    private fun fetchWhenNone(posts: Option<Posts>): Completable {
        return if (posts.isNone) repository.fetchPosts() else Completable.complete()
    }
}