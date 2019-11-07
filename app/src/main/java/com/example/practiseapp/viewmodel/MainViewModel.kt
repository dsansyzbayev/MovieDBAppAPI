package com.example.practiseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practiseapp.model.Movie

class MainViewModel() : ViewModel(){

    val movieRepository = MovieRepository()
    val allMovie: LiveData<List<Movie>> get() = movieRepository.getMutableLiveData()

    override fun onCleared(){
        super.onCleared()
        movieRepository.completableJob.cancel()
    }
}