package com.e.appmovie.api

import com.e.appmovie.api.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular?")
    fun getPopularMovies(@Query("api_key") api_key: String,
                 @Query("language") language: String,
                 @Query("page") page : Int
    ): Observable<MovieResponse>

}