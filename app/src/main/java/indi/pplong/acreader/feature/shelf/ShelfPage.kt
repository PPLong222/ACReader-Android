package indi.pplong.acreader.feature.shelf

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import indi.pplong.acreader.core.unzipFile
import indi.pplong.acreader.feature.shelf.ui.ShelfFAB
import indi.pplong.acreader.feature.shelf.viewmodel.ShelfViewModel

/**
 * Description:
 * @author PPLong
 * @date 3/5/25 8:53 PM
 */
@Composable
fun ShelfPage() {
    val viewModel : ShelfViewModel = hiltViewModel<ShelfViewModel>()
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                unzipFile(context.contentResolver.openInputStream(uri), context.filesDir.path+"/books")
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
        floatingActionButton = { ShelfFAB { filePickerLauncher.launch(arrayOf("*/*")) } }
    ) { innerValues ->
        Box(modifier = Modifier.padding(innerValues))
    }
}