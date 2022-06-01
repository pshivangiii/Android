package com.example.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val textView=findViewById<TextView>(R.id.textView)

        val bundle = intent.extras
        if (bundle != null){
            textView.text = "data = ${bundle.getString("data")}"
        }
    }
}