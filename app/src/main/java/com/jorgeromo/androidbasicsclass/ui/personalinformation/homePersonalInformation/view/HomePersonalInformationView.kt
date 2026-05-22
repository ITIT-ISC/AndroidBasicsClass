package com.jorgeromo.androidbasicsclass.ui.personalinformation.homePersonalInformation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidbasicsclass.ui.personalinformation.homePersonalInformation.viewmodel.HomePersonalInformationViewModel

/**
 * Screen composable for the Personal Information section.
 * Displays centered placeholder text driven by [HomePersonalInformationViewModel].
 * @param homeViewModel ViewModel providing state for this screen.
 */
@Composable
fun HomePersonalInformationView(
    homeViewModel: HomePersonalInformationViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Personal Information")
    }
}
