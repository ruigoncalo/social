package com.ruigoncalo.social.injection

import com.ruigoncalo.data.CommentsRepository
import com.ruigoncalo.data.DataMapper
import com.ruigoncalo.data.PostsRepository
import com.ruigoncalo.data.UsersRepository
import com.ruigoncalo.data.cache.Cache
import com.ruigoncalo.data.cache.MemoryCache
import com.ruigoncalo.data.remote.SocialApi
import com.ruigoncalo.data.store.ReactiveStore
import com.ruigoncalo.data.store.Store
import com.ruigoncalo.domain.KeyBasedRepository
import com.ruigoncalo.domain.Repository
import com.ruigoncalo.domain.model.Comment
import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesPostsCache(): Cache<Int, Post> {
        return MemoryCache()
    }

    @Provides
    @Singleton
    fun providesUsersCache(): Cache<Int, User> {
        return MemoryCache()
    }

    @Provides
    @Singleton
    fun providesCommentsCache(): Cache<Int, List<Comment>> {
        return MemoryCache()
    }

    @Provides
    @Singleton
    fun providesPostsStore(cache: Cache<Int, Post>): Store<Int, Post> {
        return ReactiveStore(cache)
    }

    @Provides
    @Singleton
    fun providesUsersStore(cache: Cache<Int, User>): Store<Int, User> {
        return ReactiveStore(cache)
    }

    @Provides
    @Singleton
    fun providesCommentsStore(cache: Cache<Int, List<Comment>>): Store<Int, List<Comment>> {
        return ReactiveStore(cache)
    }

    @Provides
    fun providesPostsRepository(remote: SocialApi, store: Store<Int, Post>, mapper: DataMapper): Repository<Int, Post> {
        return PostsRepository(remote, store, mapper)
    }

    @Provides
    fun providesUsersRepository(remote: SocialApi, store: Store<Int, User>, mapper: DataMapper): Repository<Int, User> {
        return UsersRepository(remote, store, mapper)
    }

    @Provides
    fun providesCommentsRepository(remote: SocialApi, store: Store<Int, List<Comment>>, mapper: DataMapper): KeyBasedRepository<Int, Comment> {
        return CommentsRepository(remote, store, mapper)
    }
}