package com.msukno.shoplistappwithdatabase.ui.components.itempicker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemCard
import com.msukno.shoplistappwithdatabase.utils.Formatter

@Composable
fun PickerHeader(
    searchState: String = "",
    title: String = "",
    updateSearch: (String) -> Unit = {},
    backClicked: () -> Unit = {}
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(
                    id = R.dimen.padding_small
                )
            )

    ){
        IconButton(onClick = { backClicked() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.width(
            dimensionResource(id = R.dimen.padding_small)
        ))
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.width(
            dimensionResource(id = R.dimen.padding_medium)
        ))

        TextField(
            value = searchState,
            onValueChange = { updateSearch(it)},
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

    }
}

@Composable
fun CheckedItemList(
    itemList: List<Item> = listOf(),
    isChecked: (Int) -> Boolean = {false},
    updateLinkedItems: (Int, Boolean) -> Unit = {_,_->}
) {
    LazyColumn() {
        if (itemList.isNotEmpty()) {
            items(itemList) { item ->
                val itemAmount = Formatter.formatAmount(item.amount)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            dimensionResource(
                                id = R.dimen.padding_small
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked(item.id),
                        onCheckedChange = { updateLinkedItems(item.id, it)}
                    )
                    ItemCard(
                        name = item.name,
                        quantity = itemAmount
                    )
                }

                Spacer(modifier = Modifier.height(
                    dimensionResource(id = R.dimen.padding_medium)
                ))
            }
        }
    }
}