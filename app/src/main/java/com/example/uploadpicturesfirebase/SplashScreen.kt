package com.example.uploadpicturesfirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    val durationSplashScreen : Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        changeActivity()
    }
    private fun changeActivity(){
        Handler().postDelayed(Runnable {
            val next = Intent(this, MainActivity::class.java)
            startActivity(next)
        }, durationSplashScreen)
    }
}