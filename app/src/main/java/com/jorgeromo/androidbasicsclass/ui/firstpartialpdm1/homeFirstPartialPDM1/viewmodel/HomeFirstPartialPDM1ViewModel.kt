package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.homeFirstPartialPDM1.viewmodel

import androidx.lifecycle.ViewModel
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.homeFirstPartialPDM1.model.HomeFirstPartialPDM1Model

/**
 * ViewModel for the First Partial PDM1 home screen.
 * Holds and exposes UI state via [HomeFirstPartialPDM1Model] to [HomeFirstPartialPDM1View].
 */
class HomeFirstPartialPDM1ViewModel : ViewModel() {
    val homeModel = HomeFirstPartialPDM1Model()
}
