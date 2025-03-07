package indi.pplong.acreader.feature.shelf.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 10:47 AM
 */

@Composable
fun ShelfFAB(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        text = { Text(text = "Import") },
        icon = { Icon(Icons.Filled.Add, "Extended floating action button") }
    )
}

@Preview
@Composable
fun PreviewShelfFAB(modifier: Modifier = Modifier) {
    ShelfFAB {}
}