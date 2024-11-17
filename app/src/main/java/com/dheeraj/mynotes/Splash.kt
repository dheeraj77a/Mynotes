package com.dheeraj.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dheeraj.mynotes.ui.MainActivity
import android.os.Handler

class Splash : AppCompatActivity() {
    private val splashTimeOut: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            // Start your app's main activity after the splash screen timeout
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTimeOut)
    }

}