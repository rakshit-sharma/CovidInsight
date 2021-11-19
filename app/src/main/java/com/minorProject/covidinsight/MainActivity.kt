package com.minorProject.covidinsight

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView : TextView = findViewById(R.id.api_name)
        textView.text = "Hello"

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://free-nba.p.rapidapi.com/players?page=0&per_page=25")
            .get()
            .addHeader("x-rapidapi-host", "free-nba.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "c0d0ac06d1msh357a33595b3e794p1ecb7cjsn84d779d2e89d")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call, response: Response) {
                Log.d("MyTAG", "onCreate: "+response.body?.string())
                this@MainActivity.runOnUiThread(Runnable {
                    textView.text = response.body?.string()
                }
                )
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MyTAG", "onCreate: "+e)
            }
        })
    }
}