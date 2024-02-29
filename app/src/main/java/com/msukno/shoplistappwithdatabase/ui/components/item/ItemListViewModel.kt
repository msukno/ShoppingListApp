package com.msukno.shoplistappwithdatabase.ui.components.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.data.item.ItemsRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to fetch and manage the list of items from the ItemsRepo.
 */
class ItemListViewModel(itemsRepository: ItemsRepo) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    /**
     * Holds the current list of items in the UI state.
     * It fetches all items from the ItemsRepo and maps them to an ItemListUiState.
     */
    val itemListUiState: StateFlow<ItemListUiState> =
        itemsRepository.getAllItemsStream().map { ItemListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemListUiState()
            )
}

/**
 * Represents UI State for a list of Items.
 */
data class ItemListUiState(val itemList: List<Item> = listOf())
