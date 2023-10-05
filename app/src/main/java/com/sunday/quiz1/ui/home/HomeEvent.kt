package com.sunday.quiz1.ui.home

sealed class HomeEvent {
    object OnStart: HomeEvent()
    object OnContinue: HomeEvent()
    object OnExit: HomeEvent()
}