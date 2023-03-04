package com.mutefrog.myfolio.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.mutefrog.myfolio.databinding.FragmentInappBrowserBinding
import com.mutefrog.myfolio.databinding.FragmentLoginOtpBinding
import com.mutefrog.myfolio.databinding.FragmentSignupTncBinding
import com.mutefrog.myfolio.utils.manageActionBar
import com.mutefrog.myfolio.utils.navigateToHomeScreen

class InAppBrowserFragment : Fragment() {

    private var _binding: FragmentInappBrowserBinding? = null
    private val binding get() = _binding!!
    private var pageUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInappBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        manageActionBar(activity, View.VISIBLE, "")

        if (arguments != null) {
            pageUrl = InAppBrowserFragmentArgs.fromBundle(requireArguments()).url
        }

        binding.webviewInappBrowser.settings.javaScriptEnabled = true
        binding.webviewInappBrowser.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        binding.webviewInappBrowser.loadUrl(pageUrl)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}