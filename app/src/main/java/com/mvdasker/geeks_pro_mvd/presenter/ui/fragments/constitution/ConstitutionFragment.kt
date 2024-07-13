package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.constitution

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentConstitutionBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.disableScreenShot
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding

class ConstitutionFragment : Fragment(R.layout.fragment_constitution) {

    private val binding by viewBinding(FragmentConstitutionBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        disableScreenShot(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fconstBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDetach() {
        super.onDetach()
        disableScreenShot(false)
    }

}