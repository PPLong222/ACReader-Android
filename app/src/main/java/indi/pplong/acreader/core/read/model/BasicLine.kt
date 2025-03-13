package indi.pplong.acreader.core.read.model

import coil3.Canvas

/**
 * Description:
 * @author PPLong
 * @date 3/12/25 6:20 PM
 */
interface BasicLine {
    var start: Float
    var end: Float
    var height: Float
    fun draw(canvas: Canvas)
}