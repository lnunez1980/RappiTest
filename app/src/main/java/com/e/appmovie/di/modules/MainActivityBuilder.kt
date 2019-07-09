package com.e.appmovie.di.modules

import com.e.appmovie.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(PopularMoviesFragmentBuilder::class,
                                                  DetailMovieFragmentBuilder::class,
                                                  TopRatedMoviesFragmentBuilder::class,
                                                  UpcomingMoviesFragmentBuilder::class))
    abstract fun bindMainActivity() : MainActivity



}