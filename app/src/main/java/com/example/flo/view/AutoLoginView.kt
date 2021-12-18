package com.example.flo.view

interface  AutoLoginView {
    fun onAutoLoginLoading()
    fun onAutoLoginSuccess()
    fun onAutoLoginFailure(code : Int, message : String)
}