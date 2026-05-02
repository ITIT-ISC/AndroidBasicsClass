package com.jorgeromo.androidbasicsclass.ui.thirdpartialpdm1.homeThirdPartialPDM1.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidbasicsclass.ui.thirdpartialpdm1.homeThirdPartialPDM1.viewmodel.HomeThirdPartialPDM1ViewModel

/**
 * Screen composable for the Third Partial PDM1 section.
 * Displays centered placeholder text driven by [HomeThirdPartialPDM1ViewModel].
 * @param homeViewModel ViewModel providing state for this screen.
 */
@Composable
fun HomeThirdPartialPDM1View(
    homeViewModel: HomeThirdPartialPDM1ViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Third Partial PDM1")
    }
}
