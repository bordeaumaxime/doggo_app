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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.screen.common.BreedNameFormatter
import com.doggo.ui.theme.DoggoTheme

@Composable
fun BreedListItemHeader(
    breed: Breed,
    isExpanded: Boolean,
    onBreedClick: (String) -> Unit,
    onExpand: () -> Unit,
    modifier: Modifier
) {
    val formattedBreedName =
        BreedNameFormatter.getBreedOrSubBreedFormattedName(breed.name, null)
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
            ExpandIcon(isExpanded, breed.name, onClick = onExpand)
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