package indi.pplong.acreader.feature.shelf.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 2:03 PM
 */
@Entity(
    tableName = "ebook_entry"
)
data class EBookEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val coverUri: String,
    val author: String,
    val indexLocation: String,
    val importDate: Long,
    val progress: Double
)
