package com.doggo.domain.repository

import com.doggo.domain.model.Breed

interface BreedRepository {

    /**
     * Get all the available dog breeds
     */
    suspend fun getAllBreeds(): DataResult<List<Breed>>
}