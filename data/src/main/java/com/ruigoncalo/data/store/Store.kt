package com.ruigoncalo.data.store

import io.reactivex.Completable
import io.reactivex.Observable
import polanski.option.Option

interface Store<Value> {

    fun getSingular(): Observable<Option<Value>>

    fun putSingular(value: Value): Completable
}