package com.example.flo.view

import com.example.flo.response.Auth

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code : Int, message : String)
}