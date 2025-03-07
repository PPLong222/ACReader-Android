package indi.pplong.acreader.feature.shelf.repo

import androidx.room.Insert
import androidx.room.Query
import indi.pplong.acreader.feature.shelf.model.EBookEntry

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 5:40 PM
 */
interface IEBookEntryRepository {
    @Insert
    suspend fun insert(info: EBookEntry)

    suspend fun getAll(): List<EBookEntry>
}
