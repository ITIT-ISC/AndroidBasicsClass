# AndroidBasicsClass

Proyecto Android de referencia con fines **educativos**, diseñado para aplicar conocimientos de **Ingeniería de Software** y **Programación de Dispositivos Móviles**. Sirve como base de estudio para comprender la estructura de una aplicación Android moderna usando Jetpack Compose, arquitectura MVVM y navegación por pestañas.

---

## Objetivo

Demostrar la implementación práctica de:

- Arquitectura **MVVM** (Model · View · ViewModel)
- Navegación con **Jetpack Compose Navigation** y barra inferior (`NavigationBar`)
- Organización de paquetes por feature
- Estándares de codificación aplicados a Kotlin

---

## Pantallas

| # | Pestaña | Ícono | Ruta |
|---|---------|-------|------|
| 1 | Third Partial IDS2 | School | `third_partial_ids2` |
| 2 | First Partial PDM1 | PhoneAndroid | `first_partial_pdm1` |
| 3 | Second Partial PDM1 | PhoneAndroid (outlined) | `second_partial_pdm1` |
| 4 | Third Partial PDM1 | Smartphone | `third_partial_pdm1` |
| 5 | Personal Information | Person | `personal_information` |

---

## Arquitectura

Cada pantalla sigue el patrón **MVVM** dentro de su propio paquete feature:

```
feature/
├── model/          ← data class con los datos de la pantalla
├── view/           ← @Composable que renderiza la UI
└── viewmodel/      ← ViewModel que expone el estado al View
```

La navegación central vive en `ui/navigation/AppNavigation.kt`, que define las rutas como una `sealed class` y compone el `Scaffold` con el `NavHost`.

---

## Estructura del proyecto

```
app/src/main/java/com/jorgeromo/androidbasicsclass/
├── ui/
│   ├── navigation/
│   │   └── AppNavigation.kt
│   ├── thirdpartialids2/
│   │   └── homeThirdPartialIDS2/
│   │       ├── model/      HomeThirdPartialIDS2Model.kt
│   │       ├── view/       HomeThirdPartialIDS2View.kt
│   │       └── viewmodel/  HomeThirdPartialIDS2ViewModel.kt
│   ├── firstpartialpdm1/
│   │   └── homeFirstPartialPDM1/
│   │       ├── model/      HomeFirstPartialPDM1Model.kt
│   │       ├── view/       HomeFirstPartialPDM1View.kt
│   │       └── viewmodel/  HomeFirstPartialPDM1ViewModel.kt
│   ├── secondpartialpdm1/
│   │   └── homeSecondPartialPDM1/
│   │       ├── model/      HomeSecondPartialPDM1Model.kt
│   │       ├── view/       HomeSecondPartialPDM1View.kt
│   │       └── viewmodel/  HomeSecondPartialPDM1ViewModel.kt
│   ├── thirdpartialpdm1/
│   │   └── homeThirdPartialPDM1/
│   │       ├── model/      HomeThirdPartialPDM1Model.kt
│   │       ├── view/       HomeThirdPartialPDM1View.kt
│   │       └── viewmodel/  HomeThirdPartialPDM1ViewModel.kt
│   └── personalinformation/
│       └── homePersonalInformation/
│           ├── model/      HomePersonalInformationModel.kt
│           ├── view/       HomePersonalInformationView.kt
│           └── viewmodel/  HomePersonalInformationViewModel.kt
└── MainActivity.kt
```

---

## Estándar de codificación

| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Variables | `camelCase` en inglés | `userEmail` |
| Constantes | `SCREAMING_SNAKE_CASE` | `MAX_RETRY_COUNT` |
| Funciones | `camelCase` iniciando con verbo | `fetchUserData()` |
| Clases | `PascalCase` | `HomeScreenViewModel` |
| Views (Composables) | Sufijo `View` | `HomeThirdPartialIDS2View` |
| Models | Sufijo `Model` | `HomeThirdPartialIDS2Model` |
| ViewModels | Sufijo `ViewModel` | `HomeThirdPartialIDS2ViewModel` |
| Inmutabilidad | Preferir `val` sobre `var` | `val title: String` |
| Documentación | KDoc `/** ... */` en clases y funciones | ver código fuente |
| Comentarios inline | `//` para aclaraciones no obvias | `// outlined variant for tab 3` |

---

## Dependencias principales

| Librería | Versión | Uso |
|----------|---------|-----|
| Jetpack Compose BOM | 2026.02.01 | UI declarativa |
| Navigation Compose | 2.8.9 | Navegación entre pantallas |
| Lifecycle ViewModel Compose | 2.8.7 | Integración ViewModel con Compose |
| Material Icons Extended | (BOM) | Íconos de la barra de navegación |
| Material3 | (BOM) | Componentes y tema visual |

---

## Requisitos

- Android Studio Meerkat o superior
- SDK mínimo: API 28 (Android 9)
- SDK objetivo: API 36
- Kotlin 2.2.10

---

## Ramas

| Rama | Descripción |
|------|-------------|
| `main` | Proyecto base generado por Android Studio |
| `feat/basicNavigation` | Implementación de navegación con 5 pestañas + MVVM |

---

## Cómo ejecutar

1. Clona el repositorio
   ```bash
   git clone https://github.com/ingjromo/AndroidBasicsClass.git
   ```
2. Cambia a la rama que deseas estudiar
   ```bash
   git checkout feat/basicNavigation
   ```
3. Abre el proyecto en **Android Studio**
4. Sincroniza Gradle y ejecuta en un emulador o dispositivo físico (API 28+)
