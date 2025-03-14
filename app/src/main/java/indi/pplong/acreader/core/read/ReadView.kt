package indi.pplong.acreader.core.read

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import indi.pplong.acreader.core.read.model.ui.ContentPage
import java.nio.file.WatchEvent

/**
 * Description:
 * @author PPLong
 * @date 3/12/25 5:58 PM
 */
@Composable
fun BasicReadView(chapterName: String, dataLine: ContentPage, onSize: (IntSize) -> Unit, onClick: () -> Unit) {
    Column(
        modifier = Modifier.background(color = Color(0xFFF5F5DC)).padding(top = 50.dp)
    ) {
        Text(chapterName, modifier = Modifier.padding(start = 16.dp).widthIn(max = 100.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .clickable {
                onClick()
            }
            .onGloballyPositioned { size ->
                onSize(size.size)
            }) {

            for (line in dataLine.list) {
                line.draw(drawContext.canvas.nativeCanvas)
            }
        }
    }

}