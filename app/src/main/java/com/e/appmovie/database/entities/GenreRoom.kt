package com.e.appmovie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.e.appmovie.api.model.Genre


@Entity(tableName = "genres")
data class GenreRoom(
    @PrimaryKey
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "name") val name : String
){
    fun mapToApiGenre(): Genre {
        return Genre(id, name)
    }
}