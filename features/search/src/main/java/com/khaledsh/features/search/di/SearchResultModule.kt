package com.khaledsh.features.search.di


import com.khaledsh.core.di.ViewModelKey
import com.khaledsh.core.di.scopes.FeatureScope
import com.khaledsh.features.search.ui.adapter.SearchLoadStateAdapter
import com.khaledsh.features.search.ui.adapter.SearchResultAdapter
import com.khaledsh.features.search.ui.adapter.SearchResultAdapterImpl
import com.khaledsh.features.search.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface SearchResultModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): SearchViewModel

    @FeatureScope
    @Binds
    fun bindSearchResultAdapter(searchResultAdapterImpl: SearchResultAdapterImpl): SearchResultAdapter

    companion object {

        @FeatureScope
        @Provides
        fun provideLoadStateAdapter(): SearchLoadStateAdapter = SearchLoadStateAdapter()

    }

}