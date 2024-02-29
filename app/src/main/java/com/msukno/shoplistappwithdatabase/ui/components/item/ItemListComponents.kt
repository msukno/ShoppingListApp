package com.msukno.shoplistappwithdatabase.ui.components.item

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.utils.Formatter
import com.msukno.shoplistappwithdatabase.utils.Sorter

@Composable
fun ItemListHeader(
    isDarkMode: Boolean = false,
    setDarkMode: (Boolean) -> Unit = {}
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(
                    id = R.dimen.padding_medium
                ),
                bottom = dimensionResource(
                    id = R.dimen.padding_small
                ),
                end = dimensionResource(
                    id = R.dimen.padding_medium
                )
            )
    ){
        Text(
            text = stringResource(ItemListDestination.titleRes),
            style = MaterialTheme.typography.labelLarge
        )

        Switch(
            checked = isDarkMode,
            onCheckedChange = {
                setDarkMode(it)
            }
        )


    }
}

@Composable
fun ItemListBody(
    items: List<Item>,
    navigateToItemEdit: (Int) -> Unit = {},
    navigateToItemAdd: () -> Unit = {}
){
    val comparator = compareBy<Item> { it.name.lowercase() }
        .thenByDescending { it.id }
    Box(
        modifier = Modifier
            .fillMaxHeight(0.7f)
    ){
        ScrollableItemList(
            itemList = Sorter.sortItems(items, comparator),
            itemClicked = {navigateToItemEdit(it.id)}
        )
    }
    Spacer(modifier = Modifier.height(
        dimensionResource(
            id = R.dimen.padding_medium)
    )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallFloatingActionButton(
            onClick = { navigateToItemAdd() },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ) {
            Icon(Icons.Filled.Add, "Small floating action button.")
        }
    }
}

@Composable
fun ItemListButtons(
    countItems: () -> Int = {0},
    navigateToNotes: ()->Unit = {}
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Added items:  ${countItems()}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
        )

        Button(
            onClick = { navigateToNotes()},
        ) {
            Text("Notes",
                style = MaterialTheme.typography.labelSmall)
        }

    }
}


@Composable
fun ScrollableItemList(
    itemList: List<Item> = listOf(),
    itemClicked: (Item) -> Unit = {}
) {
    LazyColumn() {
        if (itemList.isNotEmpty()) {
            items(itemList) { item ->
                val itemAmount = Formatter.formatAmount(item.amount)

                Box(modifier = Modifier.clickable {
                    itemClicked(item)
                }) {
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



@Composable
fun ItemCard(
    name: String = "",
    quantity: String = ""
){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(
                id = R.dimen.card_elevation)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_small),
                        start = dimensionResource(id = R.dimen.padding_small)
                    )

                )
                Text(
                    text = "Quantity: $quantity",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_small)
                    )
                )
            }
        }
    }
}