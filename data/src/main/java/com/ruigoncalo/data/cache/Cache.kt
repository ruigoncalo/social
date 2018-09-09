package com.ruigoncalo.data.cache

interface Cache<Key, Value> {

    fun getAll(): List<Value>

    fun putAll(values: List<Pair<Key, Value>>)

    fun getSingular(key: Key): Value

    fun putSingular(key: Key, value: Value)
}