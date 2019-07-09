package com.e.appmovie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.e.appmovie.api.model.Genre
import com.e.appmovie.api.model.Movie
import com.e.appmovie.api.model.MovieDetail
import com.google.gson.annotations.SerializedName

@Entity(tableName = "moviedetail")
data class MovieDetailRoom(

    @PrimaryKey
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "homepage") val homepage : String,
    @ColumnInfo(name = "original_title") val originalTitle : String,
    @ColumnInfo(name = "overview") val overview : String,
    @ColumnInfo(name = "poster_path") val posterPath : String,
    @ColumnInfo(name = "video") val video : Boolean

){
    fun mapToMovieDetailApi(): MovieDetail {
        return MovieDetail(id,homepage,originalTitle,overview,posterPath,video)
    }
}