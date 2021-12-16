package com.minorProject.covidinsight.Common

import com.minorProject.covidinsight.Interface.NewsService
import com.minorProject.covidinsight.RetrofitAPI

object Common{
//    "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=ea27af924ef44aab8149230bad278f1b"
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "ea27af924ef44aab8149230bad278f1b"

    val newsService: NewsService
        get() = RetrofitAPI.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsApi(source:String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }

}