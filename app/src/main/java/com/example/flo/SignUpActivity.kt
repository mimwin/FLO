package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.RoomDB.SongDatabase
import com.example.flo.`interface`.AuthService
import com.example.flo.data.User
import com.example.flo.databinding.ActivitySignupBinding
import com.example.flo.view.SignUpView

class SignUpActivity : AppCompatActivity(), SignUpView {

    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupSignUpButton.setOnClickListener {
            signUp()
        }
    }

    private fun getUser() : User {
        var email : String = binding.signupId.text.toString() + "@" + binding.signupAddress.text.toString()
        var pwd : String = binding.signupPassword.text.toString()
        var name : String = binding.signupName.text.toString()

        return User(email,pwd,name)
    }

    private fun signUp(){
//        if(binding.signupId.text.toString().isEmpty() || binding.signupAddress.text.toString().isEmpty()){
//            binding.signupErrorEmailTv.visibility = View.VISIBLE
//            binding.signupErrorEmailTv.text = "입력값을 확인해주세요."
//        }
//
        if(binding.signupPassword.text.toString() != binding.signupPasswordCheck.text.toString()){
            binding.signupErrorPasswordTv.visibility = View.VISIBLE
            binding.signupErrorPasswordTv.text = "비밀번호가 일치하지 않습니다."
        }
//
//        else if(binding.signupName.text.toString().isEmpty()){
//            binding.signupErrorNameTv.visibility = View.VISIBLE
//            binding.signupErrorNameTv.text = "입력값을 확인해주세요."
//        }


        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        val authService = AuthService()
        authService.setSignUpView(this)
        authService.signUp(getUser())

    }

    override fun onSignUpLoading() {
        binding.singupLoadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.singupLoadingPb.visibility = View.GONE
        finish()
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.singupLoadingPb.visibility = View.GONE

        when(code){
            2016, 2017 -> {
                binding.signupErrorEmailTv.visibility = View.VISIBLE
                binding.signupErrorEmailTv.text = message
            }
        }
    }

}