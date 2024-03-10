package com.doggo.ui.screen.breeds.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.doggo.R
import com.doggo.ui.theme.DoggoTheme

@Composable
fun ExpandIcon(isExpanded: Boolean, breedName: String, onClick: () -> Unit) {
    val rotationDegrees: Float by animateFloatAsState(
        if (isExpanded) 180f else 0f, label = "ArrowAnimation"
    )
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = if (isExpanded) {
                stringResource(R.string.hide_sub_breeds, breedName)
            } else {
                stringResource(R.string.show_sub_breeds, breedName)
            },
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.graphicsLayer {
                rotationZ = rotationDegrees
            })
    }
}

@PreviewLightDark
@Composable
private fun BreedListItemPreview() {
    DoggoTheme {
        Surface {
            Column {
                ExpandIcon(isExpanded = true, "bulldog", onClick = {})
                ExpandIcon(isExpanded = false, "bulldog", onClick = {})
            }
        }
    }
}