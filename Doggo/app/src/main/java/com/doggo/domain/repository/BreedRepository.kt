package com.doggo.domain.repository

import com.doggo.domain.model.Breed

interface BreedRepository {

    suspend fun getAllBreeds(): Result<List<Breed>>
}