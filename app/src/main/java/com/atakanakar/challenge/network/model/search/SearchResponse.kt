package com.atakanakar.challenge.network.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "Search")
    val search: List<Search>,
    @Json(name = "totalResults")
    val totalResults: String?,
    @Json(name = "Response")
    val response: String?
):Serializable