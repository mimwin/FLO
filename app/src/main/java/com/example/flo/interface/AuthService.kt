package com.example.flo.`interface`

import android.util.Log
import com.example.flo.data.User
import com.example.flo.getRetrofit
import com.example.flo.response.AuthResponse
import com.example.flo.view.AutoLoginView
import com.example.flo.view.LoginView
import com.example.flo.view.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    private lateinit var signUpView : SignUpView
    private lateinit var loginView : LoginView
    private lateinit var autologinView : AutoLoginView

    private val AuthService = getRetrofit().create(AuthRetrofitInterface::class.java)

    fun setLoginView(loginView : LoginView){
        this.loginView = loginView
    }

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setAutoLoginView(autologinView : AutoLoginView){
        this.autologinView = autologinView
    }

    fun signUp(user: User){

        signUpView.onSignUpLoading()

        AuthService.signUp(user).enqueue(object: Callback<AuthResponse> {

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val signUpResponse: AuthResponse = response.body()!!

                Log.e("SignUP/API",signUpResponse.toString())

                when(signUpResponse.code){
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(signUpResponse.code, signUpResponse.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                signUpView.onSignUpFailure(400, "네트워크 오류가 발생했습니다.")
            }

        })
    }

    fun login(user : User){

        loginView.onLoginLoading()

        AuthService.login(user).enqueue(object: Callback<AuthResponse> {

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val LoginResponse: AuthResponse = response.body()!!

                Log.e("LOGIN/API",LoginResponse.toString())

                when(LoginResponse.code){
                    1000 -> loginView.onLoginSuccess(LoginResponse.result!!)
                    else -> loginView.onLoginFailure(LoginResponse.code, LoginResponse.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                loginView.onLoginFailure(400, "네트워크 오류가 발생했습니다.")
            }

        })
    }

    fun autologin(jwt : String){
        autologinView.onAutoLoginLoading()

        AuthService.autoLogin(jwt).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val autoLoginResponse: AuthResponse = response.body()!!

                Log.e("AUTOLOGIN/API",autoLoginResponse.toString())

                when(autoLoginResponse.code){
                    1000 -> autologinView.onAutoLoginSuccess()
                    else -> autologinView.onAutoLoginFailure(autoLoginResponse.code,autoLoginResponse.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                autologinView.onAutoLoginFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }
}