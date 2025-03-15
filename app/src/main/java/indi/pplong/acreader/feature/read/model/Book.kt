package indi.pplong.acreader.feature.read.model

import indi.pplong.acreader.feature.shelf.model.EBookEntry
import indi.pplong.acreader.feature.shelf.model.EBookParseEntry

/**
 * Description:
 * @author PPLong
 * @date 3/14/25 7:07 PM
 */
data class Book(
    val id: Int = 0,
    val title: String = "",
    val coverUri: String = "",
    val author: String = "",
    val indexLocation: String = "",
    val importDate: Long =  0,
    val progress: Double = 0.0,
    var bookChapters: List<EBookParseEntry> = arrayListOf()
)

fun EBookEntry.toBook(): Book {
    return Book(
        id = id,
        title = title,
        coverUri = coverUri,
        author = author,
        indexLocation = indexLocation,
        importDate = importDate,
        progress = progress,
        bookChapters = arrayListOf()
    )
}
