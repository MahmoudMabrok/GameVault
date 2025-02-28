package com.mostafadevo.freegames.ui.screens.settings_screen

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.freegames.domain.model.ThemePreference
import com.mostafadevo.freegames.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {
    val themeState : StateFlow<ThemePreference> = dataStoreRepository.getThemePreference().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ThemePreference.SYSTEM
    )
    val dynamicThemeState : StateFlow<Boolean> = dataStoreRepository.getDynamicThemePereference().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )
    fun onEvent(event: SettingsScreenEvent) {
        when(event) {
            is SettingsScreenEvent.SaveThemePreference -> {
                viewModelScope.launch{
                    saveThemePreference(event.themePreference)
                }
            }

            is SettingsScreenEvent.SaveDynamicThemePreference -> {
                viewModelScope.launch{
                    saveDynamicThemePreference(event.dynamicTheme)
                }
            }
        }
    }
    suspend fun saveThemePreference(themePreference: ThemePreference) {
        dataStoreRepository.saveThemePreference(themePreference)
    }

    suspend fun saveDynamicThemePreference(isDynamic: Boolean) {
        dataStoreRepository.saveDynamicThemePreference(isDynamic)
    }
    // TODO: clear search history
    // TODO: change search history limit
}
