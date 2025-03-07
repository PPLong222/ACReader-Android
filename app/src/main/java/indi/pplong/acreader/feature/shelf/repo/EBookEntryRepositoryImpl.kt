package indi.pplong.acreader.feature.shelf.repo

import indi.pplong.acreader.feature.shelf.model.EBookEntry
import indi.pplong.acreader.feature.shelf.model.EBookEntryDao
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
    override suspend fun insert(info: EBookEntry) {
        entryDao.insert(info)
    }

    override suspend fun getAll(): List<EBookEntry> {
        return entryDao.getAll()
    }

}