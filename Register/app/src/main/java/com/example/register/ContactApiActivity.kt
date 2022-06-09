package com.example.register

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class ContactApiActivity : AppCompatActivity()
{
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var scrollView: NestedScrollView
    private lateinit var myFragment: MyFragment
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_api)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var token = sharedPreferences.getString(getString(R.string.name), "")
        scrollView = findViewById(R.id.idNestedSV);
        myFragment = MyFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, myFragment, "MyFragment")
            .commit()

        myFragment.callContactApi(this, token, page)
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight)
            {
                page += 1
                myFragment.callContactApi(this, token, page)
            }
        })
    }
} 

