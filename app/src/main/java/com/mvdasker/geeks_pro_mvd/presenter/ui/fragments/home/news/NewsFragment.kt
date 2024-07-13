package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.home.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNewsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding

class NewsFragment : Fragment(R.layout.fragment_news) {
    private val binding by viewBinding(FragmentNewsBinding::bind)
    private val args by navArgs<NewsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news()
        toBack()
    }

    private fun news() {
        val navArgs = args
        val urgentNews = navArgs.model.urgentNews
        val data = navArgs.model.data
        val tvData = navArgs.model.description
        val image = navArgs.model.image
        binding.tvNewsTitle.text = urgentNews
        binding.ivItem.setImageResource(image[1])
        binding.tvData.text = tvData
        binding.data.text = data
    }

    private fun toBack() {
        binding.toBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}