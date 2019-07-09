package com.e.appmovie.util

import android.app.Application

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.e.appmovie.util.Constants.image_url
import kotlin.properties.Delegates

object ImageBinder {

    private var glide: RequestManager by Delegates.notNull()


    fun init(application: Application) {
        glide = Glide.with(application)
    }

    fun load(imageView: ImageView, path: String) {
        glide.load(image_url + path)
            .transform(RoundedCorners(16))
            .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
            .into(imageView)
    }


}