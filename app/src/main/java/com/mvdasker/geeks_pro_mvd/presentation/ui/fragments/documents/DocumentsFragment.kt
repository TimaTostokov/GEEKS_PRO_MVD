package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.documents

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Screen
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDocumentsBinding
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentsFragment : Fragment(R.layout.fragment_documents) {

    private val binding by viewBinding(FragmentDocumentsBinding::bind)

    private val viewModel by viewModels<DocumentsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutConstitution.setOnClickListener { navigateToScreen(Screen.Constitution) }
        binding.layoutLaw.setOnClickListener { navigateToScreen(Screen.Law) }
        binding.layoutStatutes.setOnClickListener { navigateToScreen(Screen.Statutes) }
        binding.fDocNotification.setOnClickListener { navigateToScreen(Screen.Notifications) }

        binding.fDocUpBtn.setOnClickListener {
            binding.nestedSvDocument.smoothScrollTo(0, 0)
        }
    }

    private fun navigateToScreen(screen: Screen) {
        when (screen) {
            Screen.Constitution -> findNavController().navigate(R.id.action_documentsFragment_to_constitutionFragment)
            Screen.Law -> findNavController().navigate(R.id.action_documentsFragment_to_lawFragment)
            Screen.Statutes -> findNavController().navigate(R.id.action_documentsFragment_to_statutesFragment)
            Screen.Notifications -> findNavController().navigate(R.id.action_documentsFragment_to_notificationsFragment)
        }
    }


    /**
     * не удалять
     */
    private fun notificationsAvailability() {
        observeData(viewModel.notReadNotifCount) {
            if (it != 0) {
                binding.fDocNotification.setImageResource(R.drawable.bell_not_empty)
            } else {
                binding.fDocNotification.setImageResource(R.drawable.bell)
            }
        }
    }

}