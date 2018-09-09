package com.ruigoncalo.social.injection

import android.content.Context
import com.ruigoncalo.social.ui.AppNavigator
import com.ruigoncalo.social.ui.StringsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Provides
    @Singleton
    fun provideAppNavigator(context: Context): AppNavigator {
        return AppNavigator(context)
    }

    @Provides
    @Singleton
    fun provideStringsProvider(context: Context): StringsProvider {
        return StringsProvider(context)
    }
}