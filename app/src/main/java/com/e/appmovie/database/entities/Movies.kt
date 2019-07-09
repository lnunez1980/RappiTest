package com.e.appmovie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.e.appmovie.api.model.Movie

@Entity(tableName = "movies")
data class Movies(
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

    fun mapToMovieApi(): Movie {
        return Movie(id, video, voteAverage, title, posterPath, originalTitle, genre_ids, releaseDate)
    }
}