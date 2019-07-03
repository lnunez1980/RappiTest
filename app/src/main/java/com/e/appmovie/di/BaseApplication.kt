package com.e.appmovie.di

import android.app.Application
import androidx.room.Room
import com.e.appmovie.database.MoviesDataBase

class BaseApplication : Application() {

    companion object {
        lateinit var database: MoviesDataBase
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        database =  Room.databaseBuilder(this, MoviesDataBase::class.java, "movies-db").build()
        appComponent = DaggerAppComponent
            .builder()
            .retrofitModule(RetrofitModule())
            .build()


    }




}