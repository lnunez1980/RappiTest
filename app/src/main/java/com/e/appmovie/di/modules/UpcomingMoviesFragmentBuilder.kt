package com.e.appmovie.di.modules

import androidx.lifecycle.ViewModel
import com.e.appmovie.factory.ViewModelKey
import com.e.appmovie.ui.fragments.upcoming.UpcomingMoviesFragment
import com.e.appmovie.ui.fragments.upcoming.UpcomingMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UpcomingMoviesFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindUpcomingMoviesFragment(): UpcomingMoviesFragment

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingMoviesViewModel::class)
    abstract fun bindUpcomingMoviesViewModel(viewModel: UpcomingMoviesViewModel): ViewModel
}