package com.doggo.data.network

import com.doggo.data.network.model.BreedsApiResponse
import com.doggo.data.network.model.DogsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Performs request to the Dog CEO API.
 * See documentation [here](https://dog.ceo/dog-api/documentation/)
 */
interface DogCeoApiService {

    companion object {
        const val BASE_URL = "https://dog.ceo/api/"
    }

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsApiResponse>

    @GET("breed/{breed}/images/random/{count}")
    suspend fun getRandomDogs(@Path("breed") breed: String, @Path("count") count: Int): Response<DogsApiResponse>

    @GET("breed/{breed}/{sub_breed}/images/random/{count}")
    suspend fun getRandomDogs(
        @Path("breed") breed: String,
        @Path("sub_breed") subBreed: String,
        @Path("count") count: Int
    ): Response<DogsApiResponse>
}