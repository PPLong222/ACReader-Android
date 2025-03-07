package indi.pplong.acreader.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import indi.pplong.acreader.feature.shelf.model.EBookEntry
import indi.pplong.acreader.feature.shelf.model.EBookEntryDao
import indi.pplong.acreader.feature.shelf.model.EBookParseEntry

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 8:51 PM
 */
@Database(
    entities = [EBookEntry::class, EBookParseEntry::class],
    version = 1,
    exportSchema = false
)
abstract class CommonDatabase : RoomDatabase() {
    abstract fun ebookEntryDao(): EBookEntryDao

    companion object {
        private var Instance: CommonDatabase? = null
        private const val DATABASE_NAME = "common_db"
        fun getDatabase(context: Context): CommonDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CommonDatabase::class.java, DATABASE_NAME)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}