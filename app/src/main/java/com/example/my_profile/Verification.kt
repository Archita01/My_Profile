package com.example.my_profile

import android.content.Intent
import android.net.Credentials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class Verification : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        mAuth = FirebaseAuth.getInstance()

        val code = intent.getStringExtra("code")
        val input : EditText = findViewById(R.id.codeInput)
        val ver : Button = findViewById(R.id.verifyCode)

        ver.setOnClickListener {
            verify(code.toString(), input.text.toString())
        }
    }
    private fun verify( authCode: String , enteredCode : String) {
        val credential =  PhoneAuthProvider.getCredential(authCode, enteredCode)
        signInWithCredentials(credential)

    }
    private fun signInWithCredentials(credentials: PhoneAuthCredential){
        mAuth!!.signInWithCredential(credentials).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intent = Intent(this, Phone_Profile::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText( this@Verification, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}