package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.dialog.NewListDialog
import com.hawaiianmoose.munchmatch.viewmodel.ListViewModel
import com.hawaiianmoose.munchmatch.R

@Composable
fun ListHeader(
    listViewModel: ListViewModel,
    navigateToAccountSettings: () -> Unit, ) {
    val openDialog = remember { mutableStateOf(false)  }

    NewListDialog(openDialog, listViewModel)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(84.dp)
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(0.dp, 0.dp, 28.dp, 28.dp))
                .background(MaterialTheme.colorScheme.primary),
            content = {
                Text(
                    text = stringResource(R.string.my_lists),
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                )
                IconButton(
                    onClick = {
                        openDialog.value = true
                    },
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.add_new_list),
                        "New List",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = {navigateToAccountSettings.invoke()},
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.settings),
                        "Settings",
                        tint = Color.White
                    )
                }
            }
        )
    }
}

@Composable
@Preview
fun ListHeaderPreview() {
    MunchMatchTheme {
        ListHeader(ListViewModel()) {/*empty*/ }
    }
}