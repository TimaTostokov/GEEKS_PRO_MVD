package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.home.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<NewsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news()
        toBack()
        toNotification()
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

    private fun toNotification() {
        binding.ivIcon.setOnClickListener {
            findNavController().navigate(R.id.action_newsFragment_to_notificationsFragment)
        }
    }
}