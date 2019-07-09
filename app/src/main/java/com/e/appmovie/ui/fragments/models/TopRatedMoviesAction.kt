package com.e.appmovie.ui.fragments.models

import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.MovieTopRated

sealed class TopRatedMoviesAction {

    class OnError(val error: Throwable) : TopRatedMoviesAction()
    class OnLoading(val loading: Boolean) : TopRatedMoviesAction()
    class OnSuccess(val movies: List<MovieTopRated>) : TopRatedMoviesAction()
    class OnFilterQueryFound(val movies: List<MovieTopRated>) : TopRatedMoviesAction()
    class OnSuccessGenre(val genres: List<Genre>) : TopRatedMoviesAction()
}