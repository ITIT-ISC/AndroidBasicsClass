package com.jorgeromo.androidbasicsclass.ui.personalinformation.homePersonalInformation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidbasicsclass.ui.personalinformation.homePersonalInformation.viewmodel.HomePersonalInformationViewModel

/**
 * Screen composable for the Personal Information section.
 * Displays centered placeholder text driven by [HomePersonalInformationViewModel]
 * and a "Cerrar sesión" button that triggers [onLogout].
 *
 * @param onLogout invoked when the user taps "Cerrar sesión"; the caller decides
 *   where to navigate afterwards.
 * @param homeViewModel ViewModel providing state for this screen.
 */
@Composable
fun HomePersonalInformationView(
    onLogout: () -> Unit = {},
    homeViewModel: HomePersonalInformationViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Personal Information")

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(onClick = onLogout) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar sesión")
        }
    }
}
