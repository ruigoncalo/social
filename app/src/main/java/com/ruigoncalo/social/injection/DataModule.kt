package com.ruigoncalo.social.injection

import com.ruigoncalo.data.DataMapper
import com.ruigoncalo.data.GetPostsRepository
import com.ruigoncalo.data.cache.Cache
import com.ruigoncalo.data.cache.MemoryCache
import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.ReactiveStore
import com.ruigoncalo.data.store.Store
import com.ruigoncalo.domain.Repository
import com.ruigoncalo.domain.model.Posts
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesCache(): Cache<Posts> {
        return MemoryCache()
    }

    @Provides
    fun providesStore(cache: Cache<Posts>): Store<Posts> {
        return ReactiveStore(cache)
    }

    @Provides
    fun providesRepository(remote: SocialApi, store: Store<Posts>, mapper: DataMapper): Repository {
        return GetPostsRepository(remote, store, mapper)
    }
}