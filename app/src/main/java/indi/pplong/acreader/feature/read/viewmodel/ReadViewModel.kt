package indi.pplong.acreader.feature.read.viewmodel

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import indi.pplong.acreader.core.common.mvi.BaseViewModel
import indi.pplong.acreader.core.parser.EPUBFileParser
import indi.pplong.acreader.core.read.layout.TextLayoutHelper
import indi.pplong.acreader.core.read.model.book.BookChapter
import indi.pplong.acreader.core.read.model.ui.ContentPage
import indi.pplong.acreader.core.type.LoadingStatus
import indi.pplong.acreader.feature.read.model.Book
import indi.pplong.acreader.feature.read.model.toBook
import indi.pplong.acreader.feature.read.ui.ReadViewUiEffect
import indi.pplong.acreader.feature.read.ui.ReadViewUiIntent
import indi.pplong.acreader.feature.read.ui.ReadViewUiState
import indi.pplong.acreader.feature.shelf.repo.EBookEntryRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Description:
 * @author PPLong
 * @date 3/14/25 6:44 PM
 */
@HiltViewModel
class ReadViewModel @Inject constructor(
    private val ebookEntryRepository: EBookEntryRepositoryImpl,
) : BaseViewModel<ReadViewUiState, ReadViewUiIntent, ReadViewUiEffect>() {

    var bookId by Delegates.notNull<Int>()

    lateinit var book: Book

    lateinit var helper: TextLayoutHelper
    lateinit var epubReader: EPUBFileParser
    var currentChapterIdx = 0
    var pageIdx = 0

    override fun initialState(): ReadViewUiState {
        return ReadViewUiState()
    }

    override suspend fun handleIntent(intent: ReadViewUiIntent) {
        when (intent) {
            ReadViewUiIntent.NextChapter -> {
                onNextChapter()
            }

            ReadViewUiIntent.NextPage -> {
                onNextPage()
            }
            is ReadViewUiIntent.OnReadViewResize -> {
                onReadViewResize(intent.size)
            }
        }
    }

    fun queryBookInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val ebookEntry = ebookEntryRepository.getEbookEntryById(bookId)
            val ebookTocInfo = ebookEntryRepository.getBookTocEntryById(bookId)
            book = ebookEntry.toBook()
            book.bookChapters = ebookTocInfo
            delay(2000)
            initParser()
            setState { copy(loadingStatus = LoadingStatus.SUCCESS, book = this@ReadViewModel.book) }
        }
    }

    private fun onNextChapter() {
        currentChapterIdx++
        pageIdx = 0
        parseChapter()
    }

    private fun initParser() {
        helper = TextLayoutHelper()
        epubReader = EPUBFileParser(book.indexLocation)
    }

    private fun parseChapter() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentChapterIdx < book.bookChapters.size) {
                val bookChapter = book.bookChapters[currentChapterIdx]
                val parseChapter = epubReader.parseChapter(bookChapter.resUri)
                helper.layoutText(parseChapter)
                setState {
                    copy(chapterTitle = bookChapter.chapterName, contentPage = helper.pages[pageIdx])
                }
            }

        }
    }

    private fun onReadViewResize(size: IntSize) {
        helper.updateSize(size)
        val bookChapter = book.bookChapters[currentChapterIdx]
        val parseChapter = epubReader.parseChapter(bookChapter.resUri)
        helper.layoutText(parseChapter)
        setState {
            copy(contentPage = helper.pages[pageIdx])
        }
    }

    private fun onNextPage() {
        if (pageIdx + 1 in helper.pages.indices) {
            pageIdx++
            setState {
                copy(contentPage = helper.pages[pageIdx])
            }
        } else {
            onNextChapter()
        }

    }

}