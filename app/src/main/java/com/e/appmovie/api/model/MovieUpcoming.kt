package com.e.appmovie.api.model

import com.e.appmovie.database.entities.MovieUpcomingRoom
import com.google.gson.annotations.SerializedName

data class MovieUpcoming(
    @SerializedName("id") val id: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("release_date") val releaseDate: String
) {

    fun mapToRoomMovieUpcoming(): MovieUpcomingRoom {
        return MovieUpcomingRoom(id, 1, video, voteAverage, title, posterPath, originalTitle, genre_ids, releaseDate)
    }
}