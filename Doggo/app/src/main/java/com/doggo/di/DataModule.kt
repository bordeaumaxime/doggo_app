package com.doggo.di

import com.doggo.data.network.DogCeoApiService
import com.doggo.data.repository.BreedRepositoryImpl
import com.doggo.data.repository.DogRepositoryImpl
import com.doggo.domain.repository.BreedRepository
import com.doggo.domain.repository.DogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideBreedRepository(dogCeoApiService: DogCeoApiService): BreedRepository {
        return BreedRepositoryImpl(dogCeoApiService)
    }

    @Provides
    fun provideDogRepository(dogCeoApiService: DogCeoApiService): DogRepository {
        return DogRepositoryImpl(dogCeoApiService)
    }
}