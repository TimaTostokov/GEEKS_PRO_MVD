package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMenuBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.data.remote.model.parent.ParentModel
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.aboutus.content.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()

    private var isRecyclerViewVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setNavController(findNavController())

        binding.recyclerView.isVisible = false
        binding.line.isVisible = isRecyclerViewVisible

        initListeners()
        setupClickListeners()
        setupRecyclerView()
        openBrowser()

        lifecycleScope.launch {
            viewModel.selectedButtonId.collect { selectedId ->
                selectedId?.let { updateButtonState(it) }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val historyAdapter = HistoryAdapter(getSampleData(), this::onClicker)
        binding.recyclerView.adapter = historyAdapter
    }

    private fun getSampleData(): List<ParentModel> {
        return listOf(
            ParentModel("История Кыргызстана"),
            ParentModel("История ВВ МВД КР"),
            ParentModel("История МВД КР")
        )
    }

    private fun onClicker(position: Int){
        when (position) {
            0 -> {findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToHistoryOfKyrgyzstanFragment())
                isRecyclerViewVisible = false}
            1 -> {findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToHistoryVVMVDKRFragment())
                isRecyclerViewVisible = false}
            2 -> {findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToHistoryMVDKRFragment())
                isRecyclerViewVisible = false}
        }
    }

    private fun updateButtonState(checkedId: Int) {
        setButtonState(binding.kgBtn, checkedId == R.id.kg_btn)
        setButtonState(binding.ruBtn, checkedId == R.id.ru_btn)
    }

    private fun setButtonState(button: MaterialButton, isSelected: Boolean) {
        if (isSelected) {
            button.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.dark_blue)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            button.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.white)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        }
    }

    private fun openBrowser() {
        binding.openDictionaryBtn.setOnClickListener {
            viewModel.onOpenDictionaryClick()
        }
        binding.mapBtn.setOnClickListener {
            viewModel.onMapClick()
        }
        binding.trafficRulesButton.setOnClickListener {
            viewModel.onTrafficRulesClick()
        }
    }

    private fun setupClickListeners() {
        binding.buttonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            viewModel.onButtonToggleGroupCheckedChange(checkedId, isChecked)
        }
    }

    private fun initListeners() {
        binding.apply {
            spinner.setOnClickListener {
                isRecyclerViewVisible = !isRecyclerViewVisible
                binding.recyclerView.isVisible = isRecyclerViewVisible
                binding.line.isVisible = isRecyclerViewVisible
                if (isRecyclerViewVisible) {
                    binding.spinner.setImageResource(R.drawable.spinner_icon)
                }else{
                    binding.spinner.setImageResource(R.drawable.spinner_icon2)
                }
            }

            controlKRButton.setOnClickListener {
                viewModel.onClickControlKRButton()
            }
            controlMIAKRButton.setOnClickListener {
                viewModel.onClickControlMIAKRButton()
            }
            controlITMIAKRButton.setOnClickListener {
                viewModel.onClickControlITMIAKRButton()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}