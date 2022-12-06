package com.khaledsh.data.di

import com.khaledsh.data.repositories.UserRepositoryImpl
import com.khaledsh.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository

}