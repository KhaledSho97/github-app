package com.khaledsh.features.profile.di


import com.khaledsh.core.di.ViewModelKey
import com.khaledsh.core.di.scopes.FeatureScope
import com.khaledsh.features.profile.ui.ListFragment
import com.khaledsh.features.profile.ui.ProfileFragment
import com.khaledsh.features.profile.ui.adapter.ListAdapter
import com.khaledsh.features.profile.ui.adapter.ListAdapterImpl
import com.khaledsh.features.profile.ui.adapter.ListLoadStateAdapter
import com.khaledsh.features.profile.viewmodel.ListViewModel
import com.khaledsh.features.profile.viewmodel.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ProfileFragmentsProvider {

    @FeatureScope
    @ContributesAndroidInjector(
        modules = [
            ProfileModule::class
        ]
    )
    fun contributeProfileFragment(): ProfileFragment

    @FeatureScope
    @ContributesAndroidInjector(
        modules = [
            ListModule::class
        ]
    )
    fun contributeListFragment(): ListFragment

    @Module
    interface ProfileModule {

        @Binds
        @IntoMap
        @ViewModelKey(ProfileViewModel::class)
        fun bindProfileViewModel(viewModel: ProfileViewModel): ProfileViewModel

    }

    @Module
    interface ListModule {

        @Binds
        @IntoMap
        @ViewModelKey(ListViewModel::class)
        fun bindListViewModel(viewModel: ListViewModel): ListViewModel

        @FeatureScope
        @Binds
        fun bindListAdapter(listAdapterImpl: ListAdapterImpl): ListAdapter

        companion object {

            @FeatureScope
            @Provides
            fun provideLoadStateAdapter(): ListLoadStateAdapter = ListLoadStateAdapter()

        }

    }

}