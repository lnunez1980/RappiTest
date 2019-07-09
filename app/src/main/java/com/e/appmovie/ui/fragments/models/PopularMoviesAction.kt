package com.e.appmovie.ui.fragments.models


import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.Movie

sealed class PopularMoviesAction {

    class OnError(val error: Throwable) : PopularMoviesAction()
    class OnLoading(val loading: Boolean) : PopularMoviesAction()
    class OnSuccess(val movies: List<Movie>) : PopularMoviesAction()
    class OnFilterQueryFound(val movies: List<Movie>) : PopularMoviesAction()
    class OnSuccessGenre(val genres: List<Genre>) : PopularMoviesAction()
}