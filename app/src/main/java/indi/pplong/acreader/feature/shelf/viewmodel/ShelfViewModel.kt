package indi.pplong.acreader.feature.shelf.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import indi.pplong.acreader.core.common.mvi.BaseViewModel
import indi.pplong.acreader.core.parser.EPUBFileParser
import indi.pplong.acreader.feature.shelf.model.EBookEntry
import indi.pplong.acreader.feature.shelf.repo.EBookEntryRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 10:01 AM
 */
@HiltViewModel
class ShelfViewModel @Inject constructor(
    private val ebookEntryRepository: EBookEntryRepositoryImpl
) :
    BaseViewModel<ShelfUiState, ShelfUiIntent, ShelfUiEffect>() {

    init {
        getBookEntryList()
    }

    override fun initialState(): ShelfUiState {
        return ShelfUiState()
    }

    override suspend fun handleIntent(intent: ShelfUiIntent) {
        when (intent) {
            ShelfUiIntent.GetBookInfo -> {}

            is ShelfUiIntent.InsertBookInfo -> {
                exportNewBook(intent.path)
            }

            is ShelfUiIntent.NavigateToBook -> {
                sendEffect {
                    ShelfUiEffect.NavigateToReadPage(intent.bookId)
                }
            }
        }
    }

    fun exportNewBook(filePath: String) {
        val parser = EPUBFileParser(filePath)

        viewModelScope.launch {
            var bookId = -1
            parser.parseFile()
            parser.parseTocInfo()
            ebookEntryRepository.insertEBookEntry(
                EBookEntry(
                    title = parser.title!!,
                    coverUri = parser.coverPath!!,
                    author = "",
                    indexLocation = filePath,
                    importDate = System.currentTimeMillis(),
                    progress = 0.0
                )
            )
            ebookEntryRepository.getAllEbookEntries()
                .firstOrNull { it.indexLocation == filePath }?.let {
                    bookId = it.id
                }
            parser.chapterList.forEach {
                launch {
                    ebookEntryRepository.insertTocEntry(it.copy(id = bookId))
                }
            }
            getBookEntryList()
        }

    }

    private fun getBookEntryList() {
        viewModelScope.launch {
            val list = ebookEntryRepository.getAllEbookEntries()
            setState {
                copy(bookInfo = list)
            }
        }
    }
}