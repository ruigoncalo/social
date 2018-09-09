package com.ruigoncalo.domain

import io.reactivex.Completable
import io.reactivex.Observable
import polanski.option.Option

interface Repository<Key, Value> {

    fun getAll(): Observable<Option<List<Value>>>

    fun fetchAll(): Completable

    fun getSingular(key: Key): Observable<Option<Value>>

    fun fetchSingular(key: Key): Completable
}