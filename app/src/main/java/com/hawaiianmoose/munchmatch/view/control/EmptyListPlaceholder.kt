package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun EmptyListPlaceholder() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.twirly_arrow), "Pointer Arrow", modifier = Modifier.offset(x = 64.dp))
        Spacer(Modifier.size(8.dp))
        Image(painter = painterResource(id = R.drawable.empty_list_icon), "Empty List")
        Spacer(Modifier.size(16.dp))
        Image(painter = painterResource(id = R.drawable.exclamation), "Let's make a list!")
    }
}

@Composable
@Preview
fun PreviewEmptyListPlaceholder() {
    MunchMatchTheme {
        EmptyListPlaceholder()
    }
}