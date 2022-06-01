package com.example.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterForm : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        val signIn = findViewById<TextView>(R.id.signIn)
        val signUp: Button = findViewById(R.id.signup)
        val email:EditText=findViewById(R.id.eMail)
        val inputEmail:String=email.getText().toString()
        val password:EditText=findViewById(R.id.password)
        val inputPassword=password.getText().toString()
        val text:TextView=findViewById(R.id.Email)
        signUp.setOnClickListener{
            val db = DBHelper(this, null)
            db.addUser(email.toString(), password.getText().toString())
            val cursor = db.getUser()
            cursor!!.moveToFirst()
            while(cursor.moveToNext()) {

                // at last we close our cursor
                Toast.makeText(this,(cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD_COL)) + "\n"), Toast.LENGTH_SHORT).show()
//                (cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
            }


            cursor.close()

//
//            Toast.makeText(this,getData, Toast.LENGTH_SHORT).show()

        }




        signIn.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}