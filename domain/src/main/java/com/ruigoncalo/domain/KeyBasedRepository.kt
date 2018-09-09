package com.ruigoncalo.domain

import io.reactivex.Completable
import io.reactivex.Observable
import polanski.option.Option

interface KeyBasedRepository<Key, Value> {

    fun get(key: Key): Observable<Option<List<Value>>>

    fun fetch(key: Key): Completable
}