package com.e.appmovie.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun gettingListFromString(genreIds: String): List<Int> {
        val list: MutableList<Int> = emptyList<Int>().toMutableList()

        val array = genreIds.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (s in array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s))
            }
        }
        return list
    }

    @TypeConverter
    fun writingStringFromList(list: List<Int>): String {
        var genreIds = ""
        for (i in list) {
            genreIds += ",$i"
        }
        return genreIds
    }
}