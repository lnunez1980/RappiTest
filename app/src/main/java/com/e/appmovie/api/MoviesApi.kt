package com.e.appmovie.api

import com.e.appmovie.api.model.*

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular?")
    fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("genre/movie/list")
    fun getGenre(@Query("api_key") api_key: String,
                 @Query("language") language: String
    ): Single<GenreResponse>

    @GET("movie/{movie_id}?")
    fun getMovieDetail  (@Path("movie_id") id: String,
                         @Query("api_key") api_key: String,
                         @Query("language") language: String
    ): Single<MovieDetail>

    @GET("movie/top_rated?")
    fun getMovieToprated(@Query("api_key") api_key: String,
                         @Query("language") language: String,
                         @Query("page") page : Int
    ): Single<MovieResponseTopRated>

    @GET("movie/upcoming?")
    fun getMovieUpcoming(@Query("api_key") api_key: String,
                         @Query("language") language: String,
                         @Query("page") page : Int
    ): Single<MovieResponseUpcoming>

}