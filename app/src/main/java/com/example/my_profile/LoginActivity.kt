package com.example.my_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        val login = findViewById<Button>(R.id.loginButton)
        mAuth = FirebaseAuth.getInstance()
        login.setOnClickListener {

            val emailTxt = findViewById<EditText>(R.id.usernameInput)
            val passwordTxt = findViewById<EditText>(R.id.passwordInput)
            val email = emailTxt.text.toString()
            val password = passwordTxt.text.toString()
            if(email.isEmpty() )
            {
                emailTxt.error="Please enter valid email!"
                return@setOnClickListener
            }
            if(password.isEmpty() )
            {
                passwordTxt.error="Please enter valid password!"
                return@setOnClickListener
            }
            login(email,password)
        }
    }

        private fun login(email : String,password : String) {



            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
                    if(task.isSuccessful) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        Toast.makeText(this, "Login Success! ", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }

            }




}

