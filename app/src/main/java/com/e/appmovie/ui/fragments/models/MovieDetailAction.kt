package com.e.appmovie.ui.fragments.models

import com.e.appmovie.api.model.MovieDetail

sealed class MovieDetailAction {

    class OnError(val error: Throwable) : MovieDetailAction()
    class OnLoading(val loading: Boolean) : MovieDetailAction()
    class OnSuccess(val moviesDetail: MovieDetail) : MovieDetailAction()


}