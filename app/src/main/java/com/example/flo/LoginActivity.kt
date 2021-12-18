package com.example.flo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.`interface`.AuthService
import com.example.flo.adapter.getJwt
import com.example.flo.adapter.saveJwt
import com.example.flo.adapter.saveUserIdx
import com.example.flo.data.User
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.response.Auth
import com.example.flo.view.LoginView

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignupTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginLoginBtn.setOnClickListener {
            val imm : InputMethodManager = it.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.getWindowToken(), 0);
            login()
        }
    }

    private fun login(){
//        if(binding.loginId.text.toString().isEmpty() || binding.loginAddress.text.toString().isEmpty()){
//            Toast.makeText(this,"이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if(binding.loginPassword.text.toString().isEmpty()){
//            Toast.makeText(this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
//            return
//        }

        var email : String = binding.loginId.text.toString() + "@" + binding.loginAddress.text.toString()
        var pwd : String = binding.loginPassword.text.toString()
        val user : User = User(email, pwd, "")


        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(user)

//        val songDB = SongDatabase.getInstance(this)!!
//
//        val user = songDB.userDao().getUser(email,pwd)
//
//        user?.let { //user 가 null이 아닐때 실행
//            Log.d("LOGINACT/GET USER","userId : ${user.id}, $user")
//
//            //발급받은 jwt를 저장해주는 함수m
//            saveJwt(user.id)
//
//            Toast.makeText(this,"성공적으로 로그인이 되었습니다.",Toast.LENGTH_SHORT).show()
//        } ?: run {
//            Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginLoading() {
        binding.loginLoadingPb.visibility = View.VISIBLE
    }

    override fun onLoginSuccess(auth: Auth) {
        binding.loginLoadingPb.visibility = View.GONE

        saveJwt(this, auth.jwt)
        saveUserIdx(this, auth.userIdx)
        Log.e("LOGIN/JWT", getJwt(this))
        finish()
        startMainActivity()
    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoadingPb.visibility = View.GONE

        when(code){
            2015, 2019, 3014 -> {
                binding.loginErrorTv.visibility = View.VISIBLE
                binding.loginErrorTv.text = message
            }
        }
    }
}