package com.sunday.quiz1.ui.home

import android.app.Activity

sealed class HomeEvent {
    object OnStart: HomeEvent()
    object OnContinue: HomeEvent()
    data class OnExit(val activity: Activity?): HomeEvent()
}