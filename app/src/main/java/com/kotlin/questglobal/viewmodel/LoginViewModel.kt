package com.kotlin.questglobal.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.questglobal.models.GetEventResponse
import com.kotlin.questglobal.models.Login
import com.kotlin.questglobal.repository.LoginRepository
import kotlinx.coroutines.*

class LoginViewModel constructor(private val loginRepository: LoginRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val loginResponse = MutableLiveData<GetEventResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun loginUser(login: Login) {
        job = CoroutineScope(Dispatchers.IO ).launch {
            val response = loginRepository.loginUser(login)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loginResponse.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}