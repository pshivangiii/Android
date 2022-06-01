package com.example.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.auth0.android.jwt.Claim
import com.auth0.android.jwt.JWT
import org.json.JSONObject
import java.lang.System.currentTimeMillis
import java.security.Timestamp
import java.util.*


class FrontPage : AppCompatActivity() {
//
//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var editor: SharedPreferences.Editor
//    private lateinit var e:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView=findViewById<TextView>(R.id.text)
//
//        e = sharedPreferences.getString(getString(R.string.name), "").toString()
        val bundle = intent.extras
        if (bundle != null){
            val refreshToken = "data = ${bundle.getString("data")}"
            Toast.makeText(this,"Hi", Toast.LENGTH_SHORT).show()

            //calling refresh api
            val queue = Volley.newRequestQueue(this)
            val url = "https://www.google.com"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                },
                Response.ErrorListener {
                    textView.text = "That didn't work!" })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)


        }



    }
}