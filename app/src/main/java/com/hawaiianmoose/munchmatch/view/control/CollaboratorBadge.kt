package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun CollaboratorBadge(numberOfCollaborators: Int) {
    when(numberOfCollaborators) {
        0 -> {/*no badge*/}
        1 -> SingleCollab()
        2 -> DoubleCollab()
        3 -> TripleCollab()
        else -> MoreThanThreeCollab(numberOfExcessCollab = numberOfCollaborators - 3)
    }
}

@Composable
fun SingleCollab() {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.single_collab),
        "Collaborators",
        tint = Color.Unspecified
    )
}

@Composable
fun DoubleCollab() {
    Row {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.double_collab),
            "Collaborators",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun TripleCollab() {
    Row {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.triple_collab),
            "Collaborators",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun MoreThanThreeCollab(numberOfExcessCollab: Int) {
    Box {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.collab_plus),
            "Collaborators",
            tint = Color.Unspecified
        )
        Text("+$numberOfExcessCollab", color = Color.White, fontSize = 13.sp, modifier = Modifier
            .align(Alignment.CenterEnd)
            .offset(x = (-4).dp, y = (-2).dp))
    }
}

@Composable
@Preview
fun CollabBadgesPreview() {
    MunchMatchTheme {
        CollaboratorBadge(numberOfCollaborators = 2)
    }
}