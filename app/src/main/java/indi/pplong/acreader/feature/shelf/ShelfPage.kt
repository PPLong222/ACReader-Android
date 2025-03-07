package indi.pplong.acreader.feature.shelf

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import indi.pplong.acreader.core.unzipFile
import indi.pplong.acreader.feature.shelf.ui.PreviewTripleBookShelf
import indi.pplong.acreader.feature.shelf.ui.ShelfFAB
import indi.pplong.acreader.feature.shelf.ui.ShelfSearchBar
import indi.pplong.acreader.feature.shelf.ui.TripleBookShelf
import indi.pplong.acreader.feature.shelf.viewmodel.ShelfUiIntent
import indi.pplong.acreader.feature.shelf.viewmodel.ShelfViewModel
import java.io.File

/**
 * Description:
 * @author PPLong
 * @date 3/5/25 8:53 PM
 */
@Composable
fun ShelfPage() {
    val viewModel: ShelfViewModel = hiltViewModel<ShelfViewModel>()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                Log.d("123", "uri: ${uri.path}")
                unzipFile(
                    context.contentResolver.openInputStream(uri),
                    context.filesDir.path + File.separator + "books" + File.separator + uri.lastPathSegment
                )
                viewModel.sendIntent(ShelfUiIntent.InsertBookInfo(context.filesDir.path + File.separator + "books" + File.separator + uri.lastPathSegment))

            }
        }
    )


    LaunchedEffect(viewModel.uiEffect) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {

                else -> {}
            }
        }
    }
    Scaffold(
        topBar = { ShelfSearchBar({}, {}, modifier = Modifier.padding(horizontal = 16.dp)) },
        floatingActionButton = { ShelfFAB { filePickerLauncher.launch(arrayOf("*/*")) } }
    ) { innerValues ->
        Column(modifier = Modifier.padding(innerValues)) {
//            PreviewTripleBookShelf(modifier = Modifier.padding(top = 16.dp))
            TripleBookShelf(list = uiState.bookInfo, modifier = Modifier.padding(top = 16.dp))
        }

    }
}