package com.hawaiianmoose.munchmatch.view.control

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun CounterControl(
    modifier: Modifier = Modifier,
    count: MutableState<Int>
) {
    Card(
        modifier = Modifier.height(42.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(24)
    ) {
        Row(
            modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.Center
        ) {
            Card( //-
                modifier = modifier.weight(1f),
                onClick = {
                    if(count.value > 0) {
                        count.value--
                    }
                },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(
                    topStartPercent = 24,
                    topEndPercent = 0,
                    bottomStartPercent = 24,
                    bottomEndPercent = 0
                )
            ) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "-",
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Card( //int
                modifier = modifier.weight(1f),
                onClick = {},
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(
                    topStartPercent = 0,
                    topEndPercent = 0,
                    bottomStartPercent = 0,
                    bottomEndPercent = 0
                )
            ) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = count.value.toString(),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Card( //+
                modifier = modifier.weight(1f),
                onClick = {
                    count.value++
                },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(
                    topStartPercent = 0,
                    topEndPercent = 24,
                    bottomStartPercent = 0,
                    bottomEndPercent = 24
                )
            ) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "+",
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun CounterControlPreview() {
    MunchMatchTheme {
        CounterControl(count = mutableStateOf(0))
    }
}