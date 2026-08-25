package com.jorgeromo.androidbasicsclass.ui.onboarding.data

import android.content.Context
import com.jorgeromo.androidbasicsclass.common.preferences.AppPreferences

private const val KEY_COMPLETED_ONBOARDING = "completed_onboarding"

/**
 * Expone, con nombres de dominio, si el usuario ya vio el onboarding, para no volver
 * a mostrarlo en próximos inicios de la app. La persistencia real vive en [AppPreferences].
 */
class OnboardingPreferences(context: Context) {

    private val appPreferences = AppPreferences(context)

    fun hasCompletedOnboarding(): Boolean =
        appPreferences.getBoolean(KEY_COMPLETED_ONBOARDING)

    fun setOnboardingCompleted() {
        appPreferences.putBoolean(KEY_COMPLETED_ONBOARDING, true)
    }
}
