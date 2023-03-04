package com.mutefrog.myfolio.view.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ActivityOnboardingBinding
import com.mutefrog.myfolio.utils.getDeviceId
import com.mutefrog.myfolio.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.janbarari.kevent.KEvent

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    private val stringObserverGUID = "SOGUID"

    val viewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
        actionBar.elevation = 0F;
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.view_action_bar, null)
        actionBar.customView = v
        actionBar.customView.findViewById<ImageView>(R.id.img_action_bar_back).setOnClickListener{
            onBackPressed()
        }

        KEvent.register<String>(stringObserverGUID) {
            Toast.makeText(this, "observerGUID: $stringObserverGUID\nvalue: $it", Toast.LENGTH_LONG).show()
            viewModel.regFCMToken("android", getDeviceId(this), it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}