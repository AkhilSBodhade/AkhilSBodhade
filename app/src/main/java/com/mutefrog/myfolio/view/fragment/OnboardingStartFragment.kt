package com.mutefrog.myfolio.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentOnboardigStartBinding
import com.mutefrog.myfolio.utils.manageActionBar

class OnboardingStartFragment : Fragment() {

    private var _binding: FragmentOnboardigStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardigStartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnSignupIntroLogin.setOnClickListener {
            Navigation.findNavController(binding.btnSignupIntroReg).navigate(R.id.action_navigation_onboarding_start_to_navigation_login);
        }

        binding.btnSignupIntroReg.setOnClickListener {
            Navigation.findNavController(binding.btnSignupIntroReg).navigate(R.id.action_navigation_onboarding_start_to_navigation_signup);
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        manageActionBar(activity, View.GONE, "")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}