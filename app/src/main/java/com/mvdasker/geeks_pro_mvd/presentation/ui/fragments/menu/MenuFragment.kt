package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.databinding.FragmentMenuBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.history.adapter.HistoryAdapter
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.gone
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.loadImage
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.noInternetSnackbar
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.observeData
import com.mvdasker.geeks_pro_mvd.utils.ext.Extensions.visible
import com.mvdasker.geeks_pro_mvd.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::bind)

    private val viewModel by viewModels<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setNavController(findNavController())

        setupRecyclerView()
        setupListeners()
        observeViewModel()
        snackBar()

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

        lifecycleScope.launch {
            viewModel.isSpinnerIconRotated.collectLatest { isRotated ->
                updateSpinnerIcon(isRotated)
            }
        }

        lifecycleScope.launch {
            viewModel.getUserId.collectLatest { result ->
                when (result) {
                    is UiState.Loading -> {
                        binding.fMenuProgressBar.visible()
                    }

                    is UiState.Success -> {
                        binding.userName.text = result.data?.username
                        binding.avatarImageView.loadImage(result.data?.img.toString())
                        binding.fMenuProgressBar.gone()
                    }

                    is UiState.Error -> {
                        binding.fMenuProgressBar.gone()
                    }
                }
            }
        }
    }

    private fun snackBar() {
        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.NetworkIsDisconnected -> {
                    noInternetSnackbar()
                }

                else -> {
                    Extensions.showToast(requireContext(), "Network is disconnected")
                }
            }
            viewModel.clearMessage()
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

    private fun updateSpinnerIcon(isRotated: Boolean) {
        val targetRotation = if (isRotated) 180f else 0f
        val currentRotation = binding.spinner.rotation

        if (currentRotation != targetRotation) {
            ObjectAnimator.ofFloat(binding.spinner, "rotation", currentRotation, targetRotation)
                .apply {
                    duration = 300L
                    start()
                }
        }
    }

    private fun setupListeners() = with(binding) {
        spinner.setOnClickListener {
            viewModel.toggleRecyclerViewVisibility()
            expandRecyclerView(viewModel.isRecyclerViewVisible.value)
        }

        aboutUsButton.setOnClickListener {
            viewModel.toggleRecyclerViewVisibility()
            expandRecyclerView(viewModel.isRecyclerViewVisible.value)
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
            button.setOnClickListener { action() }
        }

        buttonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                viewModel.onButtonToggleGroupCheckedChange(checkedId, isChecked)
            }
        }
    }

    private fun expandRecyclerView(isVisible: Boolean) {
        ObjectAnimator.ofFloat(
            binding.recyclerView, "alpha",
            if (isVisible) 0f else 1f, if (isVisible) 1f else 0f
        ).apply {
            duration = 300L
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.recyclerView.visibility = if (isVisible) View.VISIBLE else View.GONE
                }
            })
        }.start()
    }

}