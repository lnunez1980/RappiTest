package com.e.appmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.appmovie.api.MoviesApi
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val moviesApi: MoviesApi, val isConnected : Boolean): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)) {
            PopularMoviesViewModel(this.moviesApi,this.isConnected) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}