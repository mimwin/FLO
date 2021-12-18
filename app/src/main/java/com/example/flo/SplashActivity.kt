package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.`interface`.AuthService
import com.example.flo.adapter.getJwt
import com.example.flo.databinding.ActivitySplashBinding
import com.example.flo.view.AutoLoginView

class SplashActivity : AppCompatActivity(), AutoLoginView {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            Log.e("SPLASH/JWT", getJwt(this))
            val authService = AuthService()
            authService.setAutoLoginView(this)
            authService.autologin(getJwt(this))

        },2000) //2초를 기다린 후 {} 안을 실행한다.
    }

    override fun onAutoLoginLoading() {
        binding.splashLoadingPb.visibility = View.VISIBLE
    }

    override fun onAutoLoginSuccess() {
        binding.splashLoadingPb.visibility = View.GONE
        val intent = Intent(this,MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        binding.splashLoadingPb.visibility = View.GONE

        when(code){
            2002, 2001-> {
                val intent = Intent(this,LoginActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
    }
}