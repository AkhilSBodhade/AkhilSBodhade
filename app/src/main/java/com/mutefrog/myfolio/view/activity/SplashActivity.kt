package com.mutefrog.myfolio.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ActivitySplashBinding
import com.mutefrog.myfolio.model.User
import com.mutefrog.myfolio.utils.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backgroundImage: ImageView = binding.imgSplash
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        backgroundImage.startAnimation(slideAnimation)

        Handler().postDelayed({
            handleNavigation()
        }, 3000)
    }

    private fun handleNavigation() {
        val loggedInUser = ModelPreferencesManager.get<User>(getString(R.string.pref_logged_in_user))
        val intent: Intent = if (loggedInUser != null) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, TutorialActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

}