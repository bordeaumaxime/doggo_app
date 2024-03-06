package com.doggo.data.network.model

import com.google.gson.annotations.SerializedName

data class BreedsApiResponse(@SerializedName("message") val breeds: Map<String, List<String>>)
