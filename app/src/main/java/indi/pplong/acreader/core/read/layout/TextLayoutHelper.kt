package indi.pplong.acreader.core.read.layout

import android.graphics.Typeface
import android.text.StaticLayout
import android.text.TextPaint
import androidx.compose.ui.unit.IntSize
import indi.pplong.acreader.core.read.model.ImageLine
import indi.pplong.acreader.core.read.model.TextLine
import indi.pplong.acreader.core.read.model.book.BookChapter
import indi.pplong.acreader.core.read.model.ui.ChapterStyleProvider
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

    fun layoutText(
        bookChapter: BookChapter
    ) {
        pages.clear()

        var i = 0
        while (i < bookChapter.lines.size) {
            val line = bookChapter.lines[i]

            // handle img
            if (line.startsWith("<img") && line.endsWith(">")) {
                if (checkIfNeedCreateNewPage(currentY + ChapterStyleProvider.defaultMediumImageHeight)) {
                    continue
                }
                // may throw exception
                val imgDoc = Jsoup.parse(line)
                val src = imgDoc.select("img").first()?.attr("src") ?: ""
                // read image height and width
                val imageLine = ImageLine(
                    src,
                    ChapterStyleProvider.defaultMediumImageWidth,
                    ChapterStyleProvider.defaultMediumImageHeight,
                    height = currentY
                )
                pendingPage.list.add(imageLine)
                currentY += ChapterStyleProvider.defaultMediumImageHeight
            } else {
                layoutTextByType(line)
            }
            i++
            currentY += ChapterStyleProvider.paragraphVerticalPadding
        }
        onPendingPageComplete()
    }

    private fun layoutTextByType(line: String) {
        // SubTitle
        if (line.startsWith("<h1") && line.endsWith("</h1>")) {
            val h1Doc = Jsoup.parse(line)
            val h1Text = h1Doc.select("h1").first()?.text() ?: ""
            layoutCommonText(h1Text, ChapterStyleProvider.titleTextPaint)
        } else if (line.startsWith("<h2") && line.endsWith("</h2>")) {
            val h2Doc = Jsoup.parse(line)
            val h2Text = h2Doc.select("h2").first()?.text() ?: ""
            layoutCommonText(h2Text, ChapterStyleProvider.subTitleTextPaint)
        } else {
            // Normal Text
            layoutCommonText(line, ChapterStyleProvider.paragraphPaint)
        }
    }


    private fun layoutCommonText(
        line: String,
        paint: TextPaint
    ) {
        val actualVisibleWidth = visibleWidth - 2 * ChapterStyleProvider.paragraphHorizontalPadding
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
            val lineText = TextLine(line.substring(textLineStartIdx, textLineEndIdx), paint)
            val height =
                paint.fontMetrics.descent - paint.fontMetrics.ascent + paint.fontMetrics.leading
            if (checkIfNeedCreateNewPage(currentY + height)) {
                continue
            }
            currentY += height
            lineText.height = currentY
            lineText.start = ChapterStyleProvider.paragraphHorizontalPadding
            lineText.end = ChapterStyleProvider.paragraphHorizontalPadding
            pendingPage.list.add(lineText)
            lineIdx++
        }
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