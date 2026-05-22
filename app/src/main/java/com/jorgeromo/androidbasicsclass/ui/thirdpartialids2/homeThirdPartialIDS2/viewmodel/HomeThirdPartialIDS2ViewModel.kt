package com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.viewmodel

import androidx.lifecycle.ViewModel
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.model.HomeThirdPartialIDS2Model

/**
 * ViewModel for the Third Partial IDS2 home screen.
 * Holds and exposes UI state via [HomeThirdPartialIDS2Model] to [HomeThirdPartialIDS2View].
 */
class HomeThirdPartialIDS2ViewModel : ViewModel() {
    val homeModel = HomeThirdPartialIDS2Model()
}
