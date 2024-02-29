package com.msukno.shoplistappwithdatabase.data.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.msukno.shoplistappwithdatabase.data.Converters
import java.time.Instant

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(Converters::class)
    val dateTime : Instant,
    val title: String,
    val text: String,
    @TypeConverters(Converters::class)
    val linkedItems: List<Int>
)
