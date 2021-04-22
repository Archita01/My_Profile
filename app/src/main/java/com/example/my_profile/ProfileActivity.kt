package com.example.my_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import javax.xml.namespace.NamespaceContext

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference
    lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val logout = findViewById<Button>(R.id.logoutButton)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        mDatabase = database.reference.child("Names")
        loadProfile()
        logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun loadProfile()
    {
        val currentuser = auth.currentUser

        val userreference = mDatabase.child(currentuser?.uid!!)
        val dispTxt = findViewById<TextView>(R.id.nameText)
        val emailt = findViewById<TextView>(R.id.emailText)
        userreference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dispTxt.text = snapshot.child("Name").value.toString()
                emailt.text = snapshot.child("Email").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}