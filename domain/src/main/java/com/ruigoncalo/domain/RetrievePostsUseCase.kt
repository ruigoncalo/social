package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option
import polanski.option.OptionUnsafe
import javax.inject.Inject

class RetrievePostsUseCase @Inject constructor(
        private val repository: Repository<Int, Post>) {

    fun retrievePosts(): Observable<List<Post>> {
        return repository.getAll()
                .flatMapSingle { fetchPostsWhenNoneAndThenRetrieve(it) }
                .filter(Option<List<Post>>::isSome)
                .map { OptionUnsafe.getUnsafe(it) }
    }

    fun retrievePost(key: Int): Observable<Post> {
        return repository.getSingular(key)
                .flatMapSingle { fetchPostWhenNoneAndThenRetrieve(it, key) }
                .filter(Option<Post>::isSome)
                .map { OptionUnsafe.getUnsafe(it) }
    }

    private fun fetchPostsWhenNoneAndThenRetrieve(posts: Option<List<Post>>): Single<Option<List<Post>>> {
        return fetchPostsWhenNone(posts).andThen(Single.just(posts))
    }

    private fun fetchPostsWhenNone(posts: Option<List<Post>>): Completable {
        return if (posts.isNone) repository.fetchAll() else Completable.complete()
    }

    private fun fetchPostWhenNoneAndThenRetrieve(post: Option<Post>, key: Int): Single<Option<Post>> {
        return fetchPostWhenNone(post, key).andThen(Single.just(post))
    }

    private fun fetchPostWhenNone(posts: Option<Post>, key: Int): Completable {
        return if (posts.isNone) repository.fetchSingular(key) else Completable.complete()
    }
}