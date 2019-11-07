package com.example.practiseapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.practiseapp.networking.RestApiService
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

class LoginViewModel : ViewModel() {
    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val uiScope: CoroutineScope = CoroutineScope(coroutineContext)

    val liveData = MutableLiveData<State>()

    fun login(username: String, password: String){
        uiScope.launch {
            liveData.value = State.ShowLoading
            val result = withContext(Dispatchers.IO){
                val requestTokenResponse = RestApiService.RestApiService.createRequestToken().await()
                val requestToken = requestTokenResponse
                    .body()
                    ?.getAsJsonPrimitive("request_token")
                    ?.asString

                val body = JsonObject().apply{
                    addProperty("username",username)
                    addProperty("password",password)
                    addProperty("request_token",requestToken)
                }
                val loginResponse = RestApiService.RestApiService.login(body).await()
                loginResponse.body()?.toString() ?: "null"
            }
            Log.d("result_data_vm",result)

            liveData.value = State.HideLoading
            liveData.postValue(State.ApiResult(result))
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    sealed class State(){
        object ShowLoading:State()
        object HideLoading:State()
        data class ApiResult(val result: String): State()
        data class Error(val error: String): State()
    }
}