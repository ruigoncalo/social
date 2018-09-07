package com.ruigoncalo.social.injection

import com.ruigoncalo.data.DataMapper
import com.ruigoncalo.data.GetPostsRepository
import com.ruigoncalo.data.cache.KeyValueCache
import com.ruigoncalo.data.cache.MemoryKeyValueCache
import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.KeyValueStore
import com.ruigoncalo.data.store.ReactiveKeyValueStore
import com.ruigoncalo.domain.Repository
import com.ruigoncalo.domain.model.Post
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesCache(): KeyValueCache<Int, Post> {
        return MemoryKeyValueCache()
    }

    @Provides
    @Singleton
    fun providesStore(cache: KeyValueCache<Int, Post>): KeyValueStore<Int, Post> {
        return ReactiveKeyValueStore(cache)
    }

    @Provides
    fun providesRepository(remote: SocialApi, store: KeyValueStore<Int, Post>, mapper: DataMapper): Repository<Int, Post> {
        return GetPostsRepository(remote, store, mapper)
    }
}