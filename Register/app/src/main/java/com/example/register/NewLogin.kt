package com.example.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class NewLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_login)

        var email: EditText = findViewById(R.id.eMail)
        var password = findViewById(R.id.password) as EditText
        var inputEmail=email.getText().toString()
        var inputPassword=password.getText().toString()
        val textView = findViewById<TextView>(R.id.text)
        val login = findViewById<Button>(R.id.signIn)
        val signUp = findViewById<TextView>(R.id.signup)

        login.setOnClickListener{
            val db=DBHelper(this,null)
            val cursor=db.getSpecificUser(email.toString())
            if(cursor == true) {
                Toast.makeText(this, cursor.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}