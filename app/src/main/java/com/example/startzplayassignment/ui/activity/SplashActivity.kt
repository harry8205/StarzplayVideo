package com.example.startzplayassignment.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.startzplayassignment.R
import com.example.startzplayassignment.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    private val SPLASH_DELAY: Long = 3000 // 3 seconds in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Handler().postDelayed({
            if (isNetworkAvailable(this)){
                redirectToNextActivity(MainActivity::class.java)
            }else{
                internetConnectionDialog()
            }

        }, SPLASH_DELAY)

    }
}