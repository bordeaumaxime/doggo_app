package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.theme.DoggoTheme

@Composable
fun BreedListItemSubBreed(
    breed: Breed,
    subBreed: SubBreed,
    onSubBreedClick: (String, String) -> Unit,
    modifier: Modifier
) {
    // we should not do that in a real app, API should return well formatted text in the right language
    val formattedBreedName = breed.name.capitalize(Locale.current)
    val formattedSubBreedName =
        subBreed.name.capitalize(Locale.current)
    Text(
        // we should not do that in a real app, API should return well formatted text in the right language
        text = "$formattedSubBreedName $formattedBreedName",
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onSubBreedClick(
                    breed.name,
                    subBreed.name
                )
            }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    )
}

@PreviewLightDark
@Composable
private fun BreedListItemSubBreedPreview() {
    DoggoTheme {
        Surface {
            BreedListItemSubBreed(
                Breed("bulldog", emptyList()),
                SubBreed("boston"),
                onSubBreedClick = { _, _ -> },
                Modifier.fillMaxWidth()
            )
        }
    }
}