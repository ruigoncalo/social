package com.ruigoncalo.data.store

import io.reactivex.Completable
import io.reactivex.Observable
import polanski.option.Option

interface KeyValueStore<Key, Value> {

    fun getAll(): Observable<Option<List<Value>>>

    fun storeAll(values: List<Pair<Key,Value>>): Completable

    fun getSingular(key: Key): Observable<Option<Value>>

    fun storeSingular(key: Key, value: Value): Completable
}