package com.mutefrog.myfolio.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentSecurityBinding
import com.mutefrog.myfolio.utils.ModelPreferencesManager
import com.mutefrog.myfolio.utils.hideMainActivityBottomBar
import com.mutefrog.myfolio.utils.manageMainActivityActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecurityFragment : Fragment() {

    private var _binding: FragmentSecurityBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecurityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            getString(R.string.security)
        )
        hideMainActivityBottomBar(activity)

        binding.switchSecurity.setOnCheckedChangeListener { _, isChecked ->
            ModelPreferencesManager.put(isChecked, getString(R.string.pref_security_status))
            if (isChecked)
                binding.txtSecuritySwitch.text = getString(R.string.on)
            else
                binding.txtSecuritySwitch.text = getString(R.string.off)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}