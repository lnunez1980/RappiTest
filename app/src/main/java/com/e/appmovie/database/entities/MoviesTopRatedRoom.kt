package com.e.appmovie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.e.appmovie.api.model.MovieTopRated

@Entity(tableName = "moviestoprated")
data class MoviesTopRatedRoom(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "genre_ids") val genre_ids: List<Int>,
    @ColumnInfo(name = "release_date") val releaseDate: String
) {

    fun mapToMovieApi(): MovieTopRated {
        return MovieTopRated(id, video, voteAverage, title, posterPath, originalTitle, genre_ids, releaseDate)
    }
}