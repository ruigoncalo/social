package com.ruigoncalo.data

import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.Store
import com.ruigoncalo.domain.KeyBasedRepository
import com.ruigoncalo.domain.model.Comment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class CommentsRepository(private val remote: SocialApi,
                         private val store: Store<Int, List<Comment>>,
                         private val mapper: DataMapper) : KeyBasedRepository<Int, Comment> {


    override fun get(key: Int): Observable<Option<List<Comment>>> {
        return store.getSingular(key)
    }

    override fun fetch(key: Int): Completable {
        return remote.getCommentsByPost(key)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .map(mapper::map)
                .toList()
                .observeOn(Schedulers.io())
                .flatMapCompletable { comments -> store.storeSingular(key, comments) }
    }
}