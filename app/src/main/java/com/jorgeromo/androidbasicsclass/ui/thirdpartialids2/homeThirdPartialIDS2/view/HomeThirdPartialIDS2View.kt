package com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.viewmodel.HomeThirdPartialIDS2ViewModel

@Composable
fun HomeThirdPartialIDS2View(
    onNavigateToFirstApiRequest: () -> Unit = {},
    homeViewModel: HomeThirdPartialIDS2ViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Third Partial IDS2")
        Button(onClick = onNavigateToFirstApiRequest) {
            Text("First API Request")
        }
    }
}
