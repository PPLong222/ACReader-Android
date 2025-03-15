package indi.pplong.acreader.core.read.model.ui

import android.graphics.Typeface
import android.text.TextPaint

/**
 * Description:
 * @author PPLong
 * @date 3/13/25 11:16 AM
 */
object ChapterStyleProvider {
    var paragraphVerticalPadding = 40F

    var paragraphHorizontalPadding = 40F

    val defaultMediumImageHeight = 800
    val defaultMediumImageWidth = 800

    val titleTextPaint = TextPaint().apply {
        color = android.graphics.Color.BLACK
        textSize = 80F
        typeface = Typeface.DEFAULT_BOLD
    }

    val subTitleTextPaint = TextPaint().apply {
        color = android.graphics.Color.BLACK
        textSize = 70F
        typeface = Typeface.DEFAULT_BOLD
    }

    val paragraphPaint = TextPaint().apply {
        color = android.graphics.Color.BLACK
        textSize = 60F
        typeface = Typeface.SERIF
    }
}