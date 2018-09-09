package com.ruigoncalo.social.injection

import com.ruigoncalo.domain.RetrievePostCommentsInteractor
import com.ruigoncalo.domain.RetrievePostCommentsUseCase
import com.ruigoncalo.domain.RetrieveUsersPostsInteractor
import com.ruigoncalo.domain.RetrieveUsersPostsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class DomainModule {

    @Binds
    abstract fun bindsRetrieveUsersPostsInteractor(usersPostsUseCase: RetrieveUsersPostsUseCase): RetrieveUsersPostsInteractor

    @Binds
    abstract fun bindsRetrievePostCommentsInteractor(commentsUseCase: RetrievePostCommentsUseCase): RetrievePostCommentsInteractor
}