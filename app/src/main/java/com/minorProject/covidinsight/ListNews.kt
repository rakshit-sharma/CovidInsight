package com.minorProject.covidinsight

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.flaviofaria.kenburnsview.KenBurnsView
import com.github.florent37.diagonallayout.DiagonalLayout
import com.minorProject.covidinsight.Adapter.ViewHolder.ListNewsAdapter
import com.minorProject.covidinsight.Common.Common
import com.minorProject.covidinsight.Interface.NewsService
import com.minorProject.covidinsight.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source = ""
    var webHotUrl:String?=""

    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var diagonalLayout: DiagonalLayout
    private lateinit var listNews: RecyclerView
    private lateinit var topTitle: TextView
    private lateinit var topAuthor: TextView
    private lateinit var topImage: KenBurnsView
    lateinit var dialog:AlertDialog
    lateinit var mService:NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        topImage = findViewById(R.id.top_image)
        topTitle = findViewById(R.id.top_title)
        topAuthor = findViewById(R.id.top_author)
        
        //init view
        mService = Common.newsService

        dialog = SpotsDialog(this)

        swipeToRefresh = findViewById(R.id.swipe_to_refresh)
        swipeToRefresh.setOnRefreshListener { loadNews(source, true) }

        diagonalLayout = findViewById(R.id.diagonalLayout)
        diagonalLayout.setOnClickListener{
            val detail = Intent(baseContext, NewsDetail::class.java)
            detail.putExtra("webURL", webHotUrl)
            startActivity(detail)
        }

        listNews = findViewById(R.id.list_news)
        listNews.setHasFixedSize(true)
        listNews.layoutManager = LinearLayoutManager(this)


        if (intent != null){
            source = intent.getStringExtra("source").toString()
            if (!source.isEmpty()){
                loadNews(source, false)
            }
        }
    }

    private fun loadNews(source: String, isRefreshed: Boolean) {
        if (isRefreshed){
            dialog.show()
            mService.getNewsFromSource(Common.getNewsApi(source))
                .enqueue(object : Callback<News>{

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        dialog.dismiss()

                        //Get first article to hot news
                        Picasso.get()
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(topImage)

                        topTitle.text = response.body()!!.articles!![0].title
                        topAuthor.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url

                        //Load all remain articles
                        val removeFirstItem = response.body()!!.articles
                        //Beause we get first item to hot news, so we need to remove it
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                        adapter.notifyDataSetChanged()
                        listNews.adapter = adapter
                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }else{
            swipeToRefresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsApi(source))
                .enqueue(object : Callback<News>{

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipeToRefresh.isRefreshing = false

                        //Get first article to hot news
                        Picasso.get()
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(topImage)

                        topTitle.text = response.body()!!.articles!![0].title
                        topAuthor.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url

                        //Load all remain articles
                        val removeFirstItem = response.body()!!.articles
                        //Beause we get first item to hot news, so we need to remove it
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                        adapter.notifyDataSetChanged()
                        listNews.adapter = adapter
                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}