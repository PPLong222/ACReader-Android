package indi.pplong.acreader.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import indi.pplong.acreader.R
import indi.pplong.acreader.feature.shelf.ShelfPage

/**
 * @author PPLong
 * @date 3/5/25 7:16 PM
 */

enum class BasicBottomNavItem(
    val route: String,
    val res: Int?,
    val icon: ImageVector?,
    val label: String
) {
    Shelf("Shelf", R.drawable.books, null, "Shelf"),
    Books(
        "Books", R.drawable.book_spark, null, "Books"
    ),
    Notes(
        "Notes", R.drawable.notes, null, "Notes"
    ),
    Setting(
        "Setting", null, Icons.Default.Settings, "Setting"
    ),
}

@Composable
fun MainNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BasicBottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = {
                    item.res?.let { resId ->
                        Icon(painterResource(resId), null)
                    } ?:
                    item.icon?.let { icon ->
                        Icon(icon, null)
                    }
                },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
fun BasicNavigationHost(navController: NavHostController, modifier: Modifier) {

    NavHost(
        navController = navController,
        startDestination = BasicBottomNavItem.Shelf.route,
        modifier = modifier
    ) {
        composable(
            route = BasicBottomNavItem.Shelf.route
        ) {
            ShelfPage()
        }

        composable(BasicBottomNavItem.Books.route) {

        }

        composable(BasicBottomNavItem.Notes.route) {

        }

        composable(BasicBottomNavItem.Setting.route) {

        }

    }

}