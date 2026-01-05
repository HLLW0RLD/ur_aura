package com.example.ur_color.ui.screen.viewModel

import com.example.ur_color.R
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.AuraSection
import com.example.ur_color.ui.theme.AuraColors
import com.example.ur_color.utils.auraSections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LabViewModel : BaseViewModel()  {

    private val _auraSectionsState = MutableStateFlow<List<AuraSection>>(auraSections)
    val auraSectionsState = _auraSectionsState.asStateFlow()

}
