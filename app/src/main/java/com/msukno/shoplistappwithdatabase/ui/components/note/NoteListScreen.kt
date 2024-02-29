package com.msukno.shoplistappwithdatabase.ui.components.note

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.data.note.Note
import com.msukno.shoplistappwithdatabase.ui.AppViewModelProvider
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListViewModel
import com.msukno.shoplistappwithdatabase.ui.navigation.NavigationDestination
import com.msukno.shoplistappwithdatabase.ui.theme.ShopListAppWithDatabaseTheme
import com.msukno.shoplistappwithdatabase.utils.Sorter
import kotlinx.coroutines.launch


object NoteListDestination : NavigationDestination {
    override val route = "note_list"
    override val titleRes = R.string.note_list_title
}


@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToAddNote: () -> Unit = {},
    navigateToEditNote: (Int) -> Unit = {},
    navigateBack: () -> Unit = {},
){
    val uiState = viewModel.noteListUiState.collectAsState()
    val noteList = uiState.value.noteList
    val comparator = compareBy<Note> { it.title.lowercase() }
        .thenByDescending { it.linkedItems.size }
        .thenByDescending { it.id }

    Column(
        modifier = Modifier
            .padding(
                dimensionResource(
                    id = R.dimen.padding_large
                )
            )
            .padding(top = 0.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        NoteListHeader(backToItems = navigateBack)

        Box(modifier = Modifier
            .fillMaxHeight(0.85f)
        ){
            ScrollableNoteList(
                noteList = Sorter.sortNotes(noteList, comparator),
                onCardClick = { navigateToEditNote(it.id) }
            )
        }
        Spacer(modifier = Modifier.height(
            dimensionResource(
                id = R.dimen.padding_medium))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallFloatingActionButton(
                onClick = { navigateToAddNote() },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    }
}



@Preview
@Composable
fun NoteListScreenPreview(){
    ShopListAppWithDatabaseTheme {
        NoteListScreen()
    }
}