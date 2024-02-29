package com.msukno.shoplistappwithdatabase.ui.components.item

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteListViewModel
import com.msukno.shoplistappwithdatabase.ui.components.note.toNoteData
import com.msukno.shoplistappwithdatabase.ui.navigation.NavigationDestination
import com.msukno.shoplistappwithdatabase.ui.theme.ShopListAppWithDatabaseTheme
import com.msukno.shoplistappwithdatabase.utils.Formatter
import com.msukno.shoplistappwithdatabase.utils.Sorter
import kotlinx.coroutines.launch

object ItemListDestination : NavigationDestination {
    override val route = "item_list"
    override val titleRes = R.string.item_list_title
}

@Composable
fun ItemListScreen(
    viewModel: ItemListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    vmNotes: NoteListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToEditItem: (Int) -> Unit = {},
    navigateToAddItem: () -> Unit = {},
    navigateToNotes: () -> Unit = {},
    setDarkMode: (Boolean) -> Unit = {},
    isDarkMode: Boolean = false

    ){
    val uiState = viewModel.itemListUiState.collectAsState()
    val noteListState = vmNotes.noteListUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val itemList = uiState.value.itemList

    Log.d("mytag2", "size: ${itemList.size}")
    val comparator = compareBy<Item> { it.name.lowercase() }
        .thenByDescending { it.id }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_large))
            .fillMaxSize()
    ){

        ItemListHeader(
            isDarkMode = isDarkMode,
            setDarkMode = setDarkMode
        )
        ItemListBody(
            items = Sorter.sortItems(itemList, comparator),
            navigateToItemEdit = navigateToEditItem,
            navigateToItemAdd = navigateToAddItem
        )
        Spacer(modifier = Modifier.weight(1f))
        ItemListButtons(
            countItems = {uiState.value.itemList.size},
            navigateToNotes = {
                val ids = uiState.value.itemList.map { it.id }.toSet()
                val noteList = noteListState.value.noteList
                noteList.forEach { note ->
                    val linkedItems = note.linkedItems.toSet()
                    val updatedLinkedItems = linkedItems.filter { it in ids }.toSet()
                    if(linkedItems != updatedLinkedItems){
                        coroutineScope.launch {
                            vmNotes.updateNote(
                                note.copy(
                                    linkedItems = updatedLinkedItems.toList()
                                ).toNoteData()
                            )
                        }
                    }
                }
                navigateToNotes()
            }
        )
    }
}

@Preview
@Composable
fun ItemListScreenPreview(){
    ShopListAppWithDatabaseTheme {
        ItemListScreen()
    }
}


