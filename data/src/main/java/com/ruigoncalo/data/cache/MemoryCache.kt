package com.ruigoncalo.data.cache

class MemoryCache<Value> : Cache<Value> {

    private var value: Value? = null

    override fun put(value: Value) {
        this.value = value
    }

    override fun get(): Value {
        return value ?: throw IllegalAccessException("Value is not stored")
    }

}