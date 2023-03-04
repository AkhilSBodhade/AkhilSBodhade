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
import com.mutefrog.myfolio.databinding.FragmentAddNomineeOtpBinding
import com.mutefrog.myfolio.databinding.FragmentDeleteAccountOtpBinding
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.SignupOTPViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class DeleteAccountOTPFragment : Fragment() {

    private var id: String? = null
    private var _binding: FragmentDeleteAccountOtpBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressAlert: AlertDialog

    val viewModel: SignupOTPViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountOtpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressAlert = SpotsDialog.Builder()
            .setContext(requireContext())
            .setMessage(getString(R.string.msg_loading))
            .build()

        viewModel.deleteAccountData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert.hide()
                    ModelPreferencesManager.put(null, getString(R.string.pref_logged_in_user_token))
                    ModelPreferencesManager.put(null, getString(R.string.pref_logged_in_user))
                    navigateToOnboardingScreen(activity)
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

//        viewModel.verifyNomineeOTPData.observe(viewLifecycleOwner) { dataHandler ->
//            when (dataHandler) {
//                is DataHandler.SUCCESS -> {
//                    progressAlert.hide()
//                    if (dataHandler.data?.success == true) {
//                        otpTimer.cancel()
//                        Navigation.findNavController(binding.btnDeleteAccountOtpAuthorise)
//                            .navigate(R.id.action_navigation_nominee_otp_to_navigation_nominee)
//                    } else {
//                        displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
//                    }
//                }
//                is DataHandler.ERROR -> {
//                    progressAlert.hide()
//                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
//                }
//                is DataHandler.LOADING -> {
//                    if (isAdded)
//                        progressAlert.show()
//                }
//            }
//        }

        binding.txtDeleteAccountResendOtp.visibility = View.GONE
        binding.txtDeleteAccountOtpTimerLl.visibility = View.VISIBLE
        binding.btnDeleteAccountOtpAuthorise.isEnabled = true

//        startOTPTimer()

        binding.btnDeleteAccountOtpAuthorise.isEnabled = false
        binding.otpView.requestFocusOTP()
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                binding.btnDeleteAccountOtpAuthorise.isEnabled = false
            }

            override fun onOTPComplete(otp: String) {
                binding.btnDeleteAccountOtpAuthorise.isEnabled = true
            }
        }

        binding.btnDeleteAccountOtpAuthorise.setOnClickListener {
            viewModel.deleteAccount(getAccessToken(requireActivity()), binding.otpView.otp)
        }
        return root
    }

//    private val otpTimer = object : CountDownTimer(1000 * 60 * 5, 1000) {
//        override fun onTick(millisUntilFinished: Long) {
//            val totalSecs = millisUntilFinished / 1000
//            val minutes = (totalSecs % 3600) / 60
//            val seconds = totalSecs % 60
//            binding.txtDeleteAccountOtpTimer.text = String.format(" %02d:%02d", minutes, seconds)
//        }
//
//        override fun onFinish() {
//            binding.txtDeleteAccountResendOtp.visibility = View.VISIBLE
//            binding.txtDeleteAccountOtpTimerLl.visibility = View.GONE
//            binding.btnDeleteAccountOtpAuthorise.isEnabled = false
//            binding.txtDeleteAccountResendOtp.setOnClickListener {
//                viewModel.resendDeleteAccountOTP(getAccessToken(requireActivity()), id.toString())
//            }
//        }
//    }

    override fun onResume() {
        super.onResume()
        hideMainActivityActionBar(activity)
    }

//    private fun startOTPTimer() {
//        otpTimer.start()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}