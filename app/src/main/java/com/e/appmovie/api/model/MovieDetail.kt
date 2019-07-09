package com.e.appmovie.api.model

import com.e.appmovie.database.entities.MovieDetailRoom
import com.e.appmovie.database.entities.Movies
import com.google.gson.annotations.SerializedName

data class MovieDetail(

    @SerializedName("id") val id : Int,
    @SerializedName("homepage") val homepage : String,
    @SerializedName("original_title") val originalTitle : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("poster_path") val posterPath : String,
    @SerializedName("video") val video : Boolean
){
    fun mapToMovieDetailRoom(): MovieDetailRoom {
        return MovieDetailRoom(id,homepage,originalTitle,overview,posterPath,video)
    }
}