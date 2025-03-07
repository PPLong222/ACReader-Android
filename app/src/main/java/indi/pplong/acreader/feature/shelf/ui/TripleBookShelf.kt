package indi.pplong.acreader.feature.shelf.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import indi.pplong.acreader.R
import indi.pplong.acreader.feature.shelf.model.EBookEntry

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 8:52 AM
 */


@Composable
fun TriSingleBookItem(entry: EBookEntry) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(100.dp)
            .padding(top = 10.dp)
    ) {
        AsyncImage(
            model = entry.coverUri,
            contentDescription = "Cover",
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.books),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )
        Text(
            text = entry.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun TriSingleLineBookItemPreview(modifier: Modifier = Modifier) {
    TriSingleBookItem(
        EBookEntry(
            1,
            "123",
            "123",
            "123",
            "123",
            System.currentTimeMillis(),
            progress = 0.2
        )
    )
}

@Preview
@Composable
fun PreviewTripleBookShelf(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(20) {
            TriSingleLineBookItemPreview()
        }
    }
}

@Composable
fun TripleBookShelf(list: List<EBookEntry>, modifier: Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(list) { item ->
            TriSingleBookItem(item)
        }
    }
}



