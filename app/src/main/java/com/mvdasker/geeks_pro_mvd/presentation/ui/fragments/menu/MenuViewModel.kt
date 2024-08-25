package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.mvdasker.geeks_pro_mvd.data.remote.model.parent.ParentModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel : ViewModel() {

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private val _selectedButtonId = MutableStateFlow<Int?>(null)
    val selectedButtonId: StateFlow<Int?> get() = _selectedButtonId.asStateFlow()

    private val _selectedFragment = MutableStateFlow<Int?>(null)
    val selectedFragment: StateFlow<Int?> get() = _selectedFragment

    private val _isRecyclerViewVisible = MutableStateFlow<Boolean>(false)
    val isRecyclerViewVisible: StateFlow<Boolean> get() = _isRecyclerViewVisible

    fun setSelectedFragment(fragmentId: Int) {
        _selectedFragment.value = fragmentId
    }

    fun setNavController(navController: NavController) {
        _navController = navController
    }

    fun getSampleData(): List<ParentModel> = listOf(
        ParentModel("История Кыргызстана"),
        ParentModel("История ВВ МВД КР"),
        ParentModel("История МВД КР")
    )

    fun onItemClick(position: Int) {
        val direction = when (position) {
            0 -> MenuFragmentDirections.actionMenuFragmentToHistoryOfKyrgyzstanFragment()
            1 -> MenuFragmentDirections.actionMenuFragmentToHistoryVVMVDKRFragment()
            2 -> MenuFragmentDirections.actionMenuFragmentToHistoryMVDKRFragment()
            else -> return
        }
        _isRecyclerViewVisible.value = true
        Log.d("isVisible", "Видно: ${_isRecyclerViewVisible.value}")
        navController.navigate(direction)
    }

    fun toggleRecyclerViewVisibility() {
        _isRecyclerViewVisible.value = !_isRecyclerViewVisible.value
        Log.d("isVisible", "хз: ${_isRecyclerViewVisible.value}")
    }

    fun hideRecyclerView() {
        _isRecyclerViewVisible.value = false
        Log.d("isVisible", "Не видно: ${_isRecyclerViewVisible.value}")
    }

    fun onOpenDictionaryClick() {
        val url = "https://el-sozduk.kg/"
        openWebView(url)
    }

    fun onMapClick() {
        val url = "https://www.google.com/maps"
        openWebView(url)
    }

    fun onTrafficRulesClick() {
        val url = "https://joldo.kg/ru"
        openWebView(url)
    }

    fun openInstagram() {
        val url = "https://www.instagram.com/geeks_pro/"
        openWebView(url)
    }

    private fun openWebView(url: String) {
        val action = MenuFragmentDirections.actionMenuFragmentToWebViewFragment(url)
        navController.navigate(action)
    }

    private fun setSelectedButtonId(checkedId: Int) {
        _selectedButtonId.value = checkedId
    }

    fun onButtonToggleGroupCheckedChange(checkedId: Int, isChecked: Boolean) {
        if (isChecked) {
            setSelectedButtonId(checkedId)
        }
    }

    fun onClickControlKRButton() {
        val action = MenuFragmentDirections.actionMenuFragmentToControlKRFragment()
        navController.navigate(action)
    }

    fun onClickControlMIAKRButton() {
        val action = MenuFragmentDirections.actionMenuFragmentToControlMIAKRFragment()
        navController.navigate(action)
    }

    fun onClickControlITMIAKRButton() {
        val action = MenuFragmentDirections.actionMenuFragmentToControlITMIAKRFragment()
        navController.navigate(action)
    }

}