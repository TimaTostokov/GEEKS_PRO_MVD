package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMenuBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.adapter.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MenuViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
        setupListeners()

        viewModel.setNavController(findNavController())

        viewModel.isRecyclerViewVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.recyclerView.isVisible = isVisible
            binding.line.isVisible = isVisible
            updateSpinnerIcon(isVisible)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.selectedButtonId.collect { selectedId ->
                selectedId?.let { updateButtonState(it) }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val historyAdapter = HistoryAdapter(viewModel.getSampleData(), this::onClicker)
        binding.recyclerView.adapter = historyAdapter
    }

    private fun onClicker(position: Int) {
        viewModel.onItemClick(position)
    }

    private fun updateButtonState(checkedId: Int) {
        setButtonState(binding.kgBtn, checkedId == R.id.kg_btn)
        setButtonState(binding.ruBtn, checkedId == R.id.ru_btn)
    }

    private fun setButtonState(button: MaterialButton, isSelected: Boolean) {
        button.apply {
            backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                if (isSelected) R.color.dark_blue else R.color.white
            )
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (isSelected) R.color.white else R.color.dark_blue
                )
            )
        }
    }

    private fun updateSpinnerIcon(isVisible: Boolean) {
        binding.spinner.setImageResource(
            if (isVisible) R.drawable.spinner_icon else R.drawable.spinner_icon_two
        )
    }

    private fun setupListeners() = with(binding) {
        spinner.setOnClickListener { viewModel.toggleRecyclerViewVisibility() }
        aboutUsButton.setOnClickListener { viewModel.toggleRecyclerViewVisibility() }

        val hideRecyclerViewActions = listOf(
            controlKRButton to { viewModel.onClickControlKRButton() },
            controlMIAKRButton to { viewModel.onClickControlMIAKRButton() },
            controlITMIAKRButton to { viewModel.onClickControlITMIAKRButton() },
            openDictionaryBtn to { viewModel.onOpenDictionaryClick() },
            mapBtn to { viewModel.onMapClick() },
            tvLogoGeeks to { viewModel.openInstagram() },
            imgLogoGeeks to { viewModel.openInstagram() },
            trafficRulesButton to { viewModel.onTrafficRulesClick() }
        )

        hideRecyclerViewActions.forEach { (button, action) ->
            button.setOnClickListener {
                action()
                viewModel.hideRecyclerView()
            }
        }

        buttonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            viewModel.onButtonToggleGroupCheckedChange(checkedId, isChecked)
            viewModel.hideRecyclerView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}