package com.e.appmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.e.appmovie.api.MoviesApi
import com.e.appmovie.api.model.Movie
import com.e.appmovie.repository.MoviesRepository

class PopularMoviesViewModel(moviesApi: MoviesApi, isConnected : Boolean) : ViewModel(){

    var moviesRepository : MoviesRepository = MoviesRepository(moviesApi,isConnected)


    fun getData(page:Int) {
        moviesRepository.getData(page)

    }
    fun filter(txt: String)  {
        return moviesRepository.filter(txt)
    }
        fun isLoading() : Boolean{
        return moviesRepository.isLoading
    }
    fun getMovies(): LiveData<List<Movie>> {
        return moviesRepository.getMovies()
    }
    fun resetMovies() {
        moviesRepository.resetMovies()
    }
    fun dispose(){
        moviesRepository.dispose()
    }

    override fun onCleared() {
        dispose()
        super.onCleared()

    }
}