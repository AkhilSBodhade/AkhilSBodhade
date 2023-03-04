package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentProfileBinding
import com.mutefrog.myfolio.model.User
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.SignupOTPViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    val viewModel: SignupOTPViewModel by viewModels()

    private var progressAlert: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressAlert = getProgressDialog(requireActivity())

        binding.txtProfilePersonalInfo.setOnClickListener {
            Navigation.findNavController(binding.txtProfilePersonalInfo)
                .navigate(R.id.action_navigation_profile_to_navigation_personal_information)
        }

        binding.txtProfileSecurity.setOnClickListener {
            Navigation.findNavController(binding.txtProfileSecurity)
                .navigate(R.id.action_navigation_profile_to_navigation_security)
        }

        binding.txtProfileNominee.setOnClickListener {
            Navigation.findNavController(binding.txtProfileSecurity)
                .navigate(R.id.action_navigation_profile_to_navigation_nominee)
        }

        binding.txtProfileRefer.setOnClickListener {
            ShareCompat.IntentBuilder(requireContext())
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .setText("http://www.url.com")
                .startChooser();
        }

        binding.txtProfileAboutMyfolio.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                "url",
                "http://www.mutefrog.com"
            )
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_navigation_profile_to_navigation_inapp_browser, bundle)
        }

        binding.txtProfileTnc.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                "url",
                "http://www.mutefrog.com"
            )
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_navigation_profile_to_navigation_inapp_browser, bundle)
        }

        viewModel.deleteAccountReqData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    if (dataHandler.data?.success == true) {
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_navigation_profile_to_navigation_delete_account_otp)
                    }else {
                        displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                    }
                }
                is DataHandler.ERROR -> {
                    progressAlert?.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
                    progressAlert?.show()
                }
            }
        }

        binding.txtProfileDeleteAcc.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.view_custom_dialog)
            val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
            body.text = getString(R.string.delete_account_confirmation)
            val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
            yesBtn.text = getString(R.string.delete_account)
            val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
            yesBtn.setOnClickListener {
                dialog.dismiss()
                viewModel.deleteAccountRequest(getAccessToken(requireActivity()))
            }
            noBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        binding.txtProfileLogout.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.view_custom_dialog)
            val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
            body.text = getString(R.string.logout_confirmation)
            val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
            yesBtn.text = getString(R.string.logout)
            val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
            yesBtn.setOnClickListener {
                dialog.dismiss()
                ModelPreferencesManager.put(null, getString(R.string.pref_logged_in_user_token))
                ModelPreferencesManager.put(null, getString(R.string.pref_logged_in_user))
                navigateToOnboardingScreen(activity)
            }
            noBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        hideMainActivityActionBar(activity)
        showMainActivityBottomBar(activity)
        val loggedInUser =
            ModelPreferencesManager.get<User>(getString(R.string.pref_logged_in_user))
        binding.txtProfileUsername.text = loggedInUser?.name
        binding.txtProfileEmail.text = loggedInUser?.email

        val initials =
            loggedInUser?.name?.split(' ')
            ?.mapNotNull { it.firstOrNull()?.toString() }
            ?.reduce { acc, s -> acc + s }
        binding.txtProfileUserIcon.text = initials
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}