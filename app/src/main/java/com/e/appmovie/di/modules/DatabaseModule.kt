package com.e.appmovie.di.modules

import android.app.Application
import androidx.room.Room
import com.e.appmovie.database.MoviesDao
import com.e.appmovie.database.MoviesDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MoviesDataBase {
        return Room.databaseBuilder(application, MoviesDataBase::class.java, "movies-db").build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(moviesDataBase: MoviesDataBase): MoviesDao {
        return moviesDataBase.moviesDao()
    }
}