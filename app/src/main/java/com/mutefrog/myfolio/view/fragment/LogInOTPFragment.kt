package com.mutefrog.myfolio.view.fragment

import `in`.aabhasjindal.otptextview.OTPListener
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentLoginOtpBinding
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.LoginOTPViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class LogInOTPFragment : Fragment() {

    private var email: String? = null
    private var _binding: FragmentLoginOtpBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressAlert: AlertDialog

    val viewModel: LoginOTPViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginOtpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        manageActionBar(activity, View.VISIBLE, "")

        progressAlert = SpotsDialog.Builder()
            .setContext(requireContext())
            .setMessage(getString(R.string.msg_loading))
            .build()

        viewModel.resendOTPData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert.hide()
                    if (dataHandler.data?.success == true) {
                        binding.txtLoginResendOtp.visibility = View.GONE
                        binding.txtLoginOtpTimerLl.visibility = View.VISIBLE
                        startOTPTimer()
                        binding.btnLoginOtpAuthorise.isEnabled = true
                    }
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.ERROR -> {
                    progressAlert.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
                    progressAlert.show()
                }
            }
        }

        viewModel.verifyOTPData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert.hide()
                    if (dataHandler.data?.success == true) {
                        otpTimer.cancel()
                        ModelPreferencesManager.put(dataHandler.data?.data?.token, getString(R.string.pref_logged_in_user_token))
                        ModelPreferencesManager.put(dataHandler.data?.data?.user, getString(R.string.pref_logged_in_user))
                        navigateToHomeScreen(activity)
                    }else{
                        displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                    }
                }
                is DataHandler.ERROR -> {
                    progressAlert.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
                    if(isAdded)
                    progressAlert.show()
                }
            }
        }

        binding.txtLoginResendOtp.visibility = View.GONE
        binding.txtLoginOtpTimerLl.visibility = View.VISIBLE
        binding.btnLoginOtpAuthorise.isEnabled = true

        if (arguments != null) {
            email = SignUpOTPFragmentArgs.fromBundle(requireArguments()).email
        }

        startOTPTimer()

        binding.btnLoginOtpAuthorise.isEnabled = false
        binding.otpView.requestFocusOTP()
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                binding.btnLoginOtpAuthorise.isEnabled = false
            }

            override fun onOTPComplete(otp: String) {
                binding.btnLoginOtpAuthorise.isEnabled = true
            }
        }

        binding.btnLoginOtpAuthorise.setOnClickListener {
            viewModel.verifyLoginOTP(email, binding.otpView.otp)
        }
        return root
    }

    private val otpTimer = object : CountDownTimer(1000*60*5, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val totalSecs = millisUntilFinished / 1000
            val minutes = (totalSecs % 3600) / 60
            val seconds = totalSecs % 60
            binding.txtLoginOtpTimer.text = String.format(" %02d:%02d", minutes, seconds)
        }

        override fun onFinish() {
            binding.txtLoginResendOtp.visibility = View.VISIBLE
            binding.txtLoginOtpTimerLl.visibility = View.GONE
            binding.btnLoginOtpAuthorise.isEnabled = false
            binding.txtLoginResendOtp.setOnClickListener {
                viewModel.resendOTP(email.toString())
            }
        }
    }

    private fun startOTPTimer() {
        otpTimer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}