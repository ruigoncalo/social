package com.ruigoncalo.data

import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.Store
import com.ruigoncalo.domain.Repository
import com.ruigoncalo.domain.model.Post
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class PostsRepository(private val remote: SocialApi,
                      private val store: Store<Int, Post>,
                      private val mapper: DataMapper) : Repository<Int, Post> {

    override fun getAll(): Observable<Option<List<Post>>> {
        return store.getAll()
    }

    override fun fetchAll(): Completable {
        return remote.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .map(mapper::map)
                .toList()
                .observeOn(Schedulers.io())
                .flatMapCompletable { posts -> store.storeAll(posts.map { Pair(it.id, it) }) }
    }

    override fun getSingular(key: Int): Observable<Option<Post>> {
        return store.getSingular(key)
    }

    override fun fetchSingular(key: Int): Completable {
        return remote.getPost(key)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(mapper::map)
                .observeOn(Schedulers.io())
                .flatMapCompletable { post -> store.storeSingular(key, post) }
    }
}