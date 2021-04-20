package com.example.my_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            setContentView(R.layout.activity_sign_in)
            val googleSignIn = findViewById<Button>(R.id.button1)
            val login = findViewById<Button>(R.id.loginButton)
            val register = findViewById<TextView>(R.id.registerText)
            val phone = findViewById<Button>(R.id.phoneButton)
            googleSignIn.setOnClickListener {
                if (user != null) {
                    val dashboardIntent = Intent(this, Dashboard::class.java)
                    startActivity(dashboardIntent)
                    finish()
                } else {
                    val intent1 = Intent(this, SignInActivity::class.java)
                    startActivity(intent1)
                    finish()
                }
            }
            register.setOnClickListener {
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()

            }
            login.setOnClickListener {
                if (user != null) {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            phone.setOnClickListener {
                if (user != null) {
                    startActivity(Intent(this, Phone_Profile::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, PhoneActivity::class.java))
                    finish()
                }

            }
        }, 2000)

    }
}