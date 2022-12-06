package com.khaledsh.data.di

import com.khaledsh.core.di.scopes.AppScope
import com.khaledsh.data.api.services.GithubServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * This module has the API services to fetch the data
 */
@Module(includes = [NetworkModule::class])
class ApiModule {

    @AppScope
    @Provides
    fun provideGithubApiService(retrofit: Retrofit): GithubServices {
        return retrofit.create(GithubServices::class.java)
    }

}