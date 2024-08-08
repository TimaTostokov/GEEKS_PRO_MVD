package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.trafficrules

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentTrafficRulesBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding

class TrafficRulesFragment : Fragment(R.layout.fragment_traffic_rules) {

    private val binding by viewBinding(FragmentTrafficRulesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}