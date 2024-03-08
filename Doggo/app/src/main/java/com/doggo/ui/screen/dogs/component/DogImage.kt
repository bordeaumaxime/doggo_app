package com.doggo.ui.screen.dogs.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.doggo.R
import com.doggo.domain.model.Dog
import com.doggo.ui.theme.DoggoTheme

@Composable
fun DogImage(dog: Dog, modifier: Modifier) {
    val contentDescription =
        stringResource(R.string.dog_image_content_desc)
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(dog.imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    ) {
        val state = painter.state
        when (state) {
            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }

            AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> {
                ImagePlaceholder(
                    color = MaterialTheme.colorScheme.error,
                    contentDescription
                )
            }

            is AsyncImagePainter.State.Loading -> {
                ImagePlaceholder(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    contentDescription
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DogImagePreview() {
    DoggoTheme {
        Surface {
            DogImage(
                dog = Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}