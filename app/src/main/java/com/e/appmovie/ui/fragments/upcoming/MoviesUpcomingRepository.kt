package com.e.appmovie.ui.fragments.upcoming


import com.e.appmovie.api.MoviesApi
import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.Movie
import com.e.appmovie.api.model.MovieUpcoming
import com.e.appmovie.database.MoviesDao
import com.e.appmovie.util.Constants.apiKey
import com.e.appmovie.util.Constants.language
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesUpcomingRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) {


    private var page: Int = 1

    fun filter(query: String): Single<List<MovieUpcoming>> {
        return if (query.isNotEmpty()) {
            moviesDao.getMoviesUpcomingByQuery(query)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .map { movies -> movies.map {it.mapToMovieApi()} }
                .firstOrError()
                .onErrorResumeNext {
                    getApiMovies()
                        .map { movies ->
                            movies.filter {
                                it.title.contains(query) }
                        }
                }
        } else {
            return getLocalMovies()
        }
    }

    fun getMoreApiMovies(): Single<List<MovieUpcoming>> {
        page += 1
        return moviesApi.getMovieUpcoming(apiKey, language, page)
            .map { moviesList ->
                moviesList.results.sortedByDescending { movies -> movies.voteAverage }
            }.flatMap { movies ->
                return@flatMap Single.fromCallable {
                    moviesDao.addMoviesUpcoming(movies.map {
                        it.mapToRoomMovieUpcoming() })
                }.map {
                    return@map movies
                }
            }
    }

    fun getApiMovies(): Single<List<MovieUpcoming>> {
        return moviesApi.getMovieUpcoming(apiKey, language, page)
            .map { moviesList ->
                moviesList.results.sortedByDescending { movies -> movies.voteAverage }
            }.flatMap { movies ->
                return@flatMap Single.fromCallable {
                    moviesDao.addMoviesUpcoming(movies.map {
                        it.mapToRoomMovieUpcoming() })
                }.map {
                    return@map movies
                }
            }
    }

    fun getLocalMovies(): Single<List<MovieUpcoming>> {
        return moviesDao.getMoviesUpcoming(page)
            .map { moviesList ->
                moviesList.sortedByDescending { movies -> movies.voteAverage }
            }.map { list ->
                list.map { movie ->
                    movie.mapToMovieApi()
                }
            }
    }

    fun getApiGenres(): Single<List<Genre>> {
        return moviesApi.getGenre(apiKey, language)
            .map { genresList ->
                genresList.genres.sortedByDescending { genre -> genre.id }
            }.flatMap { genres ->
                return@flatMap Single.fromCallable {
                    moviesDao.addGenres(genres.map {
                        it.mapToRoomGenre() })
                }.map {
                    return@map genres
                }
            }
    }

    fun getLocalGenres(): Single<List<Genre>> {
        return moviesDao.getGenres()
            .map { genresList ->
                genresList.sortedByDescending { genres -> genres.id }
            }.map { list ->
                list.map { genre ->
                    genre.mapToApiGenre()
                }
            }
    }
}