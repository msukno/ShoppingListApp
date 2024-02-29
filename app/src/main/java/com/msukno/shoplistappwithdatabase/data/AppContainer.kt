package com.msukno.shoplistappwithdatabase.data

import android.content.Context
import com.msukno.shoplistappwithdatabase.data.item.ItemsRepo
import com.msukno.shoplistappwithdatabase.data.item.OfflineItemsRepo
import com.msukno.shoplistappwithdatabase.data.note.NotesRepo
import com.msukno.shoplistappwithdatabase.data.note.OfflineNotesRepo

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepo
    val notesRepository: NotesRepo
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepo]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepo]
     */
    override val itemsRepository: ItemsRepo by lazy {
        OfflineItemsRepo(ShopListDatabase.getDatabase(context).itemDao())
    }

    override val notesRepository: NotesRepo by lazy {
        OfflineNotesRepo(ShopListDatabase.getDatabase(context).noteDao())
    }
}