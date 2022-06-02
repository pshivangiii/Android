package com.example.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class NewRegister: AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        val signIn = findViewById<TextView>(R.id.signIn)
        val signUp: Button = findViewById(R.id.signup)
        val email:EditText=findViewById(R.id.eMail)
        val password:EditText=findViewById(R.id.password)
        val confirmPassword:EditText=findViewById(R.id.confirmPassword)
        signUp.setOnClickListener {
            if(confirmPassword.toString().equals( password.toString()))
            {
                Toast.makeText(this,"Password didn't match", Toast.LENGTH_SHORT).show()
            }
            val db = DBHelper(this, null)
            val addUser=db.addUser(email.text.toString(), password.text.toString())
            if(addUser)
            {
                Toast.makeText(this,"User Added Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NewLogin::class.java)
                startActivity(intent)
            }
        }
        signIn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}