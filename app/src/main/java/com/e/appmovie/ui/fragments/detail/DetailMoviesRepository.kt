package com.e.appmovie.ui.fragments.detail

import com.e.appmovie.api.MoviesApi
import com.e.appmovie.api.model.Movie
import com.e.appmovie.api.model.MovieDetail
import com.e.appmovie.database.MoviesDao
import com.e.appmovie.util.Constants
import io.reactivex.Single
import javax.inject.Inject

class DetailMoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) {
    fun getApiMoviesDetail(movieId: String): Single<MovieDetail> {
        return moviesApi.getMovieDetail(movieId,Constants.apiKey, Constants.language)
            .map {moviesDetailList ->
                moviesDao.addMoviesDetail(moviesDetailList.mapToMovieDetailRoom())
                return@map moviesDetailList
            }

    }

    fun getLocalMoviesDetail(movieId: String): Single<MovieDetail> {
        return moviesDao.getMovieDetail(movieId)
            .map { list ->
                list.mapToMovieDetailApi()
            }
    }
}