package com.minorProject.covidinsight

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, CovidData::class.java) // make button; then write this inside that button's onClickListener.
        startActivity(intent)
        finish()
    }
}