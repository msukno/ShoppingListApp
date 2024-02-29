package com.msukno.shoplistappwithdatabase.ui.components.item

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.ui.ActionType
import com.msukno.shoplistappwithdatabase.ui.AppViewModelProvider
import com.msukno.shoplistappwithdatabase.ui.theme.ShopListAppWithDatabaseTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msukno.shoplistappwithdatabase.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ItemEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_item_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ItemEditScreen(
    viewModel: ItemEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit = {}
){
    val uiState = viewModel.itemUiState
    val coroutineScope = rememberCoroutineScope()
    var goBackConfirmation by rememberSaveable { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            )

    ){
        ItemHeader(
            title = stringResource(id = ItemEditDestination.titleRes)
        )
        Spacer(modifier = Modifier.height(
            dimensionResource(id = R.dimen.padding_large))
        )

        ItemInputFields(
            itemDetails = uiState.itemDetails,
            onValueChange = {viewModel.updateUiState(it)}
        )

        Spacer(modifier = Modifier.height(
            dimensionResource(id = R.dimen.padding_large))
        )

        ItemButtons(
            actionType = ActionType.Edit,
            onClickDelete = {
                coroutineScope.launch {
                    viewModel.deleteItem()
                    navigateBack()
                }
            },
            onClickSave = {
                coroutineScope.launch {
                    viewModel.updateItem()
                    navigateBack()
                }
            },
            isSaveEnabled = {viewModel.validateInput()},
            onClickBack = {
                if (viewModel.changesMade()) goBackConfirmation = true
                else navigateBack()
            }
        )
    }

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


@Composable
fun GoBackConfirmationDialog(
    onYesAction : () -> Unit = {},
    onNoAction : () -> Unit = {}
){
    Dialog(onDismissRequest = {}) {
        Card(
            shape = MaterialTheme.shapes.small
        ) {
            DialogOnChangeMessage(
                onClickYes = { onYesAction()},
                onClickNo = { onNoAction() }
            )
        }
    }
}

@Composable
fun DialogOnChangeMessage(
    onClickYes: () -> Unit,
    onClickNo: () -> Unit,
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            start = dimensionResource(
                id = R.dimen.padding_large
            ),
            end = dimensionResource(
                id = R.dimen.padding_large
            )
        )
    ){
        Text(
            text = stringResource(
                id = R.string.edit_item_popup_text
            ),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                dimensionResource(
                    id = R.dimen.padding_small
                )
            )
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { onClickYes() },
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        dimensionResource(
                            id = R.dimen.padding_small
                        )
                    )
            ) {
                Text(
                    text = "Yes",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            OutlinedButton(
                onClick = { onClickNo() },modifier = Modifier
                    .weight(1f)
                    .padding(
                        dimensionResource(
                            id = R.dimen.padding_small
                        )
                    )
            ) {
                Text(
                    text = "No",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}


@Preview
@Composable
fun ItemEditScreenPreview(){
    ShopListAppWithDatabaseTheme {
        ItemEditScreen()

    }
}