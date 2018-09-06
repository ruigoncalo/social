package com.ruigoncalo.social.injection

import com.ruigoncalo.social.ui.PostsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributesMessagesActivity(): PostsActivity

}