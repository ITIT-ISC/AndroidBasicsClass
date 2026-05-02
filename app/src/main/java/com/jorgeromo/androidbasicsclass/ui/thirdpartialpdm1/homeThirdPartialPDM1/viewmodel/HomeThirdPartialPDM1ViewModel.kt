package com.jorgeromo.androidbasicsclass.ui.thirdpartialpdm1.homeThirdPartialPDM1.viewmodel

import androidx.lifecycle.ViewModel
import com.jorgeromo.androidbasicsclass.ui.thirdpartialpdm1.homeThirdPartialPDM1.model.HomeThirdPartialPDM1Model

/**
 * ViewModel for the Third Partial PDM1 home screen.
 * Holds and exposes UI state via [HomeThirdPartialPDM1Model] to [HomeThirdPartialPDM1View].
 */
class HomeThirdPartialPDM1ViewModel : ViewModel() {
    val homeModel = HomeThirdPartialPDM1Model()
}
