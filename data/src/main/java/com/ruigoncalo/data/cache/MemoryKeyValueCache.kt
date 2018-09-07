package com.ruigoncalo.data.cache

class MemoryKeyValueCache<Key, Value> : KeyValueCache<Key, Value> {

    private var valuesMap: HashMap<Key, Value> = hashMapOf()

    override fun getAll(): List<Value> {
        return if (valuesMap.isEmpty()) {
            throw IllegalStateException("No values")
        } else {
            valuesMap.values.toList()
        }
    }

    override fun putAll(values: List<Pair<Key, Value>>) {
        values.forEach { valuesMap[it.first] = it.second }
    }

    override fun getSingular(key: Key): Value {
        return valuesMap[key] ?: throw IllegalStateException("Key is not stored")
    }

    override fun putSingular(key: Key, value: Value) {
        valuesMap[key] = value
    }
}