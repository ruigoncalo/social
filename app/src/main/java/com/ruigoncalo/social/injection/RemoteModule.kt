package com.ruigoncalo.social.injection

import android.util.Log
import com.ruigoncalo.data.remote.RetrofitFactory
import com.ruigoncalo.data.remote.SocialApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.d("OkHttp", message) }
                .setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Provides
    fun provideClient(interceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    fun provideRetrofitFactory() = RetrofitFactory()

    @Provides
    @Singleton
    fun provideSocialApi(retrofitFactory: RetrofitFactory, client: OkHttpClient): SocialApi {
        return retrofitFactory
                .build(client)
                .create(SocialApi::class.java)
    }
}