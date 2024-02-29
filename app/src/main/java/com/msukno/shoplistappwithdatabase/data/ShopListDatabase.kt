package com.msukno.shoplistappwithdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.data.item.ItemDao
import com.msukno.shoplistappwithdatabase.data.note.Note
import com.msukno.shoplistappwithdatabase.data.note.NoteDao

@TypeConverters(Converters::class)
@Database(entities = [Item::class, Note::class], version = 4, exportSchema = false)
abstract class ShopListDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var Instance: ShopListDatabase? = null

        fun getDatabase(context: Context): ShopListDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, ShopListDatabase::class.java, "shop_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}