package com.doggo.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Example:
 * {
 *     "message": [
 *       "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1.jpg",
 *       "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1066.jpg",
 *       "https:\/\/images.dog.ceo\/breeds\/hound-english\/n02089973_1748.jpg"
 *     ],
 *     "status": "success"
 *   }
 */
data class DogsApiResponse(@SerializedName("message") val imageUrls: List<String>)
