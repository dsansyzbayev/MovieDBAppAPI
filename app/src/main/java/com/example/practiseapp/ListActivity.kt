package com.example.practiseapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiseapp.model.Movie
import com.example.practiseapp.view.MovieAdapter
import com.example.practiseapp.viewmodel.LoginViewModel
import com.example.practiseapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class ListActivity : AppCompatActivity() {

    var mainViewModel: MainViewModel? = null
    var mMovieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        getPopularMovie()
        swiperefresh.setOnRefreshListener { getPopularMovie() }
    }

    fun getPopularMovie(){
        swiperefresh.isRefreshing = false
        mainViewModel!!.allMovie.observe(this, Observer { MovieList ->
            prepareRecyclerView(MovieList)
        })
    }

    private fun prepareRecyclerView(MovieList: List<Movie>?) {
        mMovieAdapter = MovieAdapter(MovieList)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            movieRecyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            movieRecyclerView.layoutManager = GridLayoutManager(this, 4)
        }
        movieRecyclerView.itemAnimator = DefaultItemAnimator()
        movieRecyclerView.adapter = mMovieAdapter
    }
}
