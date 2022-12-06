package com.khaledsh.features.search.di

import com.khaledsh.core.di.scopes.FeatureScope
import com.khaledsh.features.search.ui.SearchResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface SearchResultFragmentProvider {

    @FeatureScope
    @ContributesAndroidInjector(
        modules = [
            SearchResultModule::class
        ]
    )
    fun contributeSearchFragment(): SearchResultFragment

}