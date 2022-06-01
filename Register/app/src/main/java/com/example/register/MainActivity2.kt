package com.example.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.auth0.android.jwt.JWT
import org.json.JSONObject
import java.util.HashMap

class MainActivity2 : AppCompatActivity()
{
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sharedPreferences.getString(getString(R.string.name), "").toString()
        //            val token=""

        if (token.isNullOrEmpty())
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if (token != "")
        {
            val currentTime = System.currentTimeMillis() / 1000
            val jwt = JWT(token)
            var claim: String? = jwt.getClaim("exp").asString()
            var diffrence = ((claim?.toInt())?.minus((currentTime?.toInt()!!)))
            var expiryTimeInMinutes = diffrence?.div(60)
            var t = claim?.toInt()

            //   Check for 5 min condition
                if (expiryTimeInMinutes != null) {
                    if (expiryTimeInMinutes > 55) {
                        // Toast.makeText(this, "More than 5 min remaining", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()

                        //Calling Refresh API
                        val queue = Volley.newRequestQueue(this)
                        val url = " https://api-smartflo.tatateleservices.com/v1/auth/refresh"
                        val stringRequest = object : StringRequest(Request.Method.POST, url,
                            Response.Listener<String>
                            { response ->
                                val jsonResponse = JSONObject(response)
                                val newToken = jsonResponse.getString("access_token")
                                Toast.makeText(this,"Token refreshed", Toast.LENGTH_SHORT).show()
                            },
                            Response.ErrorListener
                            {
                                Toast.makeText(this, "INVALID", Toast.LENGTH_SHORT).show()
                            }) {
                            //adding header
                            override fun getHeaders(): Map<String, String> {
                                val headers = HashMap<String, String>()
                                headers.put("Content-Type", "application/json");
                                headers.put("Authorization", "Bearer " + token.toString())
                                return headers
                            }
                        }
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest)
                    }
            }
        }
    }
}


