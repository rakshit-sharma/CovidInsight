package com.minorProject.covidinsight.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.minorProject.covidinsight.*
import com.minorProject.covidinsight.Adapter.ViewHolder.ListSourceAdapter
import com.minorProject.covidinsight.Common.Common
import com.minorProject.covidinsight.Interface.NewsService
import com.minorProject.covidinsight.Model.WebSite
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Response

class NewsFragment : Fragment(){

    //Key=ea27af924ef44aab8149230bad278f1b
//    https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=ea27af924ef44aab8149230bad278f1b

    private lateinit var newsRV : RecyclerView
    private lateinit var newsRefresh : SwipeRefreshLayout


    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        newsRV = view.findViewById(R.id.recycler_view_source_news) as RecyclerView
        newsRefresh = view.findViewById(R.id.swipe_to_refresh)

        //Init cache DB
        Paper.init(activity)

        //Init Service
        mService = Common.newsService

        //Init View
        newsRefresh.setOnRefreshListener{
            loadWebsiteSource(true)
        }

        newsRV.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        newsRV.layoutManager = layoutManager

        dialog = SpotsDialog(activity)

        loadWebsiteSource(false)

        return view
    }

    private fun loadWebsiteSource(isRefresh: Boolean) {
        if(!isRefresh){
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null"){
                //Read cache
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                adapter = ListSourceAdapter(requireContext(), webSite) //earlier it was -> baseContext
                adapter.notifyDataSetChanged()
                newsRV.adapter = adapter
            }else{
                //Load website and write cache
                dialog.show()
                //Fetch new data
                mService.sources.enqueue(object :retrofit2.Callback<WebSite>{

                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(requireContext(), response!!.body()!!)  //try requireActivity()
                        adapter.notifyDataSetChanged()
                        newsRV.adapter = adapter

                        //Save to cache
                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                        dialog.dismiss()
                    }
                })
            }

        }else{
            newsRefresh.isRefreshing = true
            //Fetch new data
            mService.sources.enqueue(object :retrofit2.Callback<WebSite>{

                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(requireContext(), response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    newsRV.adapter = adapter

                    //Save to cache
                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                    newsRefresh.isRefreshing = false
                }
            })

        }
    }

}

