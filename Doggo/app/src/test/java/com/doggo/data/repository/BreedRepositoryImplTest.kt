package com.doggo.data.repository

import com.doggo.data.network.DogCeoApiService
import com.doggo.data.network.model.BreedsApiResponse
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.domain.repository.DataResult
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.IOException

class BreedRepositoryImplTest {

    private val dogCeoApiService = mock<DogCeoApiService>()
    private val breedRepository = BreedRepositoryImpl(dogCeoApiService)

    @Test
    fun `given valid API Response, when getAllBreeds, then returns list of breeds`() =
        runTest {
            whenever(
                dogCeoApiService.getAllBreeds()
            ).thenReturn(
                Response.success(
                    BreedsApiResponse(
                        breeds = mapOf(
                            "appenzeller" to emptyList(),
                            "australian" to listOf("shepherd"),
                            "bulldog" to listOf("boston", "english", "french"),
                            "chihuahua" to emptyList(),
                        )
                    )
                )
            )

            val result = breedRepository.getAllBreeds()

            assertThat(result).isEqualTo(
                DataResult.Success(
                    listOf(
                        Breed("appenzeller", emptyList()),
                        Breed("australian", listOf(SubBreed("shepherd"))),
                        Breed(
                            "bulldog",
                            listOf(
                                SubBreed("boston"),
                                SubBreed("english"),
                                SubBreed("french")
                            ),
                        ),
                        Breed("chihuahua", emptyList()),
                    )
                )
            )

        }

    @Test
    fun `given http error, when getAllBreeds, then returns unknown error`() =
        runTest {
            val mockResponse = mock<Response<BreedsApiResponse>> {
                on { body() } doReturn null
                on { code() } doReturn 404
            }
            whenever(
                dogCeoApiService.getAllBreeds()
            ).thenReturn(mockResponse)

            val result = breedRepository.getAllBreeds()

            assertThat(result).isEqualTo(
                DataResult.Error(
                    errorType = DataResult.ErrorType.UNKNOWN
                )
            )
        }

    @Test
    fun `given IOException, when getAllBreeds, then returns network error`() =
        runTest {
            whenever(dogCeoApiService.getAllBreeds()).thenAnswer {
                throw IOException("cannot reach server")
            }

            val result = breedRepository.getAllBreeds()

            assertThat(result).isEqualTo(
                DataResult.Error(
                    errorType = DataResult.ErrorType.NETWORK
                )
            )
        }
}