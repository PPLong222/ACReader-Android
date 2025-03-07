package indi.pplong.acreader.feature.shelf.repo

import androidx.room.Insert
import androidx.room.Query
import indi.pplong.acreader.feature.shelf.model.EBookEntry
import indi.pplong.acreader.feature.shelf.model.EBookParseEntry

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 5:40 PM
 */
interface IEBookEntryRepository {
    @Insert
    suspend fun insertEBookEntry(info: EBookEntry)

    suspend fun getAllEbookEntries(): List<EBookEntry>

    suspend fun insertTocEntry (info: EBookParseEntry)

    suspend fun getAllBookTocEntry(): List<EBookParseEntry>
}
