package com.example.practiseapp.networking

import android.util.Log
import com.example.practiseapp.model.MovieWrapper
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.*
import java.util.concurrent.TimeUnit


object RestApiService {

  //  @GET("/api/feed.json")
   // fun getPopularBlog(): Deferred<BlogWrapper>
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "482155ecb220aa83ad889ffedfd4b157"

        val RestApiService: MovieDBApi by lazy{
            val logging = HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message -> Log.d("okHttp", message)  }
            ).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val okHttp = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val newUrl = chain.request().url()
                        .newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()
                    val newRequest = chain.request()
                        .newBuilder()
                        .url(newUrl)
                        .build()
                    chain.proceed(newRequest)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return@lazy retrofit.create(MovieDBApi::class.java)
        }
}