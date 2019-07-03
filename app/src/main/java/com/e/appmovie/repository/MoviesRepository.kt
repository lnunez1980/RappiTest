package com.e.appmovie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.e.appmovie.api.MoviesApi
import com.e.appmovie.api.model.Movie
import com.e.appmovie.database.model.Movies
import com.e.appmovie.di.BaseApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import java.util.*

class MoviesRepository(var moviesApi: MoviesApi, var isConnected : Boolean){

    private var responseMovies : MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    private var moviesList : MutableList<Movie> = mutableListOf<Movie>()
    private var moviesListRoom : MutableList<Movie> = mutableListOf<Movie>()
    private var moviesListFilter : MutableList<Movie> = mutableListOf<Movie>()
    private var moviesListTemp : MutableList<Movie> = mutableListOf<Movie>()
    private var disposable : Disposable? = null

    private val API_KEY = "04d2532621aacf241e45f8e0822f8db0"
    private val LANGUAGE : String = Locale.getDefault().toString().replace("_","-")
    var isLoading: Boolean = false

    fun filter(txt: String) {
        resetList()
        if (txt.isNotEmpty()){
            moviesListTemp = moviesList
            moviesListFilter = moviesList.filter { mfilter ->
                mfilter.originalTitle.contains(txt, true) }.toMutableList()
            responseMovies.value = moviesListFilter
        }else if (txt.isEmpty()){
            moviesList = moviesListTemp
            responseMovies.value = moviesList
            resetList()
        }
    }
    fun getData(page:Int) {
        if(isConnected){
            disposable = moviesApi
                .getPopularMovies(API_KEY,LANGUAGE,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {moviesList -> moviesList.results.sortedByDescending {movies -> movies.voteAverage }}
                .doOnSubscribe {
                    this.isLoading = true
                }
                .subscribe({
                        dataList -> setMovies(dataList)
                        copyDataLocal(dataList.toList(),page)
                        this.isLoading = false
                },{},{this.isLoading = false}
                )
        }else{
            disposable =  BaseApplication.database.moviesDao()
                .getMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {moviesList -> moviesList.sortedByDescending {movies -> movies.voteAverage }}
                .doOnSubscribe {
                      this.isLoading = true
                }
                .subscribe({dataList ->
                      moviesListRoom = emptyList<Movie>().toMutableList()
                      for(movie in dataList){
                          val moviesRomm = Movie(movie.id,movie.video,movie.voteAverage,movie.title,movie.posterPath,movie.originalTitle,movie.genre_ids,movie.releaseDate)
                          moviesListRoom.add(moviesRomm)
                      }
                      setMovies(moviesListRoom)
                      this.isLoading = false
                },{},{this.isLoading = false})


        }

    }
    fun copyDataLocal(movie : List<Movie>,page: Int){
        doAsync {
            for (movie in movie.iterator()){
                val movies = Movies(movie.id,page,movie.video,movie.voteAverage,movie.title,movie.posterPath,movie.originalTitle,movie.genre_ids,movie.releaseDate)
                BaseApplication.database.moviesDao().addMovies(movies)
            }
        }
    }
    fun getMovies(): LiveData<List<Movie>> {
        return responseMovies
    }
    private fun setMovies(movies: List<Movie>) {
        moviesList.addAll(movies)
        responseMovies.value = moviesList
    }
    fun resetMovies() {
        moviesList = emptyList<Movie>().toMutableList()
        responseMovies.value = emptyList<Movie>().toMutableList()
    }
    fun dispose(){
        disposable!!.dispose()
    }
    private fun resetList(){
        moviesListFilter = emptyList<Movie>().toMutableList()
        moviesListTemp = emptyList<Movie>().toMutableList()
    }

}