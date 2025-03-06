package indi.pplong.acreader.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import indi.pplong.acreader.feature.shelf.ShelfPage

/**
 * @author PPLong
 * @date 3/5/25 7:16 PM
 */

enum class BasicBottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    Home("Home", Icons.Filled.Home, "Home"),
    Books(
        "Books", Icons.Default.Menu, "Books"
    ),
}

@Composable
fun MainNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BasicBottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, null) },
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
        startDestination = BasicBottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(
            route = BasicBottomNavItem.Home.route
        ) {
            ShelfPage()
        }

        composable(BasicBottomNavItem.Books.route) {

        }
    }

}