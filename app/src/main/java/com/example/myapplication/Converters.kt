package com.example.myapplication

import androidx.room.TypeConverter
import java.util.stream.Collectors

class Converters {

    @TypeConverter
    fun fromArrayList(photos: ArrayList<String>): String {
        return photos.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toArrayList(photos: String): ArrayList<String> {
        return ArrayList(photos.split(","))
    }
}
