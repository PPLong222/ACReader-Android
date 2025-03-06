package indi.pplong.acreader.feature.shelf.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import indi.pplong.acreader.R
import indi.pplong.acreader.feature.shelf.model.BookInfo

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 8:52 AM
 */


@Composable
fun TriSingleBookItem(bookInfo: BookInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(100.dp)
            .padding(top = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cover),
            contentDescription = "Cover",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(100.dp)
                .height(120.dp)
        )
        Text(
            text = bookInfo.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(100.dp)
        )
    }
}

@Preview
@Composable
fun TriSingleLineBookItemPreview(modifier: Modifier = Modifier) {
    TriSingleBookItem(BookInfo("123123123123123", "123", "123"))
}

@Preview
@Composable
fun TripleBookShelf(modifier: Modifier = Modifier) {
    LazyVerticalGrid (
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth())
    {
        items(3) {
            SingleLineBookItemPreview()
        }
    }
}


