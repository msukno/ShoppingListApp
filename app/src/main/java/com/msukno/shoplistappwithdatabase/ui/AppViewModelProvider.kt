package com.msukno.shoplistappwithdatabase.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.msukno.shoplistappwithdatabase.ShopListApplication
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemAddViewModel
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemEditViewModel
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteAddViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteEditViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for ItemListViewModel
        initializer {
            ItemListViewModel(shopListApplication().container.itemsRepository)
        }

        // Initializer for NoteListViewModel
        initializer {
            NoteListViewModel(shopListApplication().container.notesRepository)
        }

        // Initializer for ItemAddViewModel
        initializer {
            ItemAddViewModel(shopListApplication().container.itemsRepository)
        }

        // Initializer for NoteAddViewModel
        initializer {
            NoteAddViewModel(shopListApplication().container.notesRepository)
        }

        // Initializer for ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                shopListApplication().container.itemsRepository
            )
        }

        // Initializer for NoteEditViewModel
        initializer {
            NoteEditViewModel(
                this.createSavedStateHandle(),
                shopListApplication().container.notesRepository
            )
        }
    }
}

fun CreationExtras.shopListApplication(): ShopListApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ShopListApplication)
