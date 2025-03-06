package indi.pplong.acreader.feature.shelf.viewmodel

import android.view.View
import indi.pplong.acreader.core.common.mvi.UiEffect
import indi.pplong.acreader.core.common.mvi.UiIntent
import indi.pplong.acreader.core.common.mvi.UiState
import indi.pplong.acreader.feature.shelf.model.BookInfo

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 10:01 AM
 */
enum class ShelfViewType {
    SingleLine,
    TripleLine
}

data class ShelfUiState(
    val viewType: ShelfViewType = ShelfViewType.SingleLine,
    val bookInfo: List<BookInfo> = arrayListOf()
) : UiState

sealed class ShelfUiIntent : UiIntent {
    data object GetBookInfo: ShelfUiIntent()
    data object InsertBookInfo: ShelfUiIntent()
}

sealed class ShelfUiEffect: UiEffect {
}
