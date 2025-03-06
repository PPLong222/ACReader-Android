package indi.pplong.acreader.feature.shelf.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import indi.pplong.acreader.core.common.mvi.BaseViewModel
import javax.inject.Inject

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 10:01 AM
 */
@HiltViewModel
class ShelfViewModel @Inject constructor() :
    BaseViewModel<ShelfUiState, ShelfUiIntent, ShelfUiEffect>() {
    override fun initialState(): ShelfUiState {
        return ShelfUiState()
    }

    override suspend fun handleIntent(intent: ShelfUiIntent) {
        when (intent) {
            ShelfUiIntent.GetBookInfo -> {}
            ShelfUiIntent.InsertBookInfo -> {
            }
        }
    }
}