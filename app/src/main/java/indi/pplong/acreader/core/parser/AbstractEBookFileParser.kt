package indi.pplong.acreader.core.parser

import android.util.Log

/**
 * Abstract EBook File Parser
 * @author PPLong
 * @date 3/6/25 6:08 PM
 */
abstract class AbstractEBookFileParser(
    val basicPath: String
) {
    private val TAG = javaClass.name

    var title: String? = null
    var coverPath: String? = null

    abstract fun parseFile()

    abstract fun parseTocInfo()

    fun onFileParseComplete() {
        Log.d(TAG, "EBook Title: $title, CoverPath: $coverPath")
    }
}