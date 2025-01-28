package com.geeksstudio_krmvd.bilimaskerkr.presentation.fragments.menu.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.geeksstudio_krmvd.bilimaskerkr.R
import com.geeksstudio_krmvd.bilimaskerkr.databinding.FragmentWebViewBinding
import com.geeksstudio_krmvd.bilimaskerkr.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val binding by viewBinding(FragmentWebViewBinding::bind)

    private val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()

        binding.webView.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                val webView = v as WebView

                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> {
                        if (webView.canGoBack()) {
                            webView.goBack()
                            return@setOnKeyListener true
                        }
                    }
                }
            }
            false
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(args.url)
        }
    }

}