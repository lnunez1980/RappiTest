package com.e.appmovie.ui.fragments.toprated


import com.e.appmovie.api.MoviesApi
import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.MovieTopRated
import com.e.appmovie.database.MoviesDao
import com.e.appmovie.util.Constants.apiKey
import com.e.appmovie.util.Constants.language
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesTopRatedRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) {


    private var page: Int = 1

    fun filter(query: String): Single<List<MovieTopRated>> {
        return if (query.isNotEmpty()) {
            moviesDao.getMoviesTopRatedByQuery(query)
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

    fun getMoreApiMovies(): Single<List<MovieTopRated>> {
        page += 1
        return moviesApi.getMovieToprated(apiKey, language, page)
            .map { moviesList ->
                moviesList.results.sortedByDescending { movies -> movies.voteAverage }
            }.flatMap { movies ->
                return@flatMap Single.fromCallable {
                    moviesDao.addMoviesTopRated(movies.map {
                        it.mapToRoomMovietoprated() })
                }.map {
                    return@map movies
                }
            }
    }

    fun getApiMovies(): Single<List<MovieTopRated>> {
        return moviesApi.getMovieToprated(apiKey, language, page)
            .map { moviesList ->
                moviesList.results.sortedByDescending { movies -> movies.voteAverage }
            }.flatMap { movies ->
                return@flatMap Single.fromCallable {
                    moviesDao.addMoviesTopRated(movies.map {
                        it.mapToRoomMovietoprated() })
                }.map {
                    return@map movies
                }
            }
    }

    fun getLocalMovies(): Single<List<MovieTopRated>> {
        return moviesDao.getMoviesTopRated(page)
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