package com.example.practiseapp.networking

import com.example.practiseapp.model.MovieWrapper
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieDBApi {
    @GET("authentication/token/new")
    fun createRequestToken(): Deferred<Response<JsonObject>>

    @POST("authentication/token/validate_with_login")
    fun login(@Body body: JsonObject): Deferred<Response<JsonObject>>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Deferred<MovieWrapper>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int) : Call<JsonObject>

}