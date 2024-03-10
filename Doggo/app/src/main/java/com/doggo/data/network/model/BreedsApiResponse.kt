package com.doggo.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Example:
 * {
 *    "message": {
 *      "appenzeller": [],
 *      "australian": [
 *        "shepherd"
 *      ],
 *      "bulldog": [
 *        "boston", "english", "french"
 *      ],
 *      "chihuahua": []
 *    },
 *    "status": "success"
 *  }
 */
data class BreedsApiResponse(@SerializedName("message") val breeds: Map<String, List<String>>)
