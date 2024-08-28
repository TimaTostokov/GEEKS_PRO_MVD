package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.constitution

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConstitutionFragment : Fragment(R.layout.fragment_constitution) {

    private val binding by viewBinding(FragmentConstitutionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fconstBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}