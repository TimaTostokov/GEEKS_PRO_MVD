package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents.law.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDetailLawsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailLawsFragment : Fragment(R.layout.fragment_detail_laws) {
    private val binding by viewBinding(FragmentDetailLawsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
