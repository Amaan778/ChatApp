package com.example.chatting_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class Registration : AppCompatActivity() {
    private lateinit var userName:EditText
    private lateinit var regemail:EditText
    private lateinit var regPass:EditText
    private lateinit var signin:Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = Firebase.auth
//        database = Firebase.database.reference
        database=FirebaseDatabase.getInstance().getReference()

        userName=findViewById(R.id.name)
        regemail=findViewById(R.id.email)
        regPass=findViewById(R.id.password)
        signin=findViewById(R.id.btnLogin)

        signin.setOnClickListener(View.OnClickListener {

            val mailss=regemail.text.toString()
            val passs=regPass.text.toString()
            val names=userName.text.toString()

            auth.createUserWithEmailAndPassword(mailss, passs)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        addDatabase(names,mailss,auth.currentUser?.uid!!)

                        startActivity(Intent(this,ChattingActivity::class.java))
                        Toast.makeText(this,"Registration Successfull", Toast.LENGTH_LONG).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this,"Registration UnSuccessfull", Toast.LENGTH_LONG).show()
                    }
                }

        })

    }

    private fun addDatabase(names: String, mailss: String, uid: String) {

        database.child("users").child(uid).setValue(ModelClass(names,mailss,uid))
    }
}