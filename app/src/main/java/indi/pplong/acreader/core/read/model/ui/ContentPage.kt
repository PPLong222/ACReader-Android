package indi.pplong.acreader.core.read.model.ui

import indi.pplong.acreader.core.read.model.BasicLine
import indi.pplong.acreader.core.read.model.TextLine

/**
 * Description:
 * @author PPLong
 * @date 3/13/25 9:48 AM
 */
data class ContentPage(
    var textHeight: Float = 0F,
    val list: MutableList<BasicLine> = arrayListOf()
) {
}
