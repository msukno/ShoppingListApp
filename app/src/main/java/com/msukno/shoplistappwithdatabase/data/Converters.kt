package com.msukno.shoplistappwithdatabase.data

import androidx.room.TypeConverter
import java.time.Instant

class Converters {

    @TypeConverter
    fun fromTimestamp(dateTime: Instant): String{
        return dateTime.toString()
    }

    @TypeConverter
    fun fromText(dateTime: String): Instant{
        return Instant.parse(dateTime)
    }

    @TypeConverter
    fun fromList(linkedItems: List<Int>): String {
        return linkedItems.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromString(linkedItems: String): List<Int> {
        return if(linkedItems.any { it.isDigit() })
            linkedItems.split(",").map { it.trim().toInt() }
        else
            listOf()
    }
}
