package com.example.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MyFragment : Fragment()
{
    val data = ArrayList<ItemsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View?
    {
        val view : View = inflater.inflate(R.layout.fragment_my, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
    }

    fun callContactApi(context: Context, token:String?, page:Int)
    {
        //CALLING CONTACT API
        val queue = Volley.newRequestQueue(context)
        val progressBar : ProgressBar? = view?.findViewById(R.id.progressBar)
        val url = "https://api-smartflo.tatateleservices.com/v1/contacts?page=$page"
        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String>
            { response ->
                val json = JSONObject(response)
                val size = json.getString("size")
                val currentPage= json.getString("page")
                val jsonArray = json.getJSONArray("results")
                val recyclerview : RecyclerView? = view?.findViewById(R.id.recyclerview)
                recyclerview?.layoutManager = LinearLayoutManager(context)

                for (i in 0 until size.toInt())
                {
                    progressBar?.visibility = View.INVISIBLE
                    val jsonObj = jsonArray.getJSONObject(i)
                    val name = jsonObj.getString("name")
                    val phoneNumber = jsonObj.getString("phone_number")
                    Toast.makeText(context,currentPage, Toast.LENGTH_SHORT).show()
                    data.add(ItemsViewModel("NAME : " + name, "phoneNumber : " + phoneNumber))
                }
                progressBar?.visibility = View.VISIBLE
                val adapter = CustomAdapter(data)
                recyclerview?.adapter = adapter
                adapter.notifyDataSetChanged();

                if(size.toInt() == 0)
                {
                    progressBar?.visibility = View.INVISIBLE
                }
            },
            Response.ErrorListener
            {
                Toast.makeText(context, "that didn't work", Toast.LENGTH_SHORT).show()
            }) {
            //adding header
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization", "Bearer $token")
                return headers
            }
        }
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
