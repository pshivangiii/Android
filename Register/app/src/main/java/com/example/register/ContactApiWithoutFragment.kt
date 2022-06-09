package com.example.register

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class ContactApiWithoutFragment : AppCompatActivity()
{
    val data = ArrayList<ItemsViewModel>()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var  recyclerView: RecyclerView
    var pageNumber=1
    val recordsThreshold = 5

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_api)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sharedPreferences.getString(getString(R.string.name), "")
        recyclerView=findViewById(R.id.recyclerview)
        getData(token,pageNumber)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
            {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalCount = linearLayoutManager.itemCount
                val lastVisibleITem = linearLayoutManager.findLastVisibleItemPosition()
                if(totalCount <= lastVisibleITem + recordsThreshold)
                {
                    pageNumber++
                    getData(token,pageNumber)
                }
            }
        })
    }

    private fun getData(token: String?, page: Int) {

        //CALLING CONTACT API
        val queue = Volley.newRequestQueue(this)
        val progressBar:ProgressBar= findViewById(R.id.progressBar)
        val url = "https://api-smartflo.tatateleservices.com/v1/contacts?page=$page"

        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String>
            { response ->
                val json = JSONObject(response)
                val size = json.getString("size")
                val jsonArray = json.getJSONArray("results")
                if (size.toInt() == 0)
                {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "No more data", Toast.LENGTH_SHORT).show()

                }
                else
                {
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    for (i in 0 until jsonArray.length())
                    {
                        progressBar.visibility = View.INVISIBLE
                        val jsonObj = jsonArray.getJSONObject(i)
                        val name = jsonObj.getString("name")
                        val phoneNumber = jsonObj.getString("phone_number")
                        data.add(ItemsViewModel("NAME : " + name, "phoneNumber : " + phoneNumber))
                    }
                    progressBar.visibility = View.VISIBLE
                    val adapter = CustomAdapter(data)
                    // Setting the Adapter with the recyclerview
                    recyclerView.adapter = adapter
                    adapter.setOnLoadMoreListener()
                    adapter.notifyDataSetChanged()
                }
            },
            Response.ErrorListener
            {
                Toast.makeText(this, "INVALID", Toast.LENGTH_SHORT).show()
            })
        {
            //adding header
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer " + token.toString()
                return headers
            }
        }
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
} 

