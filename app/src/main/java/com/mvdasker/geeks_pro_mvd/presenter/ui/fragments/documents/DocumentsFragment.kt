package com.mvdasker.geeks_pro_mvd.presenter.ui.fragments.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentDocumentsBinding

class DocumentsFragment : Fragment() {

    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutConstitution.setOnClickListener { navigateToScreen(Screen.Constitution) }
        binding.layoutLaw.setOnClickListener { navigateToScreen(Screen.Law) }
        binding.layoutStatutes.setOnClickListener { navigateToScreen(Screen.Statutes) }
    }

    private fun navigateToScreen(screen: Screen) {
        when (screen) {
            Screen.Constitution -> findNavController().navigate(R.id.action_documentsFragment_to_constitutionFragment)
            Screen.Law -> findNavController().navigate(R.id.action_documentsFragment_to_lawFragment)
            Screen.Statutes -> findNavController().navigate(R.id.action_documentsFragment_to_statutesFragment)
        }
    }

    sealed class Screen {
        data object Constitution : Screen()
        data object Law : Screen()
        data object Statutes : Screen()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}