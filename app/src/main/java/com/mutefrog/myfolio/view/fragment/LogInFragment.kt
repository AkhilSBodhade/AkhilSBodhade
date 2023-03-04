package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentLoginBinding
import com.mutefrog.myfolio.utils.DataHandler
import com.mutefrog.myfolio.utils.displaySnackbarAlert
import com.mutefrog.myfolio.utils.isValidEmail
import com.mutefrog.myfolio.utils.manageActionBar
import com.mutefrog.myfolio.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class LogInFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    val viewModel: LoginViewModel by viewModels()

    private lateinit var progressAlert: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        manageActionBar(activity, View.VISIBLE, "")

        progressAlert = SpotsDialog.Builder()
            .setContext(requireContext())
            .setMessage(getString(R.string.msg_loading))
            .build()

        viewModel.loginData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert.hide()
                    if (dataHandler.data?.success == true) {
                        val bundle = Bundle()
                        bundle.putString(
                            "email",
                            binding.editTextLogInEmail.editableText.toString()
                        )
                        Navigation.findNavController(binding.btnLoginNext)
                            .navigate(
                                R.id.action_navigation_login_to_navigation_login_otp,
                                bundle
                            )
                        Toast.makeText(requireActivity(), "OTP is " + dataHandler.data?.data.otp, Toast.LENGTH_LONG).show()
                    } else {
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

        binding.btnLoginNext.isEnabled = false

        binding.editTextLogInEmail.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Does nothing intentionally
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValidEmail(s.toString())) {
                    binding.btnLoginNext.isEnabled = true
                    binding.editTextLogInEmail.error = null
                } else {
                    binding.btnLoginNext.isEnabled = false
                    binding.editTextLogInEmail.error = getString(R.string.alert_msg_invalid_email)
                }
            }
        })

        binding.loginCtaSignup.setOnClickListener {
            Navigation.findNavController(binding.loginCtaSignup)
                .navigate(R.id.action_navigation_login_to_navigation_signup)
        }

        binding.btnLoginNext.setOnClickListener {
            viewModel.login(binding.editTextLogInEmail.editableText.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}