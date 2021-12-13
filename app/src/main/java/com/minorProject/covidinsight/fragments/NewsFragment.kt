package com.minorProject.covidinsight.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.minorProject.covidinsight.*

class NewsFragment : Fragment(), NewsItemClicked{

    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var newsList:ArrayList<NewsRVModel>
    private lateinit var newsRV: RecyclerView

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NewsViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)


        newsRV = view.findViewById(R.id.idRVNews) as RecyclerView
        newsList = ArrayList<NewsRVModel>()


        newsListAdapter = NewsListAdapter(this)
        newsRV.layoutManager = LinearLayoutManager(activity)
        newsRV.adapter = newsListAdapter

        fetchData()

        return view
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=ea27af924ef44aab8149230bad278f1b"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<NewsRVModel>()
                for (i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = NewsRVModel(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                newsListAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                Toast.makeText(getContext(), "Failed to get NEWS data", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this.requireActivity()).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(newsList: NewsRVModel){
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireActivity(), Uri.parse(newsList.url))
    }
}

