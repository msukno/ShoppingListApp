package com.msukno.shoplistappwithdatabase.ui.components.note

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.ui.ActionType
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemCard
import com.msukno.shoplistappwithdatabase.utils.Formatter

@Composable
fun NoteHeader(
    title: String = ""
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))

    ){
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )

    }
}

@Composable
fun NoteInputFields(
    noteData: NoteData,
    onValueChange: (NoteData) -> Unit = {}
){

    OutlinedTextField(
        value = noteData.title,
        onValueChange = { onValueChange(noteData.copy(title = it)) },
        label = { Text(
            text = "Title",
            style = MaterialTheme.typography.bodySmall
        ) },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
    Spacer(modifier = Modifier.height(
        dimensionResource(id = R.dimen.padding_small)
    )
    )

    OutlinedTextField(
        value = noteData.text,
        onValueChange = { onValueChange(noteData.copy(text = it)) },
        label = { Text(
            text = "Note",
            style = MaterialTheme.typography.bodySmall
        )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth(0.8f)
    )

    Spacer(modifier = Modifier.height(
        dimensionResource(id = R.dimen.padding_medium)
    )
    )
}

@Composable
fun NoteButtons(
    actionType: ActionType,
    onClickBack: () -> Unit = {},
    onClickDelete:() -> Unit = {},
    onClickSave:() -> Unit = {},
    isSaveEnabled: () -> Boolean = {false}
){
    val buttonPadding =
        if (actionType== ActionType.Edit)
            dimensionResource(
                id = R.dimen.padding_small
            )
        else
            dimensionResource(
                id = R.dimen.padding_large
            )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = buttonPadding,
                end = buttonPadding
            )
    ) {
        OutlinedButton(
            onClick = { onClickBack() },
            modifier = Modifier
                .weight(1f)
                .padding(
                    dimensionResource(id = R.dimen.padding_small)
                )
        ) {
            Text(
                text = "Back",
                style = MaterialTheme.typography.labelSmall
            )
        }

        if(actionType == ActionType.Edit){
            OutlinedButton(
                onClick = { onClickDelete() },
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        dimensionResource(id = R.dimen.padding_small)
                    )
            ) {
                Text(text = "Delete",
                    style = MaterialTheme.typography.labelSmall)
            }
        }

        OutlinedButton(
            onClick = { onClickSave() },
            enabled = isSaveEnabled(),
            modifier = Modifier
                .weight(1f)
                .padding(
                    dimensionResource(id = R.dimen.padding_small)
                )
        ) {
            Text(text = "Save",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun NoteLinkedItems(
    itemList: List<Item> = listOf(),
    onAddClick: () -> Unit = {}
){
    Box (
        modifier = Modifier
            .padding(2.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
            .padding(4.dp)

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(
                            id = R.dimen.padding_medium
                        )
                    )
            ){
                Text(
                    text = stringResource(R.string.linked_list_title),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Box (
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxHeight(0.65f)
            ){
                LinkedItemList(
                    itemList = itemList
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallFloatingActionButton(
                    onClick = { onAddClick() },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Filled.Add, "Small floating action button.")
                }
            }
        }
    }
}

@Composable
fun LinkedItemList(
    itemList: List<Item> = listOf(),
) {
    LazyColumn() {
        if (itemList.isNotEmpty()) {
            items(itemList) { item ->
                val itemAmount = Formatter.formatAmount(item.amount)

                ItemCard(
                    name = item.name,
                    quantity = itemAmount
                )

                Spacer(modifier = Modifier.height(
                    dimensionResource(id = R.dimen.padding_medium)
                ))
            }
        }
    }
}