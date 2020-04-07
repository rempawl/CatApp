package com.example.catapp.data

import com.squareup.moshi.Json

data class CatFact(
    @Json(name = "_id") val id: Long,
    @Json(name = "text") val text: String,
    @Json(name = "updatedAt") val updateDate: String
)