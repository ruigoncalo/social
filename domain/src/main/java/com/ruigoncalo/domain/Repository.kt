package com.ruigoncalo.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option

interface Repository<Param, Value> {

    fun getAll(): Observable<Option<List<Value>>>

    fun fetchAll(): Completable

    fun getSingular(param: Param): Single<Option<Value>>
}