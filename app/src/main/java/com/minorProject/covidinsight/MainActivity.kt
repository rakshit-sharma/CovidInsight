package com.minorProject.covidinsight

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //for the navigation of the fragments
        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.topNavigationView)
        bottomNavigationView.setupWithNavController(navController)

//        val intent = Intent(this, CovidData::class.java) // make button; then write this inside that button's onClickListener.
//        startActivity(intent)
//        finish()
    }
}