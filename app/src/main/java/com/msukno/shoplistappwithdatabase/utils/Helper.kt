package com.msukno.shoplistappwithdatabase.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.data.note.Note
import com.msukno.shoplistappwithdatabase.ui.AppViewModelProvider
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemAddViewModel
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemDetails
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListDestination
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListViewModel
import com.msukno.shoplistappwithdatabase.ui.components.item.toItem
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteAddViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteData
import com.msukno.shoplistappwithdatabase.ui.components.note.toNote
import java.time.LocalDateTime

class Sorter {
    companion object {
        fun sortItems(items: List<Item>, comparator: Comparator<Item>): List<Item> {
            return items.sortedWith(comparator)
        }
        fun sortNotes(notes: List<Note>, comparator: Comparator<Note>): List<Note> {
            return notes.sortedWith(comparator)
        }
    }
}


class Formatter {
    companion object{
        fun formatAmount(amount: Double): String {
            val roundedAmount = "%.3f".format(amount)
            return if (roundedAmount.endsWith(".000")) {
                roundedAmount.substringBefore(".")
            } else {
                roundedAmount.trimEnd('0')
            }
        }
    }
}

class Validate {
    companion object{
        fun validateAmount(amount: String): Boolean {
            return if (amount == "") {
                true
            } else {
                try {
                    amount.toFloat()
                    true
                } catch (e: NumberFormatException) {
                    false
                }
            }
        }
    }
}
