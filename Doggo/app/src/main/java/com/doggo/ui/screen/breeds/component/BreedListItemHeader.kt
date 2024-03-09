package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun BreedListItemHeader(
    breed: Breed,
    isExpanded: Boolean,
    onBreedClick: (String) -> Unit,
    onExpand: () -> Unit,
    modifier: Modifier
) {
    // we should not do that in a real app, API should return well formatted text in the right language
    val formattedBreedName = breed.name.capitalize(Locale.current)
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier
        .clip(RoundedCornerShape(10.dp))
        .clickable { onBreedClick(breed.name) }
        .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = formattedBreedName,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
        )
        if (breed.subBreeds.isNotEmpty()) {
            ExpandIcon(isExpanded, onClick = onExpand)
        }
    }
}

@PreviewLightDark
@Composable
private fun BreedListItemHeaderPreview() {
    DoggoTheme {
        Surface {
            BreedListItemHeader(breed = Breed(
                "bulldog",
                listOf(
                    SubBreed("boston"),
                    SubBreed("english"),
                    SubBreed("french")
                ),
            ),
                isExpanded = false,
                onBreedClick = {}, onExpand = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}