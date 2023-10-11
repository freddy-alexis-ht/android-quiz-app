package com.sunday.quiz1.ui.home

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sunday.quiz1.R
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.common.MyButton
import com.sunday.quiz1.ui.common.MyVerticalSpacer
import com.sunday.quiz1.ui.theme.Quiz1Theme
import com.sunday.quiz1.ui.theme.spacing

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
            .padding(MaterialTheme.spacing.mediumPlus),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Animation()
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
            MyVerticalSpacer(MaterialTheme.spacing.medium)
            MyButton(
                onclick = { homeVM.onEvent(HomeEvent.OnStart) },
                text = stringResource(id = R.string.home_restart),
                colors = MaterialTheme.colors.secondaryVariant
            )
        }
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        val activity: Activity? = (LocalContext.current as? Activity)
        MyButton(
            onclick = { homeVM.onEvent(HomeEvent.OnExit(activity)) },
            text = stringResource(id = R.string.home_exit),
            colors = MaterialTheme.colors.secondary,
        )
    }
}

@Composable
fun Animation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))
    LottieAnimation(
        modifier = Modifier.size(400.dp),
        composition = composition
    )
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