package com.e.appmovie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.e.appmovie.database.model.Movies
import io.reactivex.Flowable

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies where page = :page")
    fun getMovies(page : Int): Flowable<List<Movies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movie : Movies)


}