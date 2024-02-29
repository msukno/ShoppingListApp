package com.msukno.shoplistappwithdatabase

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.msukno.shoplistappwithdatabase.ui.navigation.ShopListNavGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListAppBar(){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_large)
                )
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(R.drawable.topbaricon),

                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_title),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    )
}

@Composable
fun ShopListApp(
    navController: NavHostController = rememberNavController(),
    setDarkMode: (Boolean) -> Unit = {},
    modeDark: Boolean = false
) {
    Scaffold(
        topBar = { ShopListAppBar() }
    ) { innerPadding ->

        ShopListNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            isDarkMode = modeDark,
            setDarkMode = setDarkMode
        )
    }
}