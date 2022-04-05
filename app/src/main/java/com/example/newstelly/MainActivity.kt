package com.example.newstelly

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

// 2 types of scrolling view ..1 listView and RecyclerView
//Recycler view is an optimization over list view
//list view me space optimization karke Recycler view banaya
//recycler view 1 third party library hai ..and jitni bhi 3rd party lib ko use karne ke lie hume uski
//dependancy ki build.gradle(app) me entry karni padegi

//Recycler View internal working ...see flow chart on google..Layout Manager, RecyclerView, Adapter,ViewHolder

// every layout (ViewGroup) me ya to viewGroups (linearLayout, vertical Layout etc) hote hai
//ya to views (buttons,switch etc..)..again every ViewGroups(vg) can contain another vg/views
//views cant contain another vg/views.

//activity_main.xml contains our RecyclerView Group jisme hume humari items ke views bhi dalna padega
//items matlab jo display karana hai uske views..for this we have taken a new layout file (item_news)


//implements NewsItemClicked
class MainActivity : AppCompatActivity() {

    private lateinit var mMyAdaptor  : NewsListAdaptor //declaring class variable ..but not initialized so we have to
    //declare it as lateinit

    //kisi bhi varialble ke age agar "m" laga de to woh member variable ban jata hai matlab woh
    //kisi bhi jagah se accessable hai ..eg. m + MyAdaptor = mMyAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // linear, horizontal, grid , staggered LayOut managers bhi hote hai
        //they basically specify ke scrolling me images/data kaise set karni hai

        val myRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        myRecyclerView.layoutManager = LinearLayoutManager (this)

        fetchData()

        mMyAdaptor = NewsListAdaptor(this) //declaring adaptor class ka instance

        myRecyclerView.adapter = mMyAdaptor //linking/binding the myAdaptor to the recycler view
    }

    private fun fetchData() {
        val newsUrl = "https://newsdata.io/api/1/news?apikey=pub_327035c25356bc25f83b8f05a5dce002d2e2&q=science"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, newsUrl, null,
        //Response.Listener
            {
            val newsJsonArray = it.getJSONArray("results") //this "articles" is in the api url
            //observe (open url) that in "articles" in Jsonarray

            val newsArray = ArrayList<News>() //ArrayList of News type (which is a class of "data" type)

            for (i in 0 until newsJsonArray.length() - 1){
                val newsJsonObject = newsJsonArray.getJSONObject(i)
                val myNews = News ( //passing the data to News in order
                    newsJsonObject.getString("title"), //Extracting data from API url that we want
                    newsJsonObject.getString("creator"), //All these names are from the API url
                    newsJsonObject.getString("link"),
                    newsJsonObject.getString("image_url")
                )
                newsArray.add(myNews)
            }
            mMyAdaptor.updateNews(newsArray)
        },
        //Response.ErrorListener
        {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
        })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    //we have to override onItemClicked function from the interface(NewItemClicked) we created in
    //NewsListAdaptor..bcz we are passing the context of this activity as object of that interface
//    override fun onItemClicked(item: News) {
////        Toast.makeText(this, "item cLicked $item", Toast.LENGTH_LONG).show()
//
//        val builder = CustomTabsIntent.Builder()
//        val customTabsIntent = builder.build()
//        customTabsIntent.launchUrl(this, Uri.parse(item.url))
//    }
}