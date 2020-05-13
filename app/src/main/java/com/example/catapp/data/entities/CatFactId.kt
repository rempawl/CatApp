package com.example.catapp.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatFactIdsContainer(val catIds: List<CatFactId>)

@JsonClass(generateAdapter = true)
data class CatFactId(val _id: String)
