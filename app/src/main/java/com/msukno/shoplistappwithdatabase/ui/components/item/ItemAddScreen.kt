package com.msukno.shoplistappwithdatabase.ui.components.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.ui.ActionType
import com.msukno.shoplistappwithdatabase.ui.AppViewModelProvider
import com.msukno.shoplistappwithdatabase.ui.navigation.NavigationDestination
import com.msukno.shoplistappwithdatabase.ui.theme.ShopListAppWithDatabaseTheme
import kotlinx.coroutines.launch

object ItemAddDestination : NavigationDestination {
    override val route = "item_add"
    override val titleRes = R.string.add_item_title
}

@Composable
fun ItemAddScreen(
    viewModel: ItemAddViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit = {},
){
    
    val itemState = viewModel.itemUiState
    val coroutineScope = rememberCoroutineScope()

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
        ItemHeader(title = stringResource(ItemAddDestination.titleRes))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
        ItemInputFields(
            itemState.itemDetails,
            onValueChange = {viewModel.updateUiState(it)}
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
        ItemButtons(
            actionType = ActionType.Add,
            onClickBack = navigateBack,
            onClickSave = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            isSaveEnabled = {viewModel.validateInput()}
        )
    }
}

@Preview
@Composable
fun ItemAddScreenPreview(){
    ShopListAppWithDatabaseTheme {
        ItemAddScreen()
    }
}