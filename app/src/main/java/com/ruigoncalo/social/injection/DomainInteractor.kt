package com.ruigoncalo.social.injection

import com.ruigoncalo.domain.RetrievePostsInteractor
import com.ruigoncalo.domain.RetrievePostsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class DomainModule {

    @Binds
    abstract fun bindsRetrievePostsInteractor(postsUseCase: RetrievePostsUseCase): RetrievePostsInteractor
}