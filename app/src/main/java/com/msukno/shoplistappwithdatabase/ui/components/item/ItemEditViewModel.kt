package com.msukno.shoplistappwithdatabase.ui.components.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msukno.shoplistappwithdatabase.data.item.ItemsRepo
import com.msukno.shoplistappwithdatabase.ui.components.note.CacheData
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteEditViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.toCacheData
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


/**
 * ViewModel to validate and update items in the database.
 */
class ItemEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepo
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private var cache = ItemCache()
    }

    /**
     * Holds current item UI state.
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Fetch the ID of the item being edited.
     */
    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    /**
     * Initializes the item UI state with the item being edited and caches the initial item details.
     */
    init{
        viewModelScope.launch {
            itemUiState = itemsRepository.getItemStream(itemId)
                .filterNotNull()
                .first()
                .toItemUiState()

            cache = itemUiState.itemDetails.toItemCache()
        }
    }

    /**
     * Updates the [itemUiState] with the value provided in the argument.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState = ItemUiState(itemDetails = itemDetails)
    }

    /**
     * Deletes an [Item] from the database.
     */
    suspend fun deleteItem(){
        itemsRepository.deleteItem(itemUiState.itemDetails.toItem())
    }

    /**
     * Updates an [Item] in the database.
     */
    suspend fun updateItem(){
        itemsRepository.updateItem(itemUiState.itemDetails.toItem())
    }

    /**
     * Validates the input values. Returns true if the name and amount are not blank and the amount is a digit.
     */
    fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && amount.isNotBlank()
                    && amount.count { it.isDigit() } > 0
        }
    }

    /**
     * Checks if any changes have been made to the item details.
     */
    fun changesMade(): Boolean = cache != itemUiState.itemDetails.toItemCache()
}
