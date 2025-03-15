package indi.pplong.acreader

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import indi.pplong.acreader.core.navigation.BasicNavigationHost
import indi.pplong.acreader.core.navigation.MainNavigationBar
import indi.pplong.acreader.core.navigation.ReadNavScreen
import indi.pplong.acreader.ui.theme.ACReaderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ACReaderTheme {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentDestination = navBackStackEntry?.destination?.route
                Log.d("123", currentDestination.toString())
                val isBottomBarVisible = try {
                    navBackStackEntry?.toRoute<ReadNavScreen>() == null
                } catch (e: Exception) {
                    true
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (isBottomBarVisible) MainNavigationBar(navController) },
                ) { innerPadding ->
                    BasicNavigationHost(
                        navController = navController,
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ACReaderTheme {
        Greeting("Android")
    }
}