package com.msukno.shoplistappwithdatabase.ui.components.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.data.item.ItemsRepo
import com.msukno.shoplistappwithdatabase.ui.components.note.CacheData
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteData
import com.msukno.shoplistappwithdatabase.utils.Formatter
import java.time.Instant
import java.time.LocalDateTime


/**
 * ViewModel to validate and insert items in the database.
 */
class ItemAddViewModel(
    private val itemsRepository: ItemsRepo
) : ViewModel() {


    var itemUiState by mutableStateOf(ItemUiState())
        private set


    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails)
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
     * Inserts an [Item] in the database if the input is valid.
     */
    suspend fun saveItem() {
        if (validateInput()){
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }
}

/**
 * Represents UI State for an Item.
 */
data class ItemUiState(
    var itemDetails: ItemDetails = ItemDetails(),
)

/**
 * Represents the details of an Item.
 */
data class ItemDetails(
    val id: Int = 0,
    val dateTime: Instant = Instant.now(),
    val name: String = "",
    val amount: String = ""
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.amount] is
 * not a valid [Double], then the amount will be set to 0.0.
 */
fun ItemDetails.toItem(): Item = Item(
    id = id,
    dateTime= dateTime,
    name = name,
    amount = amount.toDoubleOrNull() ?: 0.0
)

/**
 * Extension function to convert [Item] to [ItemUiState].
 */
fun Item.toItemUiState(): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails()
)

/**
 * Extension function to convert [Item] to [ItemDetails].
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    dateTime = dateTime,
    name = name,
    amount = amount.toString()
)

/**
 * Represents a cache of an Item. Used to determine if the user has made some changes.
 */
data class ItemCache(
    val name: String = "",
    val amount: String = "",
)

/**
 * Extension function to convert [ItemDetails] to [ItemCache].
 */
fun ItemDetails.toItemCache(): ItemCache = ItemCache(
    name = name,
    amount = amount
)
