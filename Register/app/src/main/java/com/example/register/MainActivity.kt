package com.example.register
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity()
{
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var name: EditText
    private lateinit var storedToken:String

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val email: EditText = findViewById(R.id.eMail)
        val password:EditText = findViewById(R.id.password)
        val heading= findViewById<TextView>(R.id.heading)
        name = findViewById(R.id.eMail)
        val signIn:Button = findViewById(R.id.signIn)
        val signUp:Button = findViewById(R.id.signup)

        //Setting up an on-click listener
        signIn.setOnClickListener{
            // Calling Login API
            val queue = Volley.newRequestQueue(this)
            val url = "https://api-smartflo.tatateleservices.com/v1/auth/login"

            // Request a string response
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    val json = JSONObject(response)
                    val token = json.getString("access_token")

                    //Store token in sharedPreference
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                    editor = sharedPreferences.edit()
                    editor.putString(getString(R.string.name), token)
                    editor.commit()
                    storedToken = sharedPreferences.getString(getString(R.string.name), "").toString()
                    val intent = Intent(this,MainActivity2::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener {
                    heading.text = "INVALID!"
                }) {
                //adding params
                override fun getParams(): MutableMap<String, String>
                {
                    val params = HashMap<String, String>()
                    params.put("email", email.getText().toString())
                    params.put("password", password.getText().toString())
                    return params;
                }
            }
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
        //Redirects to Register Page
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterForm::class.java)
            startActivity(intent)
        }
    }
}

