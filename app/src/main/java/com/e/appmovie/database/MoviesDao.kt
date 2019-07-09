package com.e.appmovie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.e.appmovie.api.model.MovieTopRated
import com.e.appmovie.api.model.MovieUpcoming
import com.e.appmovie.database.entities.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies where page = :page")
    fun getMovies(page: Int): Single<List<Movies>>

    @Query("SELECT * FROM movies where title like '%' || :query || '%'")
    fun getMoviesByQuery(query: String): Observable<List<Movies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movie: List<Movies>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGenres(movie: List<GenreRoom>)

    @Query("SELECT * FROM genres")
    fun getGenres(): Single<List<GenreRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMoviesDetail(movie: MovieDetailRoom)

    @Query("SELECT * FROM moviedetail where id = :movieId")
    fun getMovieDetail(movieId:String): Single<MovieDetailRoom>

    @Query("SELECT * FROM moviestoprated where page = :page")
    fun getMoviesTopRated(page: Int): Single<List<MoviesTopRatedRoom>>

    @Query("SELECT * FROM moviestoprated where title like '%' || :query || '%'")
    fun getMoviesTopRatedByQuery(query: String): Observable<List<MoviesTopRatedRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMoviesTopRated(movie: List<MoviesTopRatedRoom>)

    @Query("SELECT * FROM moviesupcoming where page = :page")
    fun getMoviesUpcoming(page: Int): Single<List<MovieUpcomingRoom>>

    @Query("SELECT * FROM moviesupcoming where title like '%' || :query || '%'")
    fun getMoviesUpcomingByQuery(query: String): Observable<List<MovieUpcomingRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMoviesUpcoming(movie: List<MovieUpcomingRoom>)
}