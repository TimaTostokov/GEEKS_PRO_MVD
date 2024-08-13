package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu.MenuFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun setSelectedFragment(fragmentId: Int) {
        _selectedFragment.value = fragmentId
    }

    fun setNavController(navController: NavController) {
        _navController = navController
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