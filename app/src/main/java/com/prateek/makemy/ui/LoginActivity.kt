package com.prateek.makemy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.prateek.makemy.R
import com.prateek.makemy.base.BaseActivity
import com.prateek.makemy.base.MmtApp
import com.prateek.makemy.databinding.ActivityLoginBinding
import com.prateek.makemy.databinding.ActivityMainBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isEmailCorrect = false
    private var isPasswordEntered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // add text watcher to validate data
        binding.etEmail.doOnTextChanged{text, _,_,_ ->
            if(Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                isEmailCorrect = true
                binding.checkEmail.visibility = View.VISIBLE
            }else{
                isEmailCorrect = false
                binding.checkEmail.visibility = View.INVISIBLE
            }
        }

        binding.etPassword.doOnTextChanged{text, _,_,_ ->
            if(!TextUtils.isEmpty(text)){
                isPasswordEntered = true
                binding.checkPassword.visibility = View.VISIBLE
            }else{
                isPasswordEntered = false
                binding.checkPassword.visibility = View.INVISIBLE
            }
        }

        // on button login
        binding.buttonLogin.setOnClickListener{
            if(!isEmailCorrect)
                Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show()
            else if(!isPasswordEntered)
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            else{
                // sign in user
                (application as MmtApp).userSignedIn = true
                (application as MmtApp).username = binding.etEmail.text.toString()
                Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }


        //on other buttons
        binding.tvSignUp.setOnClickListener{
            Toast.makeText(this, "Yet to be Implemented", Toast.LENGTH_SHORT).show()
        }
        binding.tvPrivacyPolicy.setOnClickListener{
            Toast.makeText(this, "Yet to be Implemented", Toast.LENGTH_SHORT).show()
        }
        binding.tvForgotPass.setOnClickListener{
            Toast.makeText(this, "Yet to be Implemented", Toast.LENGTH_SHORT).show()
        }



    }
}