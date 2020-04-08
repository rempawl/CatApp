package com.example.catapp.data

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CatFact(
    val text: String,
    val updatedAt: String
) {
    fun formatDate(): String {

        return updatedAt
            .split("T")[0]
            .split("-")
            .reversed()
            .joinToString(" - ")
    }

}