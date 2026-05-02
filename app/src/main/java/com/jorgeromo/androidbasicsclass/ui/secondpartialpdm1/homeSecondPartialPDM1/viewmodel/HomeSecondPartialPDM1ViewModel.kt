package com.jorgeromo.androidbasicsclass.ui.secondpartialpdm1.homeSecondPartialPDM1.viewmodel

import androidx.lifecycle.ViewModel
import com.jorgeromo.androidbasicsclass.ui.secondpartialpdm1.homeSecondPartialPDM1.model.HomeSecondPartialPDM1Model

/**
 * ViewModel for the Second Partial PDM1 home screen.
 * Holds and exposes UI state via [HomeSecondPartialPDM1Model] to [HomeSecondPartialPDM1View].
 */
class HomeSecondPartialPDM1ViewModel : ViewModel() {
    val homeModel = HomeSecondPartialPDM1Model()
}
