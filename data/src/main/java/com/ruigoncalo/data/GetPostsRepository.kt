package com.ruigoncalo.data

import com.ruigoncalo.data.model.PostRaw
import com.ruigoncalo.data.model.UserRaw
import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.KeyValueStore
import com.ruigoncalo.domain.Repository
import com.ruigoncalo.domain.model.Post
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class GetPostsRepository(private val remote: SocialApi,
                         private val store: KeyValueStore<Int, Post>,
                         private val mapper: DataMapper) : Repository<Int, Post> {

    override fun getAll(): Observable<Option<List<Post>>> {
        return store.getAll()
    }

    override fun fetchAll(): Completable {
        return remote.getPosts()
                .zipWith(remote.getUsers(),
                        BiFunction<List<PostRaw>, List<UserRaw>, List<Post>> { posts, users -> mapper.map(posts, users) }
                )
                .subscribeOn(Schedulers.io())
                .flatMapCompletable { posts -> store.storeAll(posts.map { Pair(it.id, it) }) }
    }

    override fun getSingular(param: Int): Single<Option<Post>> {
        return store.getSingular(param).firstOrError()
    }
}