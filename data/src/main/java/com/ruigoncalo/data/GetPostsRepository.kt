package com.ruigoncalo.data

import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.Store
import com.ruigoncalo.domain.Repository
import com.ruigoncalo.domain.model.Posts
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class GetPostsRepository(private val remote: SocialApi,
                         private val store: Store<Posts>,
                         private val mapper: DataMapper) : Repository {

    override fun getPosts(): Observable<Option<Posts>> {
        return store.getSingular()
    }

    override fun fetchPosts(): Completable {
        return remote.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(mapper::map)
                .flatMapCompletable { rate -> store.putSingular(rate) }
    }
}