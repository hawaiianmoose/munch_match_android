package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun SegmentedButtonControl(
    modifier: Modifier = Modifier,
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    cornerRadius: Int = 24,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(defaultSelectedItemIndex) }
    val itemIndex = remember { mutableIntStateOf(defaultSelectedItemIndex) }

    Card(
        modifier = Modifier
            .height(42.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selectedIndex.intValue == itemIndex.intValue) {
                MaterialTheme.colorScheme.background
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                itemIndex.intValue = index
                Card(
                    modifier = modifier
                        .weight(1f),
                    onClick = {
                        selectedIndex.intValue = index
                        onItemSelection(selectedIndex.intValue)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedIndex.intValue == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        },
                        contentColor = if (selectedIndex.intValue == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = cornerRadius
                    ),
                ) {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = item,
                            style = LocalTextStyle.current.copy(
                                fontSize = 14.sp,
                                fontWeight = if (selectedIndex.intValue == index)
                                    LocalTextStyle.current.fontWeight
                                else
                                    FontWeight.Normal,
                                color = if (selectedIndex.intValue == index)
                                    Color.White
                                else
                                    MaterialTheme.colorScheme.primary
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SegmentedButtonControlPreview() {
    MunchMatchTheme {
        SegmentedButtonControl(items = listOf("1", "2", "3"), onItemSelection = {} )
    }
}