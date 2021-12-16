package com.minorProject.covidinsight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog


class NewsDetail : AppCompatActivity() {

    lateinit var alertDialog: android.app.AlertDialog
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        //WebView
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                alertDialog.dismiss()
            }
        }

        if (intent != null)
            if (!intent.getStringExtra("webURL")?.isEmpty()!!)
                webView.loadUrl(intent.getStringExtra("webURL")!!)
    }
}