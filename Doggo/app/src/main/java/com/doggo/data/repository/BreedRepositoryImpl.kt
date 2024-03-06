package com.doggo.data.repository

import com.doggo.data.network.DataNetworkCaller
import com.doggo.data.network.DogCeoApiService
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.domain.repository.BreedRepository
import com.doggo.domain.repository.DataResult

class BreedRepositoryImpl(private val dogCeoApiService: DogCeoApiService) :
    BreedRepository {

    override suspend fun getAllBreeds(): DataResult<List<Breed>> {
        return DataNetworkCaller.call(transform = {
            it.breeds.map { (breedName, subBreeds) ->
                Breed(
                    breedName,
                    subBreeds.map { subBreedName -> SubBreed(subBreedName) })
            }
        }, networkCall = { dogCeoApiService.getAllBreeds() })
    }
}