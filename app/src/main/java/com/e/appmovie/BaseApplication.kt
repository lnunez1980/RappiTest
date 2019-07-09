package com.e.appmovie

import com.e.appmovie.di.component.DaggerAppComponent
import com.e.appmovie.util.ImageBinder
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        ImageBinder.init(this)
    }
}