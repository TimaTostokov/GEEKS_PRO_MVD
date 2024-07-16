package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.documents.law

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentLawBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.disableScreenShot
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding

class LawFragment : Fragment(R.layout.fragment_law) {

    private val binding by viewBinding(FragmentLawBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        disableScreenShot(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDetach() {
        super.onDetach()
        disableScreenShot(false)
    }

}