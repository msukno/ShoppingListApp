package com.msukno.shoplistappwithdatabase.ui.components.note

import android.util.Log
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.ui.ActionType
import com.msukno.shoplistappwithdatabase.ui.AppViewModelProvider
import com.msukno.shoplistappwithdatabase.ui.components.item.GoBackConfirmationDialog
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemCard
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListViewModel
import com.msukno.shoplistappwithdatabase.ui.components.itempicker.ItemPickerScreen
import com.msukno.shoplistappwithdatabase.ui.navigation.NavigationDestination
import com.msukno.shoplistappwithdatabase.ui.theme.ShopListAppWithDatabaseTheme
import com.msukno.shoplistappwithdatabase.utils.Formatter
import kotlinx.coroutines.launch


object NoteEditDestination : NavigationDestination {
    override val route = "note_edit"
    override val titleRes = R.string.edit_note_title
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@Composable
fun NoteEditScreen(
    navController: NavHostController = rememberNavController(),
    vmNoteEdit: NoteEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    vmItems: ItemListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit = {}
) {

    val noteData = vmNoteEdit.noteUiState.noteData.copy()
    val itemListUiState = vmItems.itemListUiState.collectAsState()
    val itemList = itemListUiState.value.itemList.toList()
    val coroutineScope = rememberCoroutineScope()
    val linkedItems = itemList.filter { noteData.linkedItems[it.id] ?: false }
    var goBackConfirmation by rememberSaveable { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = "edit_form",
    ) {

        composable(route = "edit_form") {
            NoteForm(
                noteData = noteData,
                linkeditemList = linkedItems,
                formType = ActionType.Edit,
                updateNoteState = { vmNoteEdit.updateUiState(it) },
                saveNote = {
                    coroutineScope.launch {
                        vmNoteEdit.updateNote()
                    }
                    navigateBack()
                },
                deleteNote = {
                    coroutineScope.launch {
                        vmNoteEdit.deleteNote()
                    }
                    navigateBack()
                },
                isSaveEnabled = { vmNoteEdit.validateInput() },
                onBackClick = {
                    if (vmNoteEdit.changesMade()) goBackConfirmation = true
                    else navigateBack()
                },
                navigateToItemPicker = { navController.navigate("item_picker") }
            )

            if(goBackConfirmation){
                GoBackConfirmationDialog(
                    onYesAction = {
                        goBackConfirmation = false
                        navigateBack()
                    },
                    onNoAction = {
                        goBackConfirmation = false
                    }
                )
            }
        }

        composable(route = "item_picker"){
            ItemPickerScreen(
                noteData = noteData,
                itemList = itemList,
                updateNoteState = { vmNoteEdit.updateUiState(it) },
                navigateBack = {navController.navigate("edit_form")}
            )
        }

    }
}

@Composable
fun NoteForm(
    noteData: NoteData,
    linkeditemList: List<Item>,
    formType: ActionType = ActionType.Add,
    updateNoteState: (NoteData) -> Unit = {},
    saveNote: () -> Unit = {},
    deleteNote: () -> Unit = {},
    isSaveEnabled: () -> Boolean = {false},
    onBackClick: () -> Unit = {},
    navigateToItemPicker: () -> Unit = {}
){

    Column (
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            )

    ){
        NoteHeader(
            title = if (formType==ActionType.Add)
                stringResource(NoteAddDestination.titleRes)
            else
                stringResource(NoteEditDestination.titleRes)
        )
        NoteInputFields(
            noteData = noteData,
            onValueChange = { updateNoteState(it) }
        )
        NoteLinkedItems(
            itemList = linkeditemList,
            onAddClick = { navigateToItemPicker() }
        )

        Spacer(modifier = Modifier.height(
            dimensionResource(id = R.dimen.padding_medium)))

        NoteButtons(
            actionType = formType,
            onClickBack = onBackClick,
            onClickDelete = deleteNote,
            onClickSave = saveNote,
            isSaveEnabled = isSaveEnabled
        )
    }
}


@Preview
@Composable
fun NoteEditScreenPreview(){
    ShopListAppWithDatabaseTheme {
        NoteEditScreen()
    }
}