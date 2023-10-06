package com.sunday.quiz1.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.theme.Quiz1Theme

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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mem!!) {
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
        OutlinedButton(onClick = { homeVM.onEvent(HomeEvent.OnExit) }) {
            Text(text = "Salir")
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Composable
fun StartPreview() {
    Quiz1Theme {
        HomeScreen(onNavigate = {}, homeVM = HomeVM(), mem = true)
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Composable
fun RestartPreview() {
    Quiz1Theme {
        HomeScreen(onNavigate = {}, homeVM = HomeVM(), mem = false)
    }
}