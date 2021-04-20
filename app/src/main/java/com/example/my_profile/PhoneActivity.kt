package com.example.my_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)
        mAuth = FirebaseAuth.getInstance()

        val number : EditText = findViewById(R.id.phoneNumberInput)
        val sendCode :  Button = findViewById(R.id.sendcode)

        sendCode.setOnClickListener {
            val phone = number.text.toString()
            sendVerificationCode(phone)
        }
    }

    fun sendVerificationCode(phonenumber : String) {
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(phonenumber)
            .setActivity(this)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(mCallBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallBack : PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                val intent = Intent(this@PhoneActivity, Verification::class.java)
                intent.putExtra("code",p0)
                startActivity(intent)
            }
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@PhoneActivity, p0.message, Toast.LENGTH_LONG).show()
            }
        }



}