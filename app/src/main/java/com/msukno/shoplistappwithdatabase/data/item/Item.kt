package com.msukno.shoplistappwithdatabase.data.item

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.msukno.shoplistappwithdatabase.data.Converters
import java.time.Instant

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(Converters::class)
    val dateTime : Instant,
    val name: String,
    val amount: Double
)