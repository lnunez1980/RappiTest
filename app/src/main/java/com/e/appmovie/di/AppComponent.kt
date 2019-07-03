package com.e.appmovie.di


import com.e.appmovie.ui.PopularMoviesFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(RetrofitModule::class)])
@Singleton
interface AppComponent {
    fun inject(popularMoviesFragment: PopularMoviesFragment)

}