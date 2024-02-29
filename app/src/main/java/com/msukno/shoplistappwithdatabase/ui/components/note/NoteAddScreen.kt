package com.msukno.shoplistappwithdatabase.ui.components.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.ui.ActionType
import com.msukno.shoplistappwithdatabase.ui.AppViewModelProvider
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListViewModel
import com.msukno.shoplistappwithdatabase.ui.components.itempicker.ItemPickerScreen
import com.msukno.shoplistappwithdatabase.ui.navigation.NavigationDestination
import com.msukno.shoplistappwithdatabase.ui.theme.ShopListAppWithDatabaseTheme
import kotlinx.coroutines.launch


object NoteAddDestination : NavigationDestination {
    override val route = "note_add"
    override val titleRes = R.string.add_note_title

}

@Composable
fun NoteAddScreen(
    navController: NavHostController = rememberNavController(),
    vmNoteAdd: NoteAddViewModel = viewModel(factory = AppViewModelProvider.Factory),
    vmItems: ItemListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit = {}
){
    val noteData = vmNoteAdd.noteUiState.noteData.copy()
    val itemListUiState = vmItems.itemListUiState.collectAsState()
    val itemList = itemListUiState.value.itemList.toList()
    val coroutineScope = rememberCoroutineScope()
    val linkedItems = itemList.filter { noteData.linkedItems[it.id] ?: false }
    NavHost(
        navController = navController,
        startDestination = "add_form",
    ){

        composable(route = "add_form"){
            NoteForm(
                noteData = noteData,
                linkeditemList = linkedItems,
                formType = ActionType.Add,
                updateNoteState = { vmNoteAdd.updateUiState(it) },
                saveNote = {
                    coroutineScope.launch {
                        vmNoteAdd.saveNote()
                    }
                    navigateBack()
                },
                isSaveEnabled = { vmNoteAdd.validateInput() },
                onBackClick = navigateBack,
                navigateToItemPicker = { navController.navigate("item_picker") }
            )
        }

        composable(route = "item_picker"){
            ItemPickerScreen(
                noteData = noteData,
                itemList = itemList,
                updateNoteState = { vmNoteAdd.updateUiState(it) },
                navigateBack = {navController.navigate("add_form")}
            )
        }
    }
}


@Preview
@Composable
fun NoteAddScreenPreview(){
    ShopListAppWithDatabaseTheme {
        NoteAddScreen()
    }
}