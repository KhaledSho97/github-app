package com.khaledsh.features.home.di

import androidx.lifecycle.ViewModel
import com.khaledsh.core.di.ViewModelKey
import com.khaledsh.core.di.scopes.FeatureScope
import com.khaledsh.features.home.ui.HomeFragment
import com.khaledsh.features.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HomeFragmentProvider {

    @FeatureScope
    @ContributesAndroidInjector(
        modules = [
            HomeModule::class
        ]
    )
    fun contributeHomeFragment(): HomeFragment


    @Module
    interface HomeModule {

        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        fun bindHomeViewMode(viewModel: HomeViewModel): ViewModel

    }
}