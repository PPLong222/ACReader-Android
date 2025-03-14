package indi.pplong.acreader.core.read.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import coil3.Canvas
import androidx.core.graphics.scale

/**
 * Description:
 * @author PPLong
 * @date 3/14/25 5:01 PM
 */
class ImageLine (
    val src: String,
    val maxWidth: Int,
    val maxHeight: Int,
    override var start: Float = 0F, override var end: Float = 0F,
    override var height: Float = 0F
) : BasicLine {

    override fun draw(canvas: Canvas) {
        val preffix = "/data/user/0/indi.pplong.acreader/files/books/Fluent Python, 2nd Edition (Luciano Ramalho)/OEBPS/"
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeFile(preffix + src, options)

        options.apply {
            inJustDecodeBounds = false
        }
        val bitmap = BitmapFactory.decodeFile(preffix + src, options)

        val resizedBitmap = bitmap.scale(maxWidth, maxHeight)
        val startPadding = (canvas.width - maxWidth) / 2f + start
        canvas.drawBitmap(resizedBitmap, startPadding, height, Paint())
    }


}