package indi.pplong.acreader.core.read.model

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import coil3.Canvas

/**
 * Description:
 * @author PPLong
 * @date 3/12/25 6:20 PM
 */
class TextLine(
    val text: String,
    val paint: TextPaint,
    override var start: Float = 0F, override var end: Float = 0F,
    override var height: Float = 0F
) : BasicLine {
    override fun draw(canvas: Canvas) {
        canvas.drawText(text, start, height, paint)
    }
}

