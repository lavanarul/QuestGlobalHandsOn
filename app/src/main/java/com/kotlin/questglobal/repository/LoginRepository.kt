package com.kotlin.questglobal.repository

import com.kotlin.questglobal.models.Login
import com.kotlin.questglobal.network.RetroService

class LoginRepository constructor(private val retrofitService: RetroService) {

    suspend fun loginUser(login: Login) = retrofitService.loginApi(login)

}