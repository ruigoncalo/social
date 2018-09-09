package com.ruigoncalo.social.injection

import com.ruigoncalo.social.ui.detail.PostDetailActivity
import com.ruigoncalo.social.ui.posts.PostsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributesPostsActivity(): PostsActivity

    @ContributesAndroidInjector
    abstract fun contributesPostDetailActivity(): PostDetailActivity
}