package com.doggo.data.repository

import com.doggo.data.network.DogCeoApiService
import com.doggo.data.network.model.DogsApiResponse
import com.doggo.domain.model.Dog
import com.doggo.domain.repository.DataResult
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.IOException

class DogRepositoryImplTest {

    private val dogCeoApiService = mock<DogCeoApiService>()
    private val dogRepository = DogRepositoryImpl(dogCeoApiService)

    @Test
    fun `given valid API Response, when getRandomDogs, then returns list of dogs`() =
        runTest {
            whenever(
                dogCeoApiService.getRandomDogs("hound", 3)
            ).thenReturn(
                Response.success(
                    DogsApiResponse(
                        imageUrls = listOf(
                            "https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg",
                            "https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg",
                            "https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg",
                        )
                    )
                )
            )

            val result = dogRepository.getRandomDogs(
                breedName = "hound",
                subBreedName = null,
                count = 3
            )

            assertThat(result).isEqualTo(
                DataResult.Success(
                    listOf(
                        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
                        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg"),
                        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg"),
                    )
                )
            )

        }

    @Test
    fun `given http error, when getRandomDogs, then returns unknown error`() =
        runTest {
            val mockResponse = mock<Response<DogsApiResponse>> {
                on { body() } doReturn null
                on { code() } doReturn 404
            }
            whenever(
                dogCeoApiService.getRandomDogs("hound", 3)
            ).thenReturn(mockResponse)

            val result = dogRepository.getRandomDogs(
                breedName = "hound",
                subBreedName = null,
                count = 3
            )

            assertThat(result).isEqualTo(
                DataResult.Error(
                    errorType = DataResult.ErrorType.UNKNOWN
                )
            )
        }

    @Test
    fun `given IOException, when getRandomDogs, then returns network error`() =
        runTest {
            whenever(dogCeoApiService.getRandomDogs("hound", 3)).thenAnswer {
                throw IOException("cannot reach server")
            }

            val result = dogRepository.getRandomDogs(
                breedName = "hound",
                subBreedName = null,
                count = 3
            )

            assertThat(result).isEqualTo(
                DataResult.Error(
                    errorType = DataResult.ErrorType.NETWORK
                )
            )
        }

    @Test
    fun `given valid API Response, when getRandomDogs with sub breeds, then returns list of dogs`() =
        runTest {
            whenever(
                dogCeoApiService.getRandomDogs("hound", "english", 3)
            ).thenReturn(
                Response.success(
                    DogsApiResponse(
                        imageUrls = listOf(
                            "https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg",
                            "https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg",
                            "https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg",
                        )
                    )
                )
            )

            val result = dogRepository.getRandomDogs(
                breedName = "hound",
                subBreedName = "english",
                count = 3
            )

            assertThat(result).isEqualTo(
                DataResult.Success(
                    listOf(
                        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
                        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg"),
                        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg"),
                    )
                )
            )

        }

    @Test
    fun `given http error, when getRandomDogs with sub breeds, then returns unknown error`() =
        runTest {
            val mockResponse = mock<Response<DogsApiResponse>> {
                on { body() } doReturn null
                on { code() } doReturn 404
            }
            whenever(
                dogCeoApiService.getRandomDogs("hound", "english", 3)
            ).thenReturn(mockResponse)

            val result = dogRepository.getRandomDogs(
                breedName = "hound",
                subBreedName = "english",
                count = 3
            )

            assertThat(result).isEqualTo(
                DataResult.Error(
                    errorType = DataResult.ErrorType.UNKNOWN
                )
            )
        }

    @Test
    fun `given IOException, when getRandomDogs with sub breeds, then returns network error`() =
        runTest {
            whenever(dogCeoApiService.getRandomDogs("hound", "english", 3)).thenAnswer {
                throw IOException("cannot reach server")
            }

            val result = dogRepository.getRandomDogs(
                breedName = "hound",
                subBreedName = "english",
                count = 3
            )

            assertThat(result).isEqualTo(
                DataResult.Error(
                    errorType = DataResult.ErrorType.NETWORK
                )
            )
        }
}