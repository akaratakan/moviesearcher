package com.atakanakar.challenge.network.model.id


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rating(
    @Json(name = "Source")
    val source: String?,
    @Json(name = "Value")
    val value: String?
)