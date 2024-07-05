package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.bottom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMenuBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}