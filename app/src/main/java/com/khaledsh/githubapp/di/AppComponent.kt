package com.khaledsh.githubapp.di

import com.khaledsh.core.di.CoreComponent
import com.khaledsh.core.di.scopes.AppScope
import com.khaledsh.data.di.ApiModule
import com.khaledsh.data.di.NetworkModule
import com.khaledsh.data.di.RepositoryModule
import com.khaledsh.data.di.RoomModule
import com.khaledsh.githubapp.GithubApp
import dagger.Component
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    dependencies = [
        CoreComponent::class
    ],
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        ApiModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        RoomModule::class
    ]
)
interface AppComponent {
    fun inject(application: GithubApp)
}