package com.jorgeromo.androidbasicsclass.ui.secondpartialpdm1.homeSecondPartialPDM1.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidbasicsclass.ui.secondpartialpdm1.homeSecondPartialPDM1.viewmodel.HomeSecondPartialPDM1ViewModel

/**
 * Screen composable for the Second Partial PDM1 section.
 * Displays centered placeholder text driven by [HomeSecondPartialPDM1ViewModel].
 * @param homeViewModel ViewModel providing state for this screen.
 */
@Composable
fun HomeSecondPartialPDM1View(
    homeViewModel: HomeSecondPartialPDM1ViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Second Partial PDM1")
    }
}
