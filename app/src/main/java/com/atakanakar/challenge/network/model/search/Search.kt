package com.atakanakar.challenge.network.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Search(
    @Json(name = "Title")
    val title: String?,
    @Json(name = "Year")
    val year: String?,
    @Json(name = "imdbID")
    val imdbID: String?,
    @Json(name = "Type")
    val type: String?,
    @Json(name = "Poster")
    val poster: String?
): Serializable