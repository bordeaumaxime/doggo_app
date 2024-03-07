package com.doggo.ui.screen.dogs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.doggo.domain.model.Dog
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.screen.dogs.component.DogsScreenInternal

@Composable
fun DogsScreen() {
    val uiState = ScreenUiState.Result(
        data = listOf(
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_1592.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_5488.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_2131.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_1410.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_6430.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_6493.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_9229.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-afghan/n02088094_980.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-basset/n02088238_10870.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-basset/n02088238_11846.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-basset/n02088238_9419.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-basset/n02088238_9635.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-blood/n02088466_10083.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-blood/n02088466_12003.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-blood/n02088466_2030.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-english/n02089973_140.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1492.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-english/n02089973_2017.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-english/n02089973_504.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_1182.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_2062.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_2919.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_3161.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_3346.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_3707.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_3800.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_440.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_4986.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-ibizan/n02091244_756.jpg"),
            Dog("https://images.dog.ceo/breeds/hound-walker/n02089867_149.jpg")
        )
    )
    DogsScreenInternal(
        uiState = uiState,
        onRetry = {},
        modifier = Modifier.fillMaxSize()
    )
}