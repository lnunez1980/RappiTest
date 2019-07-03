package com.e.appmovie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.e.appmovie.database.model.Movies

@Database(entities = arrayOf(Movies::class), version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}