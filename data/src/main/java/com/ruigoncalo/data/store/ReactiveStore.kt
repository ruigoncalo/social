package com.ruigoncalo.data.store

import com.ruigoncalo.data.cache.Cache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import polanski.option.Option

class ReactiveStore<Key, Value>(private val cache: Cache<Key, Value>) : Store<Key, Value> {

    private val subjectSingular: PublishSubject<Option<Value>> = PublishSubject.create()
    private val subjectAll: PublishSubject<Option<List<Value>>> = PublishSubject.create()

    override fun getAll(): Observable<Option<List<Value>>> {
        return Observable.defer { Observable.just(Option.ofObj(cache.getAll())) }
                .onErrorResumeNext { _: Throwable -> Observable.just<Option<List<Value>>>(Option.none()) }
                .concatWith(subjectAll)
    }

    override fun storeAll(values: List<Pair<Key, Value>>): Completable {
        return Completable.fromCallable {
            cache.putAll(values)
            subjectAll.onNext(Option.ofObj(values.map { it.second }))
        }
    }

    override fun getSingular(key: Key): Observable<Option<Value>> {
        return Observable.defer { Observable.just(Option.ofObj(cache.getSingular(key))) }
                .onErrorResumeNext { _: Throwable -> Observable.just<Option<Value>>(Option.none()) }
                .concatWith(subjectSingular)
    }

    override fun storeSingular(key: Key, value: Value): Completable {
        return Completable.fromCallable {
            cache.putSingular(key, value)
            subjectSingular.onNext(Option.ofObj(value))
        }
    }
}