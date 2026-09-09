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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jorgeromo.androidbasicsclass.ui.auth.data.SessionPreferences
import com.jorgeromo.androidbasicsclass.ui.auth.view.LoginScreenView
import com.jorgeromo.androidbasicsclass.ui.auth.viewmodel.LogoutViewModel
import com.jorgeromo.androidbasicsclass.ui.onboarding.data.OnboardingPreferences
import com.jorgeromo.androidbasicsclass.ui.onboarding.view.OnboardingView
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.view.FirstApiRequestView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.detailBox.DetailBoxView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.detailColumn.DetailColumnView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.detailLogsToasts.DetailLogsToastsView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.detailRow.DetailRowView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.homeFirstPartialPDM1.view.HomeFirstPartialPDM1View
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.view.PetsHomeView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.view.SportsHomeView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.pickarooHome.view.PickarooHomeView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.cafeteriaHome.view.CafeteriaHomeView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.view.FinanceHomeView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.jetpackcomposeExamples.view.JetpackComposeExamplesView
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sharedPreferencesExample.view.SharedPreferencesExampleView
import com.jorgeromo.androidbasicsclass.ui.personalinformation.homePersonalInformation.view.HomePersonalInformationView
import com.jorgeromo.androidbasicsclass.ui.secondpartialpdm1.homeSecondPartialPDM1.view.HomeSecondPartialPDM1View
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.homeThirdPartialIDS2.view.HomeThirdPartialIDS2View
import com.jorgeromo.androidbasicsclass.ui.thirdpartialpdm1.homeThirdPartialPDM1.view.HomeThirdPartialPDM1View

/**
 * Una ruta de tab: solo describe CÓMO se ve en la barra inferior (label, icono)
 * y el [route] que la identifica dentro del NavHost interno de [TabsScaffold].
 * No sabe navegar por sí sola; solo es un dato.
 */
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

/**
 * Raíz de navegación de toda la app. Aquí vive el ÚNICO [rootNavController] de este grafo,
 * por eso es el único lugar donde tiene sentido escribir `.navigate(...)` para ir a una
 * pantalla que tapa por completo los tabs (login, detalle de un tab, etc).
 *
 * El NavHost de abajo es una tabla "ruta -> qué composable dibujar". Navegar es solo
 * decirle a [rootNavController] "muéstrame la entrada que tiene este string".
 */
@Composable
fun AppNavigation() {
    val rootNavController = rememberNavController()
    val context = LocalContext.current
    // Pantalla inicial según lo que ya está guardado en SharedPreferences:
    // - Si hay sesión activa (tokens guardados) → directo a "tabs".
    // - Si ya vio el onboarding pero no tiene sesión → "login".
    // - Si es la primera vez → "onboarding".
    val startDestination = remember {
        when {
            SessionPreferences(context).isLoggedIn() -> "tabs"
            OnboardingPreferences(context).hasCompletedOnboarding() -> "login"
            else -> "onboarding"
        }
    }

    NavHost(navController = rootNavController, startDestination = startDestination) {
        // Onboarding: primera pantalla que ve el usuario. Al terminar navega a "login"
        // y saca "onboarding" del back stack (inclusive = true) para que el botón de
        // back no regrese a las pantallas de onboarding.
        composable("onboarding") {
            OnboardingView(
                onFinishOnboarding = {
                    rootNavController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        // Pantalla de login: al loguear, navega a "tabs" y saca "login" del back stack
        // (inclusive = true) para que el botón de back no regrese a la pantalla de login.
        composable("login") {
            LoginScreenView(
                onLoginSuccess = {
                    rootNavController.navigate("tabs") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        // Contenedor de los 5 tabs. Aquí se "rellenan" los callbacks que las vistas de
        // cada tab solo declaran vacíos: es la única función con acceso a rootNavController,
        // así que es la única que puede decidir a qué ruta se navega.
        composable("tabs") {
            val logoutViewModel: LogoutViewModel = viewModel()
            TabsScaffold(
                onLogout = {
                    logoutViewModel.logout {
                        rootNavController.navigate("login") {
                            popUpTo("tabs") { inclusive = true }
                        }
                    }
                },
                onNavigateToFirstApiRequest = { rootNavController.navigate("first_api_request") },
                onNavigateToSharedPreferencesExample = { rootNavController.navigate("shared_preferences_example") },
                onNavigateToJetPackComposeExample = { rootNavController.navigate("jetpack_compose_examples") },
                onNavigateToDetailColumn = { rootNavController.navigate("detail_column") },
                onNavigateToDetailRow = { rootNavController.navigate("detail_row") },
                onNavigateToDetailBox = { rootNavController.navigate("detail_box") },
                onNavigateToDetailLogsToasts = { rootNavController.navigate("detail_logs_toasts") },
                onNavigateToPetsHome = { rootNavController.navigate("pets_home") },
                onNavigateToSportsHome = { rootNavController.navigate("sports_home") },
                onNavigateToPickarooHome = { rootNavController.navigate("pickaroo_home") },
                onNavigateToCafeteriaHome = { rootNavController.navigate("cafeteria_home") },
                onNavigateToFinanceHome = { rootNavController.navigate("finance_home") }
            )
        }
        // Pantallas de detalle: cada una se registra con el MISMO string que se usó
        // arriba en el navigate(...) correspondiente. onBack usa popBackStack(), lo
        // opuesto a navigate(): regresa a la pantalla anterior en vez de ir a una nueva.
        composable("first_api_request") {
            FirstApiRequestView(onBack = { rootNavController.popBackStack() })
        }
        composable("shared_preferences_example") {
            SharedPreferencesExampleView(onBack = { rootNavController.popBackStack() })
        }

        composable("jetpack_compose_examples") {
            JetpackComposeExamplesView(onBack = { rootNavController.popBackStack() })
        }

        composable("detail_column") {
            DetailColumnView(onBack = { rootNavController.popBackStack() })
        }

        composable("detail_row") {
            DetailRowView(onBack = { rootNavController.popBackStack() })
        }

        composable("detail_box") {
            DetailBoxView(onBack = { rootNavController.popBackStack() })
        }

        composable("detail_logs_toasts") {
            DetailLogsToastsView(onBack = { rootNavController.popBackStack() })
        }

        composable("pets_home") {
            PetsHomeView(onBack = { rootNavController.popBackStack() })
        }

        composable("sports_home") {
            SportsHomeView(onBack = { rootNavController.popBackStack() })
        }

        composable("pickaroo_home") {
            PickarooHomeView(onBack = { rootNavController.popBackStack() })
        }

        composable("cafeteria_home") {
            CafeteriaHomeView(onBack = { rootNavController.popBackStack() })
        }

        composable("finance_home") {
            FinanceHomeView(onBack = { rootNavController.popBackStack() })
        }
    }
}

/**
 * Scaffold con la barra inferior de tabs y su propio NavHost interno (con su propio
 * NavController, distinto al [AppNavigation.rootNavController]) para cambiar entre tabs
 * sin perder el bottomBar de pantalla.
 *
 * Recibe los callbacks de navegación hacia pantallas de detalle ya armados desde
 * [AppNavigation] y solo los reenvía a la vista del tab que los necesita — nunca
 * los abre ni conoce el string de la ruta destino.
 */
@Composable
private fun TabsScaffold(
    onLogout: () -> Unit,
    onNavigateToFirstApiRequest: () -> Unit,
    onNavigateToSharedPreferencesExample: () -> Unit,
    onNavigateToJetPackComposeExample: () -> Unit,
    onNavigateToDetailColumn: () -> Unit,
    onNavigateToDetailRow: () -> Unit,
    onNavigateToDetailBox: () -> Unit,
    onNavigateToDetailLogsToasts: () -> Unit,
    onNavigateToPetsHome: () -> Unit,
    onNavigateToSportsHome: () -> Unit,
    onNavigateToPickarooHome: () -> Unit,
    onNavigateToCafeteriaHome: () -> Unit,
    onNavigateToFinanceHome: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                TABS.forEach { tab ->
                    // Este navigate() es distinto al de AppNavigation: usa el navController
                    // interno de los tabs (no el rootNavController), así que solo cambia
                    // el contenido del NavHost de abajo, sin tocar el bottomBar.
                    // - popUpTo(startDestinationId) + saveState: evita apilar tabs infinitos
                    //   al ir y volver entre ellos (deja como máximo un tab por debajo).
                    // - launchSingleTop: si ya estás en ese tab, no crea una copia encima.
                    // - restoreState: si ya visitaste el tab, recupera su scroll/estado en vez
                    //   de recrearlo desde cero.
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
            composable(AppRoute.FirstPartialPDM1.route) {
                HomeFirstPartialPDM1View(
                    onNavigateToSharedPreferencesExample = onNavigateToSharedPreferencesExample,
                    onNavigateToJetPackComposeExample = onNavigateToJetPackComposeExample,
                    onNavigateToDetailColumn = onNavigateToDetailColumn,
                    onNavigateToDetailRow = onNavigateToDetailRow,
                    onNavigateToDetailBox = onNavigateToDetailBox,
                    onNavigateToDetailLogsToasts = onNavigateToDetailLogsToasts,
                    onNavigateToPetsHome = onNavigateToPetsHome,
                    onNavigateToSportsHome = onNavigateToSportsHome,
                    onNavigateToPickarooHome = onNavigateToPickarooHome,
                    onNavigateToCafeteriaHome = onNavigateToCafeteriaHome,
                    onNavigateToFinanceHome = onNavigateToFinanceHome
                )
            }
            composable(AppRoute.SecondPartialPDM1.route) { HomeSecondPartialPDM1View() }
            composable(AppRoute.ThirdPartialPDM1.route) { HomeThirdPartialPDM1View() }
            composable(AppRoute.PersonalInformation.route) {
                HomePersonalInformationView(onLogout = onLogout)
            }
        }
    }
}
