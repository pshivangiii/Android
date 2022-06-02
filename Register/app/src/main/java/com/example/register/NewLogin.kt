package com.example.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class NewLogin : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_login)
        val email: EditText = findViewById(R.id.eMail)
        val password = findViewById(R.id.password) as EditText
        val inputEmail=email.getText().toString()
        val inputPassword=password.getText().toString()
        val textView = findViewById<TextView>(R.id.heading)
        val login = findViewById<Button>(R.id.signIn)
        val signUp = findViewById<TextView>(R.id.signup)

        login.setOnClickListener{
            val db=DBHelper(this,null)
            val checkUser=db.checkUsernameAndPassword(email.text.toString(),password.text.toString())
            if(checkUser == false)
            {
                Toast.makeText(this,"NO SUCH USER FOUND ", Toast.LENGTH_SHORT).show()
            }
                Toast.makeText(this,"LOGGED IN", Toast.LENGTH_SHORT).show()
        }
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterForm::class.java)
            startActivity(intent)
        }
    }
}