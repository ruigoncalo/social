package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option
import polanski.option.OptionUnsafe
import javax.inject.Inject

class RetrieveUserUseCase @Inject constructor(
        private val repository: Repository<Int, User>) {

    fun retrieveUsers(): Observable<List<User>> {
        return repository.getAll()
                .flatMapSingle { fetchUsersWhenNoneAndThenRetrieve(it) }
                .filter(Option<List<User>>::isSome)
                .map { OptionUnsafe.getUnsafe(it) }
    }

    fun retrieveUser(key: Int): Observable<User> {
        return repository.getSingular(key)
                .flatMapSingle { fetchUserWhenNoneAndThenRetrieve(it, key) }
                .filter(Option<User>::isSome)
                .map { OptionUnsafe.getUnsafe(it) }
    }

    private fun fetchUsersWhenNoneAndThenRetrieve(users: Option<List<User>>): Single<Option<List<User>>> {
        return fetchUsersWhenNone(users).andThen(Single.just(users))
    }

    private fun fetchUsersWhenNone(user: Option<List<User>>): Completable {
        return if (user.isNone) repository.fetchAll() else Completable.complete()
    }

    private fun fetchUserWhenNoneAndThenRetrieve(user: Option<User>, key: Int): Single<Option<User>> {
        return fetchUserWhenNone(user, key).andThen(Single.just(user))
    }

    private fun fetchUserWhenNone(user: Option<User>, key: Int): Completable {
        return if (user.isNone) repository.fetchSingular(key) else Completable.complete()
    }
}