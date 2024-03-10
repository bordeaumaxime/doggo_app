package com.doggo.domain.repository

import com.doggo.domain.model.Dog

interface DogRepository {

    /**
     * Get [count] pictures of dogs from the given breed and optional sub breed.
     */
    suspend fun getRandomDogs(
        breedName: String,
        subBreedName: String?,
        count: Int
    ): DataResult<List<Dog>>
}