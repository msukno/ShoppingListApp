package com.msukno.shoplistappwithdatabase.ui.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemAddDestination
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemAddScreen
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemEditDestination
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemEditScreen
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListDestination
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemListScreen
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteAddDestination
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteAddScreen
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteEditDestination
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteEditScreen
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteListDestination
import com.msukno.shoplistappwithdatabase.ui.components.note.NoteListScreen


@Composable
fun ShopListNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = false,
    setDarkMode: (Boolean) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = ItemListDestination.route,
        modifier = modifier
    ) {

        composable(route = ItemListDestination.route) {
            ItemListScreen(
                navigateToAddItem = { navController.navigate(ItemAddDestination.route) },
                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                navigateToNotes = { navController.navigate(NoteListDestination.route) },
                isDarkMode = isDarkMode,
                setDarkMode = setDarkMode
            )
        }
        composable(route = NoteListDestination.route) {
            NoteListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToAddNote = { navController.navigate(NoteAddDestination.route) },
                navigateToEditNote = { navController.navigate("${NoteEditDestination.route}/$it") }
            )
        }
        composable(route = ItemAddDestination.route) {
            ItemAddScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = NoteAddDestination.route) {
            NoteAddScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) { type = NavType.IntType })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = NoteEditDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteEditDestination.noteIdArg) { type = NavType.IntType })
        ) {
            NoteEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}