package com.example.practiseapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class MovieWrapper(
    @Json(name="page") val page: Int? = null,
    @Json(name="total_results") val totalResults: Int? = null,
    @Json(name="total_pages") val totalPages: Int? = null,
    @Json(name="result") val movies: MutableList<Movie>? = null
):Parcelable {
}