package com.e.appmovie

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStateHandler @Inject constructor(private val application: Application) {

    private val connectivityManager by lazy {
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun getNetworkStatus(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return (networkInfo != null && networkInfo.detailedState == NetworkInfo.DetailedState.CONNECTED)
    }
}