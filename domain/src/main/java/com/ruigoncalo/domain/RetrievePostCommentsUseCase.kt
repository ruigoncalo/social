package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Comment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option
import polanski.option.OptionUnsafe
import javax.inject.Inject

class RetrievePostCommentsUseCase @Inject constructor(
        private val repository: KeyBasedRepository<Int, Comment>) : RetrievePostCommentsInteractor {

    override fun retrievePostComments(key: Int): Observable<List<Comment>> {
        return repository.get(key)
                .flatMapSingle { fetchWhenNoneAndThenRetrieve(it, key) }
                .filter(Option<List<Comment>>::isSome)
                .map { OptionUnsafe.getUnsafe(it) }
    }

    private fun fetchWhenNoneAndThenRetrieve(user: Option<List<Comment>>, key: Int): Single<Option<List<Comment>>> {
        return fetchWhenNone(user, key).andThen(Single.just(user))
    }

    private fun fetchWhenNone(user: Option<List<Comment>>, key: Int): Completable {
        return if (user.isNone) repository.fetch(key) else Completable.complete()
    }
}