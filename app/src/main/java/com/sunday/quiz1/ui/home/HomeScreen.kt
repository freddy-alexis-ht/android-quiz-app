package com.sunday.quiz1.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import com.sunday.quiz1.ui.common.AppEvent

@Composable
fun HomeScreen(
    onNavigate: (AppEvent.Navigate) -> Unit,
    homeVM: HomeVM,
    mem: Boolean?
) {
    LaunchedEffect(key1 = true) {
        homeVM.appEvent.collect { event ->
            when (event) {
                is AppEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(mem!!) {
            Button(onClick = { homeVM.onEvent(HomeEvent.OnStart) }) {
                Text(text = "Iniciar")
            }
        } else {
            Button(onClick = { homeVM.onEvent(HomeEvent.OnStart) }) {
                Text(text = "Reiniciar")
            }
            Button(onClick = { homeVM.onEvent(HomeEvent.OnContinue) }) {
                Text(text = "Continuar")
            }
        }
        Button(onClick = { homeVM.onEvent(HomeEvent.OnExit) }) {
            Text(text = "Salir")
        }
    }
}