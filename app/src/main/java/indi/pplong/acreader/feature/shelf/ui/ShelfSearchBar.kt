package indi.pplong.acreader.feature.shelf.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import indi.pplong.acreader.ui.theme.ACReaderTheme

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 1:18 PM
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelfSearchBar(
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = "",
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false,
                onExpandedChange = { },
                leadingIcon = { Icon(Icons.Filled.Search, null) },
                placeholder = { Text("Search within your Shelves") }
            )
        },
        expanded = false,
        onExpandedChange = { },
        modifier = modifier
    ) {

    }
}

@Preview
@Composable
fun PreviewShelfSearchBar(modifier: Modifier = Modifier) {
    ACReaderTheme {
        ShelfSearchBar(
            {}, {}, Modifier
        )
    }

}