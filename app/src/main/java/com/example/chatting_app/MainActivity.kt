package com.example.chatting_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mail:EditText
    private lateinit var pass:EditText
    private lateinit var login:Button
    private lateinit var auth: FirebaseAuth
    private lateinit var user:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mail=findViewById(R.id.email)
        pass=findViewById(R.id.password)
        login=findViewById(R.id.btnLogin)
        user=findViewById(R.id.newuser)

        auth = Firebase.auth

        user.setOnClickListener {
            startActivity(Intent(this,Registration::class.java))
        }

        login.setOnClickListener(View.OnClickListener {

            val emails=mail.text.toString()
            val passw=pass.text.toString()

            auth.signInWithEmailAndPassword(emails, passw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(Intent(this,ChattingActivity::class.java))
                        Toast.makeText(this,"Login Successfull", Toast.LENGTH_LONG).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this,"Login UnSuccessfull", Toast.LENGTH_LONG).show()
                    }
                }

        })


    }
}