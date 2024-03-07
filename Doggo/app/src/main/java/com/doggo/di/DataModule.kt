package com.doggo.di

import com.doggo.data.network.DogCeoApiService
import com.doggo.data.repository.BreedRepositoryImpl
import com.doggo.domain.repository.BreedRepository
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
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DogCeoApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDogCeoApiService(retrofit: Retrofit): DogCeoApiService {
        return retrofit.create(DogCeoApiService::class.java)
    }

    @Provides
    fun provideBreedRepository(dogCeoApiService: DogCeoApiService): BreedRepository {
        return BreedRepositoryImpl(dogCeoApiService)
    }
}