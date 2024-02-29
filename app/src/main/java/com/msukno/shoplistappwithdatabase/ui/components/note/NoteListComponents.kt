package com.msukno.shoplistappwithdatabase.ui.components.note

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.note.Note

@Composable
fun NoteListHeader(
    backToItems: () -> Unit = {}
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()

    ){
        IconButton(onClick = { backToItems() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.width(
            dimensionResource(id = R.dimen.padding_small)
        ))
        Text(
            text = stringResource(NoteListDestination.titleRes),
            style = MaterialTheme.typography.labelLarge
        )

    }
}

@Composable
fun ScrollableNoteList(
    noteList: List<Note> = listOf(),
    onCardClick: (Note) -> Unit = {}
) {
    LazyColumn() {
        if (noteList.isNotEmpty()) {
            items(noteList) { note ->

                Box(modifier = Modifier.clickable {
                    onCardClick(note)
                }) {
                    NoteCard(
                        title = note.title,
                        note = note.text,
                        nLinkedItems = note.linkedItems.size.toString()
                    )
                }
                Spacer(modifier = Modifier.height(
                    dimensionResource(id = R.dimen.padding_medium)
                ))
            }
        }
    }
}

@Composable
fun NoteCard(
    title: String = "",
    note: String = "",
    nLinkedItems: String = ""
){

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(
            dimensionResource(id = R.dimen.noteCard_border),
            MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.noteCard_round_corner))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_small),
                        start = dimensionResource(id = R.dimen.padding_small)
                    )

                )
                Text(
                    text = "Note: $note",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_small)
                    )
                )
                Text(
                    text = "Linked items: $nLinkedItems",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_small)
                    )
                )
            }
        }
    }
}