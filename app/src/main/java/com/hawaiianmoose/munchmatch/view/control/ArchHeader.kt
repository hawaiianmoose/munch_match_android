package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun ArchHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(240.dp)
            .padding(bottom = 4.dp)
    ) {
        ConstraintLayout {
            val (box, arch) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(240.dp)
                    .clip(RoundedCornerShape(0.dp, 0.dp, 28.dp, 28.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .constrainAs(box) {
                        top.linkTo(parent.top)
                    },
                content = {
                    Text(
                        text = "Munch Match",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 40.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 8.dp)
                    )
                }
            )
//            Image(
//                imageVector = ImageVector.vectorResource(id = R.drawable.arch_header),
//                contentDescription = "Arch Image",
//                modifier = Modifier
//                    .offset(y = 16.dp)
//                    .constrainAs(arch) {
//                        bottom.linkTo(box.bottom)
//                        centerHorizontallyTo(parent)
//                    }
//            )
        }
    }
}

@Composable
@Preview
fun ArchPreview() {
    MunchMatchTheme {
        ArchHeader()
    }
}