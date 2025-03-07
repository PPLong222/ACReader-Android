package indi.pplong.acreader.feature.shelf.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import indi.pplong.acreader.R
import indi.pplong.acreader.ui.theme.ACReaderTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    drawerState: DrawerState,
    scope: CoroutineScope,
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
                leadingIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Open Drawer",
                        )
                    }
                },
                placeholder = {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                        )
                        Text(
                            text = "Search Books",
                        )
                    }
                },
                trailingIcon = {
                    Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.liststyle_icon),
                                contentDescription = "List or Grid style"
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More")
                        }
                    }
                })

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
    val drawerState =
        androidx.compose.material3.rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = CoroutineScope(Dispatchers.Main)

    ACReaderTheme {
        ShelfSearchBar(
            onQueryChange = {},
            onSearch = {},
            drawerState = drawerState,
            scope = scope,
            modifier = modifier,
        )
    }

}