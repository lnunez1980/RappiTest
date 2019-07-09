package com.e.appmovie.di.modules

import androidx.lifecycle.ViewModel
import com.e.appmovie.factory.ViewModelKey
import com.e.appmovie.ui.fragments.toprated.TopRatedMoviesFragment
import com.e.appmovie.ui.fragments.toprated.TopRatedMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TopRatedMoviesFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindTopRatedMoviesFragment(): TopRatedMoviesFragment

    @Binds
    @IntoMap
    @ViewModelKey(TopRatedMoviesViewModel::class)
    abstract fun bindTopRatedMoviesViewModel(viewModel: TopRatedMoviesViewModel): ViewModel
}