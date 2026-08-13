package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.homeFirstPartialPDM1.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.homeFirstPartialPDM1.viewmodel.HomeFirstPartialPDM1ViewModel

/**
 * Screen composable for the First Partial PDM1 section.
 * Displays centered placeholder text driven by [HomeFirstPartialPDM1ViewModel].
 * @param homeViewModel ViewModel providing state for this screen.
 */
@Composable
fun HomeFirstPartialPDM1View(
    homeViewModel: HomeFirstPartialPDM1ViewModel = viewModel(),
    onNavigateToSharedPreferencesExample: () -> Unit = {},
    onNavigateToJetPackComposeExample: () -> Unit = {}
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {
        Text(text = "First Partial PDM1")

        Button(
            onClick = onNavigateToSharedPreferencesExample,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Shared Preferences")}

        Button(onClick = {
            onNavigateToJetPackComposeExample()
        }, modifier = Modifier.fillMaxWidth())
        { Text("Jetpack Compose Examples") }

    }
}
