package indi.pplong.acreader.feature.read

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import indi.pplong.acreader.R
import indi.pplong.acreader.core.read.ReadView
import indi.pplong.acreader.core.type.LoadingStatus
import indi.pplong.acreader.feature.read.ui.ReadViewUiIntent
import indi.pplong.acreader.feature.read.viewmodel.ReadViewModel

/**
 * Description:
 * @author PPLong
 * @date 3/14/25 6:38 PM
 */
@Composable
fun ReadScreen(modifier: Modifier = Modifier, bookId: Int) {
    val viewModel = hiltViewModel<ReadViewModel>().apply { this.bookId = bookId }
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.queryBookInfo()
    }
    if (uiState.loadingStatus == LoadingStatus.LOADING) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
            Text(
                stringResource(R.string.loading),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    } else if (uiState.loadingStatus == LoadingStatus.SUCCESS) {
        ReadView(uiState, viewModel::sendIntent)
    }
}