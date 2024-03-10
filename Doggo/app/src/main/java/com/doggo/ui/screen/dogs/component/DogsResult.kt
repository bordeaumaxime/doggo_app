package com.doggo.ui.screen.dogs.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.doggo.domain.model.Dog
import com.doggo.ui.theme.DoggoTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DogsResult(
    dogs: List<Dog>,
    modifier: Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(200.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(dogs) { dog ->
                DogImage(
                    dog, Modifier
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .testTag(dog.imageUrl)
                )
            }
        },
        modifier = modifier.testTag(DOGS_LIST_TEST_TAG)
    )
}

const val DOGS_LIST_TEST_TAG = "DogsList"

@PreviewLightDark
@Composable
private fun DogsResultPreview() {
    DoggoTheme {
        Surface {
            DogsResult(
                dogs = listOf(
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg"),
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg"),
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}