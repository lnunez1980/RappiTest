package com.e.appmovie.ui.fragments.detail

import androidx.lifecycle.*
import com.e.appmovie.NetworkStateHandler
import com.e.appmovie.api.model.MovieDetail
import com.e.appmovie.ui.fragments.models.MovieDetailAction
import com.e.appmovie.util.Constants.movieId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailMoviesViewModel @Inject constructor(
    private val moviesRepository: DetailMoviesRepository,
    private val networkStateHandler: NetworkStateHandler
) : ViewModel(), LifecycleObserver {

    val liveData = MutableLiveData<MovieDetailAction>()
    private val compositeDisposable = CompositeDisposable()
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (networkStateHandler.getNetworkStatus()) {
            compositeDisposable.add(getMovieDetailApi())
        } else {
            compositeDisposable.add(getMovieDetailLocal())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        compositeDisposable.clear()
    }

    private fun getMovieDetailApi(): Disposable {
        return moviesRepository.getApiMoviesDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = MovieDetailAction.OnLoading(true)
            }
            .subscribe(this::onSuccess, this::onError)
    }

    private fun getMovieDetailLocal(): Disposable {
        return moviesRepository.getLocalMoviesDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveData.value = MovieDetailAction.OnLoading(true)
            }
            .subscribe(this::onSuccess, this::onError)
    }

    private fun onSuccess(moviesDetail: MovieDetail) {
        liveData.value = MovieDetailAction.OnSuccess(moviesDetail)
        liveData.value = MovieDetailAction.OnLoading(false)
    }
    private fun onError(throwable: Throwable) {
        liveData.value = MovieDetailAction.OnError(throwable)
        liveData.value = MovieDetailAction.OnLoading(false)
    }
}