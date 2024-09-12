package com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.menu

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.common.Messages
import com.mvdasker.geeks_pro_mvd.common.UiState
import com.mvdasker.geeks_pro_mvd.data.remote.model.authorization.User
import com.mvdasker.geeks_pro_mvd.data.remote.model.parent.ParentModel
import com.mvdasker.geeks_pro_mvd.data.repositories.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
) : ViewModel() {

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private val _isRecyclerViewVisible = MutableStateFlow(false)
    val isRecyclerViewVisible: StateFlow<Boolean> = _isRecyclerViewVisible

    private val _isSpinnerIconRotated = MutableStateFlow(false)
    val isSpinnerIconRotated: StateFlow<Boolean> get() = _isSpinnerIconRotated

    private val _getUserId = MutableStateFlow<UiState<User?>>(UiState.Loading)
    val getUserId: Flow<UiState<User?>> = _getUserId.filterNotNull()

    private val _messageFlow = MutableStateFlow<Messages?>(null)
    val messageFlow: Flow<Messages> = _messageFlow.filterNotNull()

    init {
        getUserId()
    }

    fun clearMessage() {
        _messageFlow.value = null
    }

    fun setNavController(navController: NavController) {
        _navController = navController
    }

    private fun getUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = menuRepository.getUserId()
                _getUserId.value = UiState.Success(result)
            } catch (e: Exception) {
                _getUserId.value = UiState.Error(throwable = e, message = "Error getting user")
                _messageFlow.value = Messages.NetworkIsDisconnected
            }
        }
    }

    fun getSampleData(context: Context): List<ParentModel> = listOf(
        ParentModel(context.getString(R.string.historyKyrgyzstan)),
        ParentModel(context.getString(R.string.historyVV_MVD_KR)),
        ParentModel(context.getString(R.string.history_mvd_kr))
    )

    fun onItemClick(position: Int) {
        val direction = when (position) {
            0 -> MenuFragmentDirections.actionMenuFragmentToHistoryOfKyrgyzstanFragment()
            1 -> MenuFragmentDirections.actionMenuFragmentToHistoryVVMVDKRFragment()
            2 -> MenuFragmentDirections.actionMenuFragmentToHistoryMVDKRFragment()
            else -> return
        }
        _isRecyclerViewVisible.value = true
        navController.navigate(direction)
    }

    fun onOpenDictionaryClick() = openWebView("https://el-sozduk.kg/")
    fun onMapClick() = openWebView("https://www.google.com/maps")
    fun onTrafficRulesClick() = openWebView("https://joldo.kg/ru")
    fun openInstagram() = openWebView("https://www.instagram.com/geeks_pro/")

    private fun openWebView(url: String) {
        val action = MenuFragmentDirections.actionMenuFragmentToWebViewFragment(url)
        navController.navigate(action)
    }

    fun toggleRecyclerViewVisibility() {
        _isRecyclerViewVisible.value = !_isRecyclerViewVisible.value
        _isSpinnerIconRotated.value = !_isSpinnerIconRotated.value
    }

    fun saveSelectedLanguage(languageCode: String) {
        menuRepository.saveSelectedLanguage(languageCode)
    }

    fun onClickControlKRButton() =
        navController.navigate(MenuFragmentDirections.actionMenuFragmentToControlKRFragment(0))

    fun getSavedLanguage(): String {
        return menuRepository.getSavedLanguage()
    }

    fun onClickControlMIAKRButton() =
        navController.navigate(MenuFragmentDirections.actionMenuFragmentToControlMIAKRFragment(0))

    fun onClickControlITMIAKRButton() =
        navController.navigate(MenuFragmentDirections.actionMenuFragmentToControlITMIAKRFragment(0))
}