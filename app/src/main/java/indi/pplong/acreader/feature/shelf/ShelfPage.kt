package indi.pplong.acreader.feature.shelf

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import indi.pplong.acreader.core.getFileNameFromUri
import indi.pplong.acreader.core.navigation.ReadNavScreen
import indi.pplong.acreader.core.unzipFile
import indi.pplong.acreader.feature.shelf.ui.ShelfFAB
import indi.pplong.acreader.feature.shelf.ui.ShelfSearchBar
import indi.pplong.acreader.feature.shelf.ui.TripleBookShelf
import indi.pplong.acreader.feature.shelf.viewmodel.ShelfUiEffect
import indi.pplong.acreader.feature.shelf.viewmodel.ShelfUiIntent
import indi.pplong.acreader.feature.shelf.viewmodel.ShelfViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File

/**
 * Description:
 * @author PPLong
 * @date 3/5/25 8:53 PM
 */
@Composable
fun ShelfPage(navController: NavHostController) {
    val viewModel: ShelfViewModel = hiltViewModel<ShelfViewModel>()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(), onResult = { uri ->
            uri?.let {
                val targetDirectory = File(
                    context.filesDir, "books" + File.separator + getFileNameFromUri(context, uri)
                ).path
                Log.d("Hust1037", targetDirectory)
                Log.d("Hust1037", "uri: ${uri.path}")
                unzipFile(context.contentResolver.openInputStream(uri), targetDirectory)
                Log.d(
                    "Hust1037",
                    context.filesDir.path + File.separator + "books" + File.separator + uri.lastPathSegment
                )
                viewModel.sendIntent(ShelfUiIntent.InsertBookInfo(targetDirectory))
            }
        })


    LaunchedEffect(viewModel.uiEffect) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ShelfUiEffect.NavigateToReadPage -> {
                    navController.navigate(ReadNavScreen(effect.bookId))
                }

                else -> {}
            }
        }
    }
    val drawerState =
        androidx.compose.material3.rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = CoroutineScope(Dispatchers.Main)
    Scaffold(
        topBar = {
            ShelfSearchBar(
                {},
                {},
                drawerState,
                scope,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth()
            )
        },
        floatingActionButton = { ShelfFAB { filePickerLauncher.launch(arrayOf("*/*")) } }) { innerValues ->
        Column(modifier = Modifier.padding(innerValues)) {
            TripleBookShelf(
                list = uiState.bookInfo,
                modifier = Modifier.padding(top = 16.dp),
                onIntent = viewModel::sendIntent
            )
        }

    }
}