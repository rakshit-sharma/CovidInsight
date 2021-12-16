package com.minorProject.covidinsight.Interface

import com.minorProject.covidinsight.Model.News
import com.minorProject.covidinsight.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    // We can delete https://newsapi.org/ because this is BASE_URL we have declared on Common
    @get:GET("v2/top-headlines/sources?apiKey=ea27af924ef44aab8149230bad278f1b")

    // WebSite is our Model class
    val sources:Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url: String):Call<News>
}