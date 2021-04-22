package com.example.my_profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 120
    }


    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_sign_in)
        val button = findViewById<ImageView>(R.id.button1)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        button.setOnClickListener {
            signIn()
        }
        val login = findViewById<Button>(R.id.loginButton)
        val register = findViewById<TextView>(R.id.registerText)
        val phone = findViewById<ImageView>(R.id.phoneButton)
        register.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        login.setOnClickListener {
            loginf()
        }
        phone.setOnClickListener {
            startActivity(Intent(this, PhoneActivity::class.java))
        }
    }

    private fun loginf() {
        val emailTxt = findViewById<EditText>(R.id.usernameInput)
        val passwordTxt = findViewById<EditText>(R.id.passwordInput)
        val email = emailTxt.text.toString()
        val password = passwordTxt.text.toString()
        if (email.isEmpty()) {
            emailTxt.error = "Please enter valid email!"
        }
        else
        {
            login(email, password)
        }
        if (password.isEmpty()) {
            passwordTxt.error = "Please enter valid password!"
        }
        else
        {
            login(email, password)
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignInActivity", "signInWithCredential:success")
                        val intent = Intent(this, Dashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("SignInActivity", "signInWithCredential:failure", task.exception)
                    }
                }
    }

    private fun login(email: String, password: String) {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login Success! ", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login failed, please try again! ", Toast.LENGTH_LONG).show()
            }
        }

    }
}
