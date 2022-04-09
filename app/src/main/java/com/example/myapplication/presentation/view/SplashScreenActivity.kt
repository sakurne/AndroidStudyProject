package com.example.myapplication.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        routeToPage()
    }

    private fun routeToPage() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }
}
