package com.mutefrog.myfolio.view.fragment

import `in`.aabhasjindal.otptextview.OTPListener
import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentSingupOtpBinding
import com.mutefrog.myfolio.utils.DataHandler
import com.mutefrog.myfolio.utils.ModelPreferencesManager
import com.mutefrog.myfolio.utils.displaySnackbarAlert
import com.mutefrog.myfolio.utils.manageActionBar
import com.mutefrog.myfolio.viewmodel.SignupOTPViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class SignUpOTPFragment : Fragment() {

    private var email: String? = null
    private var _binding: FragmentSingupOtpBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressAlert: AlertDialog

    val viewModel: SignupOTPViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingupOtpBinding.inflate(inflater, container, false)
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
                        binding.txtSignupResendOtp.visibility = View.GONE
                        binding.txtSignupOtpTimerLl.visibility = View.VISIBLE
                        startOTPTimer()
                        binding.btnSignupOtpAuthorise.isEnabled = true
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
                        Navigation.findNavController(binding.btnSignupOtpAuthorise)
                            .navigate(R.id.action_navigation_signup_otp_to_navigation_complete_navigation)
                    }else{
                        displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                    }
                }
                is DataHandler.ERROR -> {
                    progressAlert.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
                    if (isAdded)
                    progressAlert.show()
                }
            }
        }

        binding.txtSignupResendOtp.visibility = View.GONE
        binding.txtSignupOtpTimerLl.visibility = View.VISIBLE
        binding.btnSignupOtpAuthorise.isEnabled = true

        if (arguments != null) {
            email = SignUpOTPFragmentArgs.fromBundle(requireArguments()).email
        }

        startOTPTimer()

        binding.btnSignupOtpAuthorise.isEnabled = false
        binding.otpView.requestFocusOTP()
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                binding.btnSignupOtpAuthorise.isEnabled = false
            }

            override fun onOTPComplete(otp: String) {
                binding.btnSignupOtpAuthorise.isEnabled = true
            }
        }

        binding.btnSignupOtpAuthorise.setOnClickListener {
            viewModel.verifySignupOTP(email, binding.otpView.otp)
        }
        return root
    }

    private val otpTimer = object : CountDownTimer(1000*60*5, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val totalSecs = millisUntilFinished / 1000
            val minutes = (totalSecs % 3600) / 60
            val seconds = totalSecs % 60
            binding.txtSignupOtpTimer.text = String.format(" %02d:%02d", minutes, seconds)
        }

        override fun onFinish() {
            binding.txtSignupResendOtp.visibility = View.VISIBLE
            binding.txtSignupOtpTimerLl.visibility = View.GONE
            binding.btnSignupOtpAuthorise.isEnabled = false
            binding.txtSignupResendOtp.setOnClickListener {
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