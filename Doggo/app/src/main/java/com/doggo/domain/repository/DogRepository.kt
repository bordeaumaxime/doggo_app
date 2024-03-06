package com.doggo.domain.repository

import com.doggo.domain.model.Dog

interface DogRepository {

    suspend fun getRandomDogs(
        breedName: String,
        subBreedName: String?,
        count: Int
    ): DataResult<List<Dog>>
}