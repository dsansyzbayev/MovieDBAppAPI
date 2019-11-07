package com.example.practiseapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name="id") var id: Int? = null,
    @Json(name="title") var title: String? = null,
    @Json(name="overview") var overview: String? = null,
    @Json(name="vote_average") var voteAverage: String? = null,
    @Json(name="poster_path") var posterPath: String? = null
): Parcelable {
}