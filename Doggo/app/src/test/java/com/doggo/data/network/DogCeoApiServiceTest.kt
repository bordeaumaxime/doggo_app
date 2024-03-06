package com.doggo.data.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doggo.data.network.model.BreedsApiResponse
import com.doggo.data.network.model.DogsApiResponse
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class DogCeoApiServiceTest {

    private lateinit var retrofitService: DogCeoApiService
    private val mockWebServer = MockWebServer()

    @Before
    fun before() {
        retrofitService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/api/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(DogCeoApiService::class.java)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun `given valid response when getAllBreeds then it returns valid Response of BreedsApiResponse`() =
        runTest {
            val mockResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(
                    """
            {
              "message": {
                "appenzeller": [],
                "australian": [
                  "shepherd"
                ],
                "bulldog": [
                  "boston", "english", "french"
                ],
                "chihuahua": []
              },
              "status": "success"
            }
            """.trimIndent()
                )
            mockWebServer.enqueue(mockResponse)

            val response = retrofitService.getAllBreeds()

            val request: RecordedRequest = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/api/breeds/list/all")
            assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
            assertThat(response.body()).isEqualTo(
                BreedsApiResponse(
                    breeds = mapOf(
                        "appenzeller" to emptyList(),
                        "australian" to listOf("shepherd"),
                        "bulldog" to listOf("boston", "english", "french"),
                        "chihuahua" to emptyList(),
                    )
                )
            )
        }

    @Test(expected = IOException::class)
    fun `given network issue when getAllBreeds then it throws IOException`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
            retrofitService.getAllBreeds()
        }

    @Test
    fun `given valid response when getRandomDogs then it returns valid Response of DogsApiResponse`() =
        runTest {
            val mockResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(
                    """
            {
              "message": [
                "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1.jpg",
                "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1066.jpg",
                "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1748.jpg"
              ],
              "status": "success"
            }
            """.trimIndent()
                )
            mockWebServer.enqueue(mockResponse)

            val response = retrofitService.getRandomDogs("hound", 3)

            val request: RecordedRequest = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/api/breed/hound/images/random/3")
            assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
            assertThat(response.body()).isEqualTo(
                DogsApiResponse(
                    imageUrls = listOf(
                        "https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg",
                        "https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg",
                        "https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg",
                    )
                )
            )
        }

    @Test(expected = IOException::class)
    fun `given network issue when getRandomDogs then it throws IOException`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
            retrofitService.getRandomDogs("hound", 3)
        }

    @Test
    fun `given valid response when getRandomDogs with sub breeds then it returns valid Response of DogsApiResponse`() =
        runTest {
            val mockResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(
                    """
            {
              "message": [
                "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1.jpg",
                "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1066.jpg",
                "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1748.jpg"
              ],
              "status": "success"
            }
            """.trimIndent()
                )
            mockWebServer.enqueue(mockResponse)

            val response = retrofitService.getRandomDogs("hound", "english", 3)

            val request: RecordedRequest = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/api/breed/hound/english/images/random/3")
            assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
            assertThat(response.body()).isEqualTo(
                DogsApiResponse(
                    imageUrls = listOf(
                        "https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg",
                        "https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg",
                        "https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg",
                    )
                )
            )
        }

    @Test(expected = IOException::class)
    fun `given network issue when getRandomDogs with sub breeds then it throws IOException`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
            retrofitService.getRandomDogs("hound", "english", 3)
        }
}