package com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.viewmodel.HomeThirdPartialIDS2ViewModel

/**
 * Screen composable for the Third Partial IDS2 section.
 * Displays centered placeholder text driven by [HomeThirdPartialIDS2ViewModel].
 * @param homeViewModel ViewModel providing state for this screen.
 */
@Composable
fun HomeThirdPartialIDS2View(
    homeViewModel: HomeThirdPartialIDS2ViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Third Partial IDS2")
    }
}
