package com.example.photogenerator.network

import com.squareup.moshi.Json

data class DogProperty(
    @Json(name = "message") var imgSrcUrl: String,
    var status: String
)
