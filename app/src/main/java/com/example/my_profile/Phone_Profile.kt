package com.example.my_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Phone_Profile : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone__profile)

        mAuth = FirebaseAuth.getInstance()

        val num : TextView = findViewById(R.id.number)
        val out : Button = findViewById(R.id.Out)

        num.text = mAuth?.currentUser?.phoneNumber.toString()
        out.setOnClickListener {
            mAuth!!.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        }
    }
}