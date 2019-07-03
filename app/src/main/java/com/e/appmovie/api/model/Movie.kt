package com.e.appmovie.api.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id : Int,
    @SerializedName("video") val video : Boolean,
    @SerializedName("vote_average") val voteAverage : Double,
    @SerializedName("title") val title : String,
    @SerializedName("poster_path") val posterPath : String,
    @SerializedName("original_title") val originalTitle : String,
    @SerializedName("genre_ids") val genre_ids : List<Int>,
    @SerializedName("release_date") val releaseDate : String
)