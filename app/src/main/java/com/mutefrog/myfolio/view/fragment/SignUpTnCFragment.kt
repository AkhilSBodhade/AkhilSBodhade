package com.mutefrog.myfolio.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.mutefrog.myfolio.databinding.FragmentLoginOtpBinding
import com.mutefrog.myfolio.databinding.FragmentSignupTncBinding
import com.mutefrog.myfolio.utils.manageActionBar
import com.mutefrog.myfolio.utils.navigateToHomeScreen

class SignUpTnCFragment : Fragment() {

    private var _binding: FragmentSignupTncBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupTncBinding.inflate(inflater, container, false)
        val root: View = binding.root

        manageActionBar(activity, View.VISIBLE, "Terms & Conditions")

        binding.webviewSignupTnc.settings.javaScriptEnabled = true

        binding.webviewSignupTnc.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl("https://www.google.co.in/")
                return true
            }
        }
        binding.webviewSignupTnc.loadUrl("https://www.google.co.in/")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}