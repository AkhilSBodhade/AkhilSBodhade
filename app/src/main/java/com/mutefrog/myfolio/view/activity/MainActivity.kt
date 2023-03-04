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
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ActivityMainBinding
import com.mutefrog.myfolio.model.User
import com.mutefrog.myfolio.utils.DataHandler
import com.mutefrog.myfolio.utils.ModelPreferencesManager
import com.mutefrog.myfolio.utils.getDeviceId
import com.mutefrog.myfolio.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.janbarari.kevent.KEvent
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val stringObserverGUID = "SOGUID"

    val viewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KEvent.register<String>(stringObserverGUID) {
            Toast.makeText(this, "observerGUID: $stringObserverGUID\nvalue: $it", Toast.LENGTH_LONG).show()
            viewModel.regFCMToken("android", getDeviceId(this), it)
        }

        viewModel.tokenRegiData.observe(this) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
//                    progressAlert?.hide()
                }
                is DataHandler.ERROR -> {
//                    progressAlert?.hide()
//                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
//                        progressAlert?.show()
                }
            }
        }


        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.view_main_activity_action_bar, null)
        actionBar.customView = v
        actionBar.customView.findViewById<ImageView>(R.id.img_main_act_action_bar_back)
            .setOnClickListener {
                onBackPressed()
            }
        actionBar.hide()

        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null;
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        val loggedInUser =
            ModelPreferencesManager.get<User>(getString(R.string.pref_logged_in_user))

        if (ModelPreferencesManager.get<Boolean>(getString(R.string.pref_security_status)) == true) {
            val executor = Executors.newSingleThreadExecutor()
            val activity: FragmentActivity = this
            val biometricPrompt =
                BiometricPrompt(
                    activity,
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                                finish()
                            }
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            finish()
                        }
                    })

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Welcome, " + loggedInUser?.name)
                .setSubtitle("Use fingerprint to unlock")
                .setNegativeButtonText("Cancel")
                .build()

            biometricPrompt.authenticate(promptInfo)
        }

    }

}