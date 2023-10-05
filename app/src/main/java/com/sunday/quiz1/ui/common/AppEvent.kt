package com.sunday.quiz1.ui.common

sealed class AppEvent {
    object PopBackStack: AppEvent()
    data class Navigate(val route: String): AppEvent()
}
