package com.e.appmovie.ui.fragments.models

import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.Movie
import com.e.appmovie.api.model.MovieUpcoming

sealed class UpcomingMoviesAction {

    class OnError(val error: Throwable) : UpcomingMoviesAction()
    class OnLoading(val loading: Boolean) : UpcomingMoviesAction()
    class OnSuccess(val movies: List<MovieUpcoming>) : UpcomingMoviesAction()
    class OnFilterQueryFound(val movies: List<MovieUpcoming>) : UpcomingMoviesAction()
    class OnSuccessGenre(val genres: List<Genre>) : UpcomingMoviesAction()
}