package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.theme.DoggoTheme

@Composable
fun BreedsResult(
    breeds: List<Breed>,
    onBreedClick: (String) -> Unit,
    onSubBreedClick: (String, String) -> Unit,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(breeds.size, key = { breeds[it].name }) { i ->
            val breed = breeds[i]
            BreedListItem(
                breed = breed,
                onBreedClick = onBreedClick,
                onSubBreedClick = onSubBreedClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun BreedsResultPreview() {
    DoggoTheme {
        Surface {
            BreedsResult(
                breeds = listOf(
                    Breed("appenzeller", emptyList()),
                    Breed("australian", listOf(SubBreed("shepherd"))),
                    Breed(
                        "bulldog",
                        listOf(
                            SubBreed("boston"),
                            SubBreed("english"),
                            SubBreed("french")
                        ),
                    ),
                    Breed("chihuahua", emptyList()),
                ),
                onBreedClick = {},
                onSubBreedClick = { _, _ -> },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}