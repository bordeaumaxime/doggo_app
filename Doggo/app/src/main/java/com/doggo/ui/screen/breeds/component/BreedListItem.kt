package com.doggo.ui.screen.breeds.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.doggo.R
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.theme.DoggoTheme

@Composable
fun BreedListItem(
    breed: Breed,
    onBreedClick: (String) -> Unit,
    onSubBreedClick: (String, String) -> Unit,
    modifier: Modifier
) {
    val isPreview = LocalInspectionMode.current
    Column(modifier = modifier) {
        // expand by default inside the Preview
        var isExpanded by rememberSaveable { mutableStateOf(isPreview) }
        BreedListItemHeader(
            breed = breed,
            isExpanded = isExpanded,
            onBreedClick = onBreedClick,
            onExpand = { isExpanded = !isExpanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (breed.subBreeds.isNotEmpty()) {
            AnimatedVisibility(
                isExpanded,
                enter = expandAnimation(),
                exit = shrinkAnimation(),
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.sub_breeds),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 48.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                    breed.subBreeds.forEach { subBreed ->
                        BreedListItemSubBreed(
                            breed,
                            subBreed,
                            onSubBreedClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 48.dp,
                                    end = 8.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
                        )
                    }
                }
            }
        }
    }

}

private fun shrinkAnimation() = fadeOut() + shrinkVertically(
    animationSpec =
    spring(
        stiffness = Spring.StiffnessMedium,
        visibilityThreshold = IntSize.VisibilityThreshold
    )
)

private fun expandAnimation() = fadeIn() + expandVertically(
    animationSpec =
    spring(
        stiffness = Spring.StiffnessMedium,
        visibilityThreshold = IntSize.VisibilityThreshold
    )
)

@PreviewLightDark
@Composable
private fun BreedListItemPreview() {
    DoggoTheme {
        Surface {
            BreedListItem(
                Breed(
                    "bulldog",
                    listOf(
                        SubBreed("boston"),
                        SubBreed("english"),
                        SubBreed("french")
                    ),
                ),
                onBreedClick = {},
                onSubBreedClick = { _, _ -> },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}