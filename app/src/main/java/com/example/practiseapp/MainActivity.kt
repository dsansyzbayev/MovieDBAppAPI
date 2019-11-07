package com.example.practiseapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiseapp.model.Movie
import com.example.practiseapp.networking.RestApiService
import com.example.practiseapp.view.MovieAdapter
import com.example.practiseapp.viewmodel.LoginViewModel
import com.example.practiseapp.viewmodel.MainViewModel
import com.example.practiseapp.viewmodel.MovieRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.await
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val uiScope: CoroutineScope = CoroutineScope(coroutineContext)

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        // editTextUserName.setText("dsansyzbayev")
        // editTextPassword.setText("1234567589")

        buttonLogin.setOnClickListener{
            loginViewModel.login(
                username = editTextUserName.text.toString(),
                password = editTextPassword.text.toString()
            )
        }

        setData()
    }

    private fun setData(){
        loginViewModel.liveData.observe(this, Observer { result->
            when(result){
                is LoginViewModel.State.ShowLoading->{
                    progressBar.visibility = View.VISIBLE
                }
                is LoginViewModel.State.HideLoading ->{
                    progressBar.visibility = View.GONE
                }
                is LoginViewModel.State.ApiResult ->{
                    Log.d("activity_result",result.result)

                    val intent = Intent(this, ListActivity::class.java).apply {
                        putExtra("test", result.result)
                    }
                    startActivity(intent)
                }
                is LoginViewModel.State.Error->{

                }
            }
        })
    }

    override fun onDestroy(){
        super.onDestroy()
        job.cancel()
    }
}
