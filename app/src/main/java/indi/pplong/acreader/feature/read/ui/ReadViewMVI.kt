package indi.pplong.acreader.feature.read.ui

import androidx.compose.ui.unit.IntSize
import indi.pplong.acreader.core.common.mvi.UiEffect
import indi.pplong.acreader.core.common.mvi.UiIntent
import indi.pplong.acreader.core.common.mvi.UiState
import indi.pplong.acreader.core.read.model.ui.ContentPage
import indi.pplong.acreader.core.type.LoadingStatus
import indi.pplong.acreader.feature.read.model.Book

/**
 * Description:
 * @author PPLong
 * @date 3/14/25 7:12 PM
 */
data class ReadViewUiState(
    val loadingStatus: LoadingStatus = LoadingStatus.LOADING,
    val chapterTitle: String = "",
    val contentPage: ContentPage = ContentPage(),
    val currentChapterIdx : Int = 0,
    val book: Book = Book()
) : UiState

sealed class ReadViewUiIntent : UiIntent {
    data object NextChapter: ReadViewUiIntent()
    data class OnReadViewResize(val size: IntSize): ReadViewUiIntent()
    data object NextPage: ReadViewUiIntent()
}

sealed class ReadViewUiEffect : UiEffect {
}
