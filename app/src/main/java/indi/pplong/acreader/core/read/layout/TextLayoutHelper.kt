package indi.pplong.acreader.core.read.layout

import android.graphics.Typeface
import android.text.StaticLayout
import android.text.TextPaint
import androidx.compose.ui.unit.IntSize
import indi.pplong.acreader.core.read.model.ImageLine
import indi.pplong.acreader.core.read.model.TextLine
import indi.pplong.acreader.core.read.model.book.BookChapter
import indi.pplong.acreader.core.read.model.ui.ContentPage
import org.jsoup.Jsoup

/**
 * Description:
 * @author PPLong
 * @date 3/12/25 7:41 PM
 */
class TextLayoutHelper() {
    var visibleWidth: Int = 400
    var visibleHeight: Int = 400
    var pendingPage = ContentPage()
    val pages = arrayListOf<ContentPage>()
    var currentY = 0F

    val paragraphVerticalPadding = 40F
    val paragraphHorizontalPadding = 40F
    val defaultMediumImageHeight = 800
    val defaultMediumImageWidth = 800

    fun layoutText(
        bookChapter: BookChapter
    ) {
        pages.clear()
        val paint = TextPaint().apply {
            color = android.graphics.Color.BLACK
            textSize = 80f
            typeface = Typeface.SERIF
        }

        var i = 0
        while (i < bookChapter.lines.size) {
            val line = bookChapter.lines[i]
            val actualVisibleWidth = visibleWidth - 2 * paragraphHorizontalPadding
            // handle img
            if (line.startsWith("<img") && line.endsWith(">")) {
                if (checkIfNeedCreateNewPage(currentY + defaultMediumImageHeight)) {
                    continue
                }
                // may throw exception
                val imgDoc = Jsoup.parse(line)
                val src = imgDoc.select("img").first()?.attr("src") ?: ""
                // read image height and width
                val imageLine = ImageLine(src, defaultMediumImageWidth, defaultMediumImageHeight, height = currentY)
                pendingPage.list.add(imageLine)
                currentY += defaultMediumImageHeight
            } else {
                val layout = StaticLayout.Builder.obtain(
                    line,
                    0, line.length,
                    paint,
                    actualVisibleWidth.toInt()
                ).build()
                var lineIdx = 0
                while (lineIdx < layout.lineCount) {

                    val textLineStartIdx = layout.getLineStart(lineIdx)
                    val textLineEndIdx = layout.getLineEnd(lineIdx)
                    val lineText = TextLine(line.substring(textLineStartIdx, textLineEndIdx))
                    val height =
                        paint.fontMetrics.descent - paint.fontMetrics.ascent + paint.fontMetrics.leading
                    if (checkIfNeedCreateNewPage(currentY + height)) {
                        continue
                    }
                    currentY += height
                    lineText.height = currentY
                    lineText.start = paragraphHorizontalPadding
                    lineText.end = paragraphHorizontalPadding
                    pendingPage.list.add(lineText)
                    lineIdx++
                }
            }
            i++
            currentY += paragraphVerticalPadding
        }
        onPendingPageComplete()
    }

    fun updateSize(size: IntSize) {
        visibleHeight = size.height
        visibleWidth = size.width
    }

    fun checkIfNeedCreateNewPage(height: Float): Boolean {
        if (height > visibleHeight) {
            onPendingPageComplete()
            currentY = 0F
            return true
        }
        return false
    }

    fun onPendingPageComplete() {
        val page = pendingPage
        pages.add(page)
        pendingPage = ContentPage()
    }
}