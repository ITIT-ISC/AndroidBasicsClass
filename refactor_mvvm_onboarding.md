# Refactorización del Onboarding a MVVM

**Proyecto:** AndroidBasicsClass
**Feature:** Onboarding (pantallas de bienvenida al abrir la app por primera vez)

## Objetivo

El `OnboardingView` original era un único Composable que mezclaba tres responsabilidades:
datos (la lista de páginas), estado (página actual) y lógica (avanzar/retroceder), todo
dentro de la misma función `@Composable`. El objetivo fue separar esas responsabilidades
siguiendo el patrón **MVVM (Model – View – ViewModel)**, y de paso persistir si el usuario
ya vio el onboarding para no mostrárselo de nuevo en próximos inicios de la app.

## Estructura antes vs. después

**Antes:**
```
ui/onboarding/
└── OnboardingView.kt   (datos + estado + UI + lógica, todo junto)
```

**Después:**
```
ui/onboarding/
├── model/
│   └── OnboardingModel.kt        (datos y estado)
├── viewmodel/
│   └── OnboardingViewModel.kt    (lógica y estado observable)
├── view/
│   └── OnboardingView.kt         (solo UI)
└── data/
    └── OnboardingPreferences.kt  (persistencia: onboarding ya visto)

common/preferences/
└── AppPreferences.kt             (acceso centralizado a SharedPreferences)
```

## 1. Model — `ui/onboarding/model/OnboardingModel.kt`

Contiene únicamente **datos**, sin ninguna lógica de Android/UI:

- `OnboardingPage`: un `data class` con el ícono, título y descripción de una página.
- `OnboardingUiState`: el estado completo de la pantalla — la lista de páginas y el índice
  de la página actual — más una propiedad calculada `isLastPage` que indica si se está en
  la última página.

**Por qué:** antes la lista de páginas y el cálculo de "es la última página" vivían sueltos
dentro del Composable. Aislarlos en un `model` los hace reutilizables y fáciles de testear
sin necesidad de renderizar UI.

## 2. ViewModel — `ui/onboarding/viewmodel/OnboardingViewModel.kt`

Es el dueño del estado y de toda la lógica de negocio del onboarding:

- Define la lista de las 3 páginas (antes estaba fuera del Composable, como variable global
  del archivo).
- Expone el estado como `StateFlow<OnboardingUiState>` (patrón estándar de MVVM en Compose:
  `MutableStateFlow` privado + versión pública de solo lectura con `asStateFlow()`).
- Expone funciones para modificar el estado: `onPageChanged(index)`, `goToNextPage()`,
  `goToPreviousPage()`, `completeOnboarding()`.
- Extiende `AndroidViewModel(application)` en vez de `ViewModel()` normal, para tener acceso
  seguro al `Context` de la aplicación (necesario para leer/escribir en `SharedPreferences`
  sin arriesgar fugas de memoria).

**Por qué:** la vista ya no decide "cuál es la siguiente página" ni conoce el listado de
páginas; solo le pide al ViewModel que avance/retroceda y observa el resultado. Esto permite
probar la lógica de navegación entre páginas sin necesidad de UI.

## 3. View — `ui/onboarding/view/OnboardingView.kt`

Quedó reducido a solo **dibujar** lo que el ViewModel expone:

- Observa `viewModel.uiState` con `collectAsState()`.
- Usa dos `LaunchedEffect` para mantener sincronizado el `PagerState` de Compose (que maneja
  el gesto de swipe) con el estado del ViewModel en ambas direcciones:
  - Si el usuario desliza manualmente → avisa al ViewModel (`onPageChanged`).
  - Si el ViewModel cambia de página (por los botones) → anima el `PagerState` a esa página.
- Los botones "Anterior" / "Siguiente" / "Iniciar sesión" ya no calculan nada: solo llaman a
  `viewModel.goToPreviousPage()`, `viewModel.goToNextPage()` o
  `viewModel.completeOnboarding()`.

**Por qué:** es el principio central de MVVM — la View no debe tomar decisiones, solo
reaccionar a un estado y reenviar eventos.

## 4. Persistencia — "mostrar el onboarding una sola vez"

### `ui/onboarding/data/OnboardingPreferences.kt`

Clase pequeña con dos métodos de dominio:

- `hasCompletedOnboarding(): Boolean`
- `setOnboardingCompleted()`

No sabe nada de `SharedPreferences` en detalle; delega en `AppPreferences`.

### `common/preferences/AppPreferences.kt`

Módulo **centralizado** para toda la app: abre un único archivo de `SharedPreferences`
(`app_prefs`) y expone una API genérica (`getBoolean/putBoolean`, `getString/putString`).
Cualquier otra pantalla que necesite guardar algo simple (por ejemplo, el ejercicio de
`SharedPreferencesExample`) puede reutilizar esta clase en vez de volver a escribir
`context.getSharedPreferences(...)`.

**Por qué separar `AppPreferences` de `OnboardingPreferences`:**
- `AppPreferences` = *cómo* se guarda algo (mecanismo genérico, reusable por toda la app).
- `OnboardingPreferences` = *qué* se guarda y bajo qué nombre (`completed_onboarding`),
  específico del feature de onboarding.

## 5. Navegación — `ui/navigation/AppNavigation.kt`

Se agregó, antes de construir el `NavHost`:

```kotlin
val startDestination = remember {
    if (OnboardingPreferences(context).hasCompletedOnboarding()) "login" else "onboarding"
}
```

**Por qué:** si el usuario ya completó el onboarding en un inicio anterior, la app arranca
directo en `"login"`; si no, arranca en `"onboarding"` como antes.

## Flujo completo resultante

1. La app arranca → `AppNavigation` consulta `OnboardingPreferences.hasCompletedOnboarding()`.
2. Si es la primera vez → se muestra `OnboardingView`, que observa `OnboardingViewModel`.
3. El usuario navega entre páginas (swipe o botones) → `OnboardingViewModel` actualiza su
   `StateFlow` y la vista se recompone sola.
4. En la última página, el usuario toca "Iniciar sesión" →
   `OnboardingViewModel.completeOnboarding()` guarda `true` en `SharedPreferences` (vía
   `OnboardingPreferences` → `AppPreferences`) y luego se navega a `"login"`.
5. La próxima vez que se abra la app, `AppNavigation` encuentra el flag en `true` y salta
   el onboarding por completo.

## Resumen de archivos

| Archivo | Tipo de cambio | Rol en MVVM |
|---|---|---|
| `model/OnboardingModel.kt` | Nuevo | Model |
| `viewmodel/OnboardingViewModel.kt` | Nuevo | ViewModel |
| `view/OnboardingView.kt` | Movido y simplificado | View |
| `data/OnboardingPreferences.kt` | Nuevo | Persistencia del feature |
| `common/preferences/AppPreferences.kt` | Nuevo | Persistencia central de la app |
| `ui/navigation/AppNavigation.kt` | Modificado | Decide pantalla inicial según preferencia |
