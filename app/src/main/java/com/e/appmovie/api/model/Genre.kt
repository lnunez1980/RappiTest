package com.e.appmovie.api.model

import com.e.appmovie.database.entities.GenreRoom
import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
){
    fun mapToRoomGenre(): GenreRoom {
        return GenreRoom(id, name)
    }
}


