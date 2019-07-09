package com.e.appmovie.di.modules

import androidx.lifecycle.ViewModel
import com.e.appmovie.factory.ViewModelKey
import com.e.appmovie.ui.fragments.Popular.PopularMoviesFragment
import com.e.appmovie.ui.fragments.Popular.PopularMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PopularMoviesFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindPopularMoviesFragment(): PopularMoviesFragment

    @Binds
    @IntoMap
    @ViewModelKey(PopularMoviesViewModel::class)
    abstract fun bindPopularMoviesViewModel(viewModel: PopularMoviesViewModel): ViewModel
}