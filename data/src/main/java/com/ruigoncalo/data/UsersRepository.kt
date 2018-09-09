package com.ruigoncalo.data

import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.Store
import com.ruigoncalo.domain.Repository
import com.ruigoncalo.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class UsersRepository(private val remote: SocialApi,
                      private val store: Store<Int, User>,
                      private val mapper: DataMapper) : Repository<Int, User> {

    override fun getAll(): Observable<Option<List<User>>> {
        return store.getAll()
    }

    override fun fetchAll(): Completable {
        return remote.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .map(mapper::map)
                .toList()
                .flatMapCompletable { users -> store.storeAll(users.map { Pair(it.id, it) }) }
    }

    override fun getSingular(key: Int): Observable<Option<User>> {
        return store.getSingular(key)
    }

    override fun fetchSingular(key: Int): Completable {
        return remote.getUser(key)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(mapper::map)
                .flatMapCompletable { user -> store.storeSingular(key, user) }
    }
}