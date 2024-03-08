package com.doggo.ui.screen.dogs.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.doggo.ui.theme.DoggoTheme

@Composable
fun ImagePlaceholder(color: Color, contentDesc: String) {
    Box(
        modifier = Modifier
            .semantics { contentDescription = contentDesc }
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(color)
    )
}

@Preview
@Composable
private fun ImagePlaceholderPreview() {
    DoggoTheme {
        Surface {
            ImagePlaceholder(
                color = Color.Blue, contentDesc = "Content desc"
            )
        }
    }
}