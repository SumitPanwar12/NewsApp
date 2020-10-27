package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.security.KeyStore


class MainActivity : AppCompatActivity(), NewItemClicked {


    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        fetchData()

        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

    }

   private fun fetchData() {
       val url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=017745206c2646f9b1f79a91b01bec5c"
       val jsonObjectRequest = JsonObjectRequest(
           Request.Method.GET,
           url,
           null,
           Response.Listener {

               val newsJsonArray = it.getJSONArray("articles")
               val newsArray = ArrayList<News>()
               for(i in 0 until newsJsonArray.length())
               {
                   val newsJsonObject = newsJsonArray.getJSONObject(i)
                   val news = News(
                       newsJsonObject.getString("title"),
                       newsJsonObject.getString("author"),
                       newsJsonObject.getString("url"),
                       newsJsonObject.getString("urlToImage")
                   )
                   newsArray.add(news)
               }
               mAdapter.updatedNews(newsArray)

           },
           Response.ErrorListener {
               Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
           })


            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {

        val builder= CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}
