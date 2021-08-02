package com.krupa.trendroid.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krupa.trendroid.di.ViewModelKey
import com.krupa.trendroid.viewmodels.RepositoryViewModel
import com.krupa.trendroid.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
 abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryViewModel::class)
    abstract fun sampleViewModel(viewModel: RepositoryViewModel): ViewModel
}