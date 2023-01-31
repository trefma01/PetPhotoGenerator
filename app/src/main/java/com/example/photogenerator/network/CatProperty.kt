package com.example.photogenerator.network

import com.squareup.moshi.Json

data class CatProperty(
    @Json(name = "id") var id: String,
    @Json(name = "url") var imgSrcUrl: String,
    @Json(name = "width") var width: Int,
    @Json(name = "height") var height: Int
)
