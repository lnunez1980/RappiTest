package com.e.appmovie.ui.fragments.toprated

import androidx.lifecycle.*
import com.e.appmovie.NetworkStateHandler
import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.Movie
import com.e.appmovie.api.model.MovieTopRated
import com.e.appmovie.ui.fragments.models.TopRatedMoviesAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRatedMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesTopRatedRepository,
    private val networkStateHandler: NetworkStateHandler
) : ViewModel(), LifecycleObserver {

    val liveData = MutableLiveData<TopRatedMoviesAction>()
    private val compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (networkStateHandler.getNetworkStatus()) {
            compositeDisposable.add(getGenreApi())
            compositeDisposable.add(getMoviesApi())

        } else {
            compositeDisposable.add(getLocalGenres())
            compositeDisposable.add(getLocalMovies())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun filter(query : String): Disposable {
        return moviesRepository.filter(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = TopRatedMoviesAction.OnLoading(true)
            }
            .subscribe(this::OnFilterQueryFound, this::onError)
    }

    fun loadMoreData(): Disposable {
        return moviesRepository.getMoreApiMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = TopRatedMoviesAction.OnLoading(true)
            }
            .subscribe(this::onSuccess, this::onError)
    }

    private fun getGenreApi(): Disposable {
        return moviesRepository.getApiGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = TopRatedMoviesAction.OnLoading(true)
            }
            .subscribe(this::onSuccessGenre, this::onError)
    }

    private fun getLocalGenres(): Disposable {
        return moviesRepository.getLocalGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = TopRatedMoviesAction.OnLoading(true)
            }
            .subscribe(this::onSuccessGenre, this::onError)
    }

    private fun getMoviesApi(): Disposable {
        return moviesRepository.getApiMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = TopRatedMoviesAction.OnLoading(true)
            }
            .subscribe(this::onSuccess, this::onError)
    }

    private fun getLocalMovies(): Disposable {
        return moviesRepository.getLocalMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = TopRatedMoviesAction.OnLoading(true)
            }
            .subscribe(this::onSuccess, this::onError)
    }

    private fun OnFilterQueryFound(movies: List<MovieTopRated>){
        liveData.value = TopRatedMoviesAction.OnFilterQueryFound(movies)
        liveData.value = TopRatedMoviesAction.OnLoading(false)
    }
    private fun onSuccess(movies: List<MovieTopRated>) {
        liveData.value = TopRatedMoviesAction.OnSuccess(movies)
        liveData.value = TopRatedMoviesAction.OnLoading(false)
    }

    private fun onSuccessGenre(genre: List<Genre>) {
        liveData.value = TopRatedMoviesAction.OnSuccessGenre(genre)
        liveData.value = TopRatedMoviesAction.OnLoading(false)
    }

    private fun onError(throwable: Throwable) {
        liveData.value = TopRatedMoviesAction.OnError(throwable)
        liveData.value = TopRatedMoviesAction.OnLoading(false)
    }
}