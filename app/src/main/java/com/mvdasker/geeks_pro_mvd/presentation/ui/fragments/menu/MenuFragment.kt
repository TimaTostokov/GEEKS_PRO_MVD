package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
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
import com.mvdasker.geeks_pro_mvd.App
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMenuBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.adapter.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MenuViewModel>()

    private var spinnerIconUpdated = false

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
        val id = (requireContext().applicationContext as App).userProvider?.getUserId().toString()
        Log.d("ololo", "Данные не пришли: ${id}")
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.isRecyclerViewVisible.collectLatest { isVisible ->
                binding.recyclerView.isVisible = isVisible
                binding.line.isVisible = isVisible
            }
        }

        lifecycleScope.launch {
            viewModel.selectedButtonId.collect { selectedId ->
                selectedId?.let { updateButtonState(it) }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = HistoryAdapter(viewModel.getSampleData(), this@MenuFragment::onClicker)
        }
    }

    private fun onClicker(position: Int) {
        viewModel.onItemClick(position)
    }

    private fun updateButtonState(checkedId: Int) {
        binding.kgBtn.updateState(checkedId == R.id.kg_btn)
        binding.ruBtn.updateState(checkedId == R.id.ru_btn)
    }

    private fun MaterialButton.updateState(isSelected: Boolean) {
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

    private fun updateSpinnerIcon(isVisible: Boolean) {
        ObjectAnimator.ofFloat(binding.spinner, "rotation",
            if (isVisible) 0f else 180f, if (isVisible) 180f else 0f).apply {
            duration = 300L
        }.start()
    }

    private fun setupListeners() = with(binding) {
        spinner.setOnClickListener {
            viewModel.toggleRecyclerViewVisibility()
            spinnerIconUpdated = false
            expandRecyclerView(viewModel.isRecyclerViewVisible.value ?: false)
            updateSpinnerIcon(viewModel.isRecyclerViewVisible.value ?: false)
        }
        aboutUsButton.setOnClickListener {
            viewModel.toggleRecyclerViewVisibility()
            spinnerIconUpdated = false
            expandRecyclerView(viewModel.isRecyclerViewVisible.value ?: false)
            updateSpinnerIcon(viewModel.isRecyclerViewVisible.value ?: false)
        }

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
            }
        }

        buttonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                viewModel.onButtonToggleGroupCheckedChange(checkedId, isChecked)
            }
        }
    }

    private fun expandRecyclerView(isVisible: Boolean) {
        ObjectAnimator.ofFloat(binding.recyclerView, "alpha",
            if (isVisible) 0f else 1f, if (isVisible) 1f else 0f).apply {
            duration = 300L
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.recyclerView.visibility = if (isVisible) View.VISIBLE else View.GONE
                }
            })
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}