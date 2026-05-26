package com.jorgeromo.androidbasicsclass.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jorgeromo.androidbasicsclass.ui.login.LoginScreenView
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.view.FirstApiRequestView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.homeFirstPartialPDM1.view.HomeFirstPartialPDM1View
import com.jorgeromo.androidbasicsclass.ui.personalinformation.homePersonalInformation.view.HomePersonalInformationView
import com.jorgeromo.androidbasicsclass.ui.secondpartialpdm1.homeSecondPartialPDM1.view.HomeSecondPartialPDM1View
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.view.HomeThirdPartialIDS2View
import com.jorgeromo.androidbasicsclass.ui.thirdpartialpdm1.homeThirdPartialPDM1.view.HomeThirdPartialPDM1View

sealed class AppRoute(val route: String, val label: String, val icon: ImageVector) {
    object ThirdPartialIDS2 : AppRoute("third_partial_ids2", "IDS2 P3", Icons.Filled.School)
    object FirstPartialPDM1 : AppRoute("first_partial_pdm1", "PDM1 P1", Icons.Filled.PhoneAndroid)
    object SecondPartialPDM1 : AppRoute("second_partial_pdm1", "PDM1 P2", Icons.Outlined.PhoneAndroid)
    object ThirdPartialPDM1 : AppRoute("third_partial_pdm1", "PDM1 P3", Icons.Filled.Smartphone)
    object PersonalInformation : AppRoute("personal_information", "About Me", Icons.Filled.Person)
}

private val TABS = listOf(
    AppRoute.ThirdPartialIDS2,
    AppRoute.FirstPartialPDM1,
    AppRoute.SecondPartialPDM1,
    AppRoute.ThirdPartialPDM1,
    AppRoute.PersonalInformation
)

@Composable
fun AppNavigation() {
    val rootNavController = rememberNavController()

    NavHost(navController = rootNavController, startDestination = "login") {
        composable("login") {
            LoginScreenView(
                onLoginClick = {
                    rootNavController.navigate("tabs") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("tabs") {
            TabsScaffold(
                onNavigateToFirstApiRequest = { rootNavController.navigate("first_api_request") }
            )
        }
        composable("first_api_request") {
            FirstApiRequestView(onBack = { rootNavController.popBackStack() })
        }
    }
}

@Composable
private fun TabsScaffold(onNavigateToFirstApiRequest: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                TABS.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label, fontSize = 10.sp) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoute.ThirdPartialIDS2.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppRoute.ThirdPartialIDS2.route) {
                HomeThirdPartialIDS2View(onNavigateToFirstApiRequest = onNavigateToFirstApiRequest)
            }
            composable(AppRoute.FirstPartialPDM1.route) { HomeFirstPartialPDM1View() }
            composable(AppRoute.SecondPartialPDM1.route) { HomeSecondPartialPDM1View() }
            composable(AppRoute.ThirdPartialPDM1.route) { HomeThirdPartialPDM1View() }
            composable(AppRoute.PersonalInformation.route) { HomePersonalInformationView() }
        }
    }
}
