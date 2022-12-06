package com.khaledsh.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.khaledsh.core.di.scopes.AppScope
import com.khaledsh.data.api.interceptors.HeadersInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This module contains the provide methods for retrofit & OkHttp
 */
@Module
class NetworkModule {

    @AppScope
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(HeadersInterceptor())
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(loggingInterceptor)
        okHttpBuilder.readTimeout(120, TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(120, TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }

    @AppScope
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create()
    }

    @AppScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}