package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
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
            // we should not do that in a real app, API should return well formatted text in the right language
            val breedName = breed.name.capitalize(Locale.current)
            Text(
                text = breed.name.capitalize(Locale.current),
                modifier = Modifier
                    .clickable { onBreedClick(breed.name) }
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )
            HorizontalDivider()
            Column {
                breed.subBreeds.forEach { subBreed ->
                    // we should not do that in a real app, API should return well formatted text in the right language
                    val subBreedName = subBreed.name.capitalize(Locale.current)
                    Text(
                        // we should not do that in a real app, API should return well formatted text in the right language
                        text = "$subBreedName $breedName",
                        modifier = Modifier
                            .clickable {
                                onSubBreedClick(
                                    breed.name,
                                    subBreed.name
                                )
                            }
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(
                                start = 48.dp,
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            )
                    )
                    HorizontalDivider()
                }
            }
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