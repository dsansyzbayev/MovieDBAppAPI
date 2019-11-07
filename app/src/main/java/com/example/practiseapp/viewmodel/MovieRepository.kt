package com.example.practiseapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.practiseapp.model.Movie
import com.example.practiseapp.networking.RestApiService
import kotlinx.coroutines.*
import retrofit2.HttpException

class MovieRepository {
    private var movies = mutableListOf<Movie>()
    private var mutableLiveData = MutableLiveData<List<Movie>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO+completableJob)

    fun getMutableLiveData():MutableLiveData<List<Movie>>{
        coroutineScope.launch {
            val request = RestApiService.RestApiService.getPopularMovies(page=1)
            withContext(Dispatchers.Main){
                try{
                    val response = request.await()
                    val mMovieWrapper = response
                    if (mMovieWrapper != null && mMovieWrapper.movies != null) {
                        movies = mMovieWrapper.movies
                        mutableLiveData.value=movies;
                    } else {

                    }
                } catch (e: HttpException){
                    Log.d("httpexception",e.printStackTrace().toString())
                } catch (e: Throwable){
                    Log.d("throwable", e.printStackTrace().toString())
                }
            }
        }
        return mutableLiveData
    }
}