package com.e.appmovie.ui.fragments.Popular


import com.e.appmovie.api.MoviesApi
import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.Movie
import com.e.appmovie.database.MoviesDao
import com.e.appmovie.util.Constants.apiKey
import com.e.appmovie.util.Constants.language
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) {


    private var page: Int = 1

    fun filter(query: String): Single<List<Movie>> {
        return if (query.isNotEmpty()) {
            moviesDao.getMoviesByQuery(query)
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

    fun getMoreApiMovies(): Single<List<Movie>> {
        page += 1
        return moviesApi.getPopularMovies(apiKey, language, page)
            .map { moviesList ->
                moviesList.results.sortedByDescending { movies -> movies.voteAverage }
            }.flatMap { movies ->
                return@flatMap Single.fromCallable {
                    moviesDao.addMovies(movies.map {
                        it.mapToRoomMovie() })
                }.map {
                    return@map movies
                }
            }
    }

    fun getApiMovies(): Single<List<Movie>> {
        return moviesApi.getPopularMovies(apiKey, language, page)
            .map { moviesList ->
                moviesList.results.sortedByDescending { movies -> movies.voteAverage }
            }.flatMap { movies ->
                return@flatMap Single.fromCallable {
                    moviesDao.addMovies(movies.map {
                        it.mapToRoomMovie() })
                }.map {
                    return@map movies
                }
            }
    }

    fun getLocalMovies(): Single<List<Movie>> {
        return moviesDao.getMovies(page)
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