package com.atakanakar.challenge.di

import android.content.Context
import com.atakanakar.challenge.BuildConfig
import com.atakanakar.challenge.commons.Constants
import com.atakanakar.challenge.network.OmbdApi
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshi)
            .build()

    @Singleton
    @Provides
    fun provideOmdbApi(retrofit: Retrofit): OmbdApi =
        retrofit.create(OmbdApi::class.java)

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideMoshi(): Converter.Factory {
        return MoshiConverterFactory.create(
            Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        )
    }

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext appContext: Context): RequestManager {
        return Glide.with(appContext)
    }

}