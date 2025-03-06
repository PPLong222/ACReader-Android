package indi.pplong.acreader.feature.shelf.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
 * @date 3/6/25 9:46 AM
 */

@Composable
fun SingleBookItem(bookInfo: BookInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cover),
            contentDescription = "Cover",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(start = 16.dp)
                .width(100.dp)
                .height(120.dp)

        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 16.dp)
        ) {

            Text(
                text = bookInfo.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }

}

@Preview
@Composable
fun SingleLineBookItemPreview(modifier: Modifier = Modifier) {
    SingleBookItem(BookInfo("123123123123123", "123", "123"))
}

@Composable
fun SingleBookShelf(list: List<BookInfo>) {
    LazyColumn {
        items(list) { item ->
            SingleBookItem(item)
        }
    }
}

@Preview
@Composable
fun PreviewSingleBookShelf() {
    val list = mutableListOf<BookInfo>()
    repeat(10) {
        list.add(BookInfo("!23", "!23", "123"))
    }
    SingleBookShelf(list)
}