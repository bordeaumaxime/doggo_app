package com.doggo.data.repository

import com.doggo.data.network.DataNetworkCaller
import com.doggo.data.network.DogCeoApiService
import com.doggo.domain.model.Dog
import com.doggo.domain.repository.DataResult
import com.doggo.domain.repository.DogRepository

class DogRepositoryImpl(private val dogCeoApiService: DogCeoApiService) :
    DogRepository {

    override suspend fun getRandomDogs(
        breedName: String,
        subBreedName: String?,
        count: Int
    ): DataResult<List<Dog>> {
        return DataNetworkCaller.call(transform = {
            it.imageUrls.map { imageUrl ->
                Dog(imageUrl = imageUrl)
            }
        }, networkCall = {
            if (subBreedName == null) {
                dogCeoApiService.getRandomDogs(breedName, count)
            } else {
                dogCeoApiService.getRandomDogs(breedName, subBreedName, count)
            }
        })
    }
}