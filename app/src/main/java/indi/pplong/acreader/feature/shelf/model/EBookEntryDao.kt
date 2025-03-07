package indi.pplong.acreader.feature.shelf.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 5:29 PM
 */
@Dao
interface EBookEntryDao {
    @Insert
    suspend fun insert (info: EBookEntry)

    @Query("SELECT * from ebook_entry")
    suspend fun getAll(): List<EBookEntry>
}