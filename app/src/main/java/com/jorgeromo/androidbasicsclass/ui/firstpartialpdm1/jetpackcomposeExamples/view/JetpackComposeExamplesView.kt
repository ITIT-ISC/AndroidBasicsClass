package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.jetpackcomposeExamples.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetpackComposeExamplesView(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jetpack Compose Examples") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
            Text(text = "Jetpack")
            Text(text = "Jorge Luis Romo",
                color = Color.Blue,
                fontSize = 30.sp

            )

        }
    }
}

@Preview
@Composable
fun JetpackComposeExamplesViewPreview() {
    JetpackComposeExamplesView()
}

/*
@Composable
fun HomeFirstPartialPDM1View(
    homeViewModel: HomeFirstPartialPDM1ViewModel = viewModel(),
    onNavigateToSharedPreferencesExample: () -> Unit = {}
) {

    Column {
        Text(text = "First Partial PDM1")
        Button(
            onClick = onNavigateToSharedPreferencesExample
        ) { Text("Shared Preferences")}

    }
}*/