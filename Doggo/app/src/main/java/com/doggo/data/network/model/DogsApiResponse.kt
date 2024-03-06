package com.doggo.data.network.model

import com.google.gson.annotations.SerializedName

data class DogsApiResponse(@SerializedName("message") val imageUrls: List<String>)
