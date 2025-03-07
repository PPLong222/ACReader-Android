package indi.pplong.acreader.feature.shelf.repo

import indi.pplong.acreader.feature.shelf.model.EBookEntry
import indi.pplong.acreader.feature.shelf.model.EBookEntryDao
import indi.pplong.acreader.feature.shelf.model.EBookParseEntry
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 5:40 PM
 */
@Singleton
class EBookEntryRepositoryImpl @Inject constructor(
    private val entryDao: EBookEntryDao
) : IEBookEntryRepository {
    override suspend fun insertEBookEntry(info: EBookEntry) {
        entryDao.insertEBookEntry(info)
    }

    override suspend fun getAllEbookEntries(): List<EBookEntry> {
        return entryDao.getAllEBookEntry()
    }

    override suspend fun insertTocEntry(info: EBookParseEntry) {
        entryDao.insertTocEntry(info)
    }

    override suspend fun getAllBookTocEntry(): List<EBookParseEntry> {
        return entryDao.getBookTocEntry()
    }

}