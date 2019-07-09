package com.e.appmovie.di.modules

import androidx.lifecycle.ViewModelProvider
import com.e.appmovie.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}