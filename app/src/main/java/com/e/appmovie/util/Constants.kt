package com.e.appmovie.util

import com.e.appmovie.api.model.Genre
import java.util.*

object Constants{
    var listGenres : List<Genre> = emptyList()
    val apiKey = "04d2532621aacf241e45f8e0822f8db0"
    val language: String = Locale.getDefault().toString().replace("_", "-")
    const val image_url = "https://image.tmdb.org/t/p/w300"
    var movieId: String = ""
}