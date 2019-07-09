package com.e.appmovie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.e.appmovie.database.entities.*

@Database(entities = arrayOf(Movies::class,GenreRoom::class,MovieDetailRoom::class,MoviesTopRatedRoom::class,MovieUpcomingRoom::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}