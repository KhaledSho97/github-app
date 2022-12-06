package com.khaledsh.githubapp.di

import com.khaledsh.features.home.di.HomeFragmentProvider
import com.khaledsh.features.profile.di.ProfileFragmentsProvider
import com.khaledsh.features.search.di.SearchResultFragmentProvider
import com.khaledsh.githubapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            HomeFragmentProvider::class,
            SearchResultFragmentProvider::class,
            ProfileFragmentsProvider::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

}