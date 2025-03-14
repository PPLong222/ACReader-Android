package indi.pplong.acreader.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import indi.pplong.acreader.core.parser.EPUBFileParser
import indi.pplong.acreader.core.read.BasicReadView
import indi.pplong.acreader.core.read.layout.TextLayoutHelper
import indi.pplong.acreader.core.read.model.ui.ContentPage


@Composable
fun SettingPage(modifier: Modifier = Modifier) {
    val helper = TextLayoutHelper()
    val epubRead = EPUBFileParser("/data/user/0/indi.pplong.acreader/files/books/Fluent Python, 2nd Edition (Luciano Ramalho)")
    val bookchapter = epubRead.parseChapter("/data/user/0/indi.pplong.acreader/files/books/Fluent Python, 2nd Edition (Luciano Ramalho)/OEBPS/ch01.xhtml")
    var currentPage by remember { mutableStateOf(ContentPage()) }
    var currentIdx by remember { mutableIntStateOf(0) }
    BasicReadView(bookchapter.title, dataLine = currentPage, onSize = {
            size ->
        helper.updateSize(size)
        helper.layoutText(bookchapter)
        currentPage = helper.pages.getOrNull(currentIdx) ?: ContentPage()
    }) {
        currentIdx++
        currentPage = helper.pages.getOrNull(currentIdx) ?: ContentPage()
    }
}

