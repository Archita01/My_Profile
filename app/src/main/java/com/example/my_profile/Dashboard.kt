package com.example.my_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Dashboard : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val signout = findViewById<Button>(R.id.sign_out)
        val name = findViewById<TextView>(R.id.nameView)
        val email = findViewById<TextView>(R.id.emailView)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        name.text= currentUser?.displayName
        email.text= currentUser?.email
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN )
        signout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                signout.visibility = View.INVISIBLE
            }
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}