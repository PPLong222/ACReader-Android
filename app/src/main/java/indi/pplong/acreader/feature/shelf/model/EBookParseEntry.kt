package indi.pplong.acreader.feature.shelf.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 2:07 PM
 */
@Entity(
    tableName = "ebook_parse_entry"
)
data class EBookParseEntry(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int,
    val chapter: Int,
    val resUri: String
)
