package indi.pplong.acreader.core.read

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import indi.pplong.acreader.core.read.model.ui.ContentPage

/**
 * Description:
 * @author PPLong
 * @date 3/12/25 5:58 PM
 */
@Composable
fun BasicReadView(dataLine: ContentPage, onSize: (IntSize) -> Unit, onClick: () -> Unit) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp)
        .clickable {
            onClick()
        }
        .background(color = Color.Blue)
        .onGloballyPositioned { size ->
            onSize(size.size)
        }) {

        for (line in dataLine.list) {
            line.draw(drawContext.canvas.nativeCanvas)
        }
    }
}