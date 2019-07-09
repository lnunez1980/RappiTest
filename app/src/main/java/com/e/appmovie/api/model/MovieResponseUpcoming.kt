package com.e.appmovie.api.model

import com.google.gson.annotations.SerializedName

class MovieResponseUpcoming (
    @SerializedName("page") val page : Int,
    @SerializedName("results") val results : List<MovieUpcoming>
)