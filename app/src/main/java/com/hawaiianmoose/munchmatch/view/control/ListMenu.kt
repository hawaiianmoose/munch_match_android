package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun ListMenu(modifier: Modifier = Modifier, clearFunc: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.more_menu),
                contentDescription = stringResource(id = R.string.desc_more),
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.clear_menu),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.clear_menu),
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                onClick = {
                    clearFunc()
                    expanded = !expanded
                }
            )
        }
    }
}

@Composable
@Preview
fun PreviewListMenu() {
    MunchMatchTheme {
        Column {
//            DropdownMenuItem(
//                text = {
//                    Text(
//                        text = stringResource(id = R.string.remove_menu),
//                        fontSize = MaterialTheme.typography.labelSmall.fontSize
//                    )
//                },
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.ClearAll,
//                        contentDescription = stringResource(id = R.string.remove_menu),
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                },
//                onClick = { /* TODO removeFunc()*/ }
//            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.clear_menu),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.clear_menu),
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                onClick = { /* TODO clearFunc()*/ }
            )
        }
    }
}
