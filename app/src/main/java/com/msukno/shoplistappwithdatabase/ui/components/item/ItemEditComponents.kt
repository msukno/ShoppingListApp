package com.msukno.shoplistappwithdatabase.ui.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.msukno.shoplistappwithdatabase.R
import com.msukno.shoplistappwithdatabase.ui.ActionType
import com.msukno.shoplistappwithdatabase.utils.Validate

@Composable
fun ItemHeader(
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
fun ItemInputFields(
    itemDetails: ItemDetails,
    onValueChange: (ItemDetails) -> Unit = {}
){

    OutlinedTextField(
        value = itemDetails.name,
        onValueChange = {onValueChange(itemDetails.copy(name=it))},
        label = { Text(
            text = "Name",
            style = MaterialTheme.typography.bodySmall
        ) },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
    Spacer(modifier = Modifier.height(
        dimensionResource(id = R.dimen.padding_medium))
    )
    OutlinedTextField(
        value = itemDetails.amount,
        onValueChange = {
            if(Validate.validateAmount(it)) {
                onValueChange(itemDetails.copy(amount = it))
            }
        },
        label = {
            Text(text = "Amount", style = MaterialTheme.typography.bodySmall)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun ItemButtons(
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