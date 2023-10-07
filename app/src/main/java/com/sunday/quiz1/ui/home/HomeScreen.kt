package com.sunday.quiz1.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunday.quiz1.R
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.common.MyButton
import com.sunday.quiz1.ui.common.MySpacer16Dp
import com.sunday.quiz1.ui.theme.Purple200
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
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mem!!) {
            MyButton(
                onclick = { homeVM.onEvent(HomeEvent.OnStart) },
                text = stringResource(id = R.string.home_start)
            )
        } else {
            MyButton(
                onclick = { homeVM.onEvent(HomeEvent.OnContinue) },
                text = stringResource(id = R.string.home_continue)
            )
            MySpacer16Dp()
            MyButton(
                onclick = { homeVM.onEvent(HomeEvent.OnStart) },
                text = stringResource(id = R.string.home_restart),
                colors = Color.Gray
            )
        }
        MySpacer16Dp()
        MyButton(
            onclick = { homeVM.onEvent(HomeEvent.OnExit) },
            text = stringResource(id = R.string.home_exit),
            colors = MaterialTheme.colors.surface,
        )
    }
}


@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Composable
fun StartPreview() {
    Quiz1Theme {
        Surface() {
            HomeScreen(onNavigate = {}, homeVM = HomeVM(), mem = true)
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Composable
fun RestartPreview() {
    Quiz1Theme {
        Surface() {
            HomeScreen(onNavigate = {}, homeVM = HomeVM(), mem = false)
        }
    }
}