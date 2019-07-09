package com.e.appmovie.di.modules

import androidx.lifecycle.ViewModel
import com.e.appmovie.factory.ViewModelKey
import com.e.appmovie.ui.fragments.detail.DetailMoviesFragment
import com.e.appmovie.ui.fragments.detail.DetailMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DetailMovieFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindDetailMoviesFragment(): DetailMoviesFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailMoviesViewModel::class)
    abstract fun bindDetailMoviesViewModel(viewModel: DetailMoviesViewModel): ViewModel
}