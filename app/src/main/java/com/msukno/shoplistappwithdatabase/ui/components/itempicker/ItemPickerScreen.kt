package com.msukno.shoplistappwithdatabase.ui.components.itempicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.ui.AppViewModelProvider
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemCard
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteAddViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteData
import com.msukno.shoplistappwithdatabase.ui.navigation.NavigationDestination
import com.msukno.shoplistappwithdatabase.ui.theme.ShopListAppWithDatabaseTheme
import com.msukno.shoplistappwithdatabase.utils.Formatter
import com.msukno.shoplistappwithdatabase.utils.Sorter


object ItemPickerDestination : NavigationDestination {
    override val route = "item_list"
    override val titleRes = R.string.item_picker_title
}

@Composable
fun ItemPickerScreen(
    noteData: NoteData,
    itemList: List<Item>,
    updateNoteState: (NoteData) -> Unit = {},
    navigateBack: () -> Unit = {}
){

    val linkedIndices = remember { mutableStateMapOf<Int, Boolean>() }
    var searchInput by remember { mutableStateOf("") }
    linkedIndices.apply { putAll(noteData.linkedItems) }

    val comparator = compareBy<Item> { it.name.lowercase() }
        .thenByDescending { it.dateTime }
        .thenByDescending { it.id }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxSize()
    ){
        PickerHeader(
            title = stringResource(ItemPickerDestination.titleRes),
            searchState = searchInput,
            backClicked = {
                updateNoteState(noteData.copy(
                    linkedItems = linkedIndices.toMap())
                )
                linkedIndices.clear()
                navigateBack()
            },
            updateSearch = { searchInput = it}
        )
        Spacer(modifier = Modifier.height(
            dimensionResource(id = R.dimen.padding_medium)
        ))
        CheckedItemList(
            itemList = Sorter.sortItems(
                itemList.filter { it.name.startsWith(searchInput) },
                comparator
            ),
            isChecked = { linkedIndices[it] ?: false },
            updateLinkedItems = { id, change -> linkedIndices[id] = change }
        )
    }
}


@Preview
@Composable
fun ItemPickerScreenPreview(){
    ShopListAppWithDatabaseTheme {
        ItemPickerScreen(NoteData(), listOf())
    }
}
