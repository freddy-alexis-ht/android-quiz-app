package com.sunday.quiz1.ui.home

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sunday.quiz1.R
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.common.MyButton
import com.sunday.quiz1.ui.common.MyVerticalSpacer
import com.sunday.quiz1.ui.theme.spacing

@Composable
fun HomeScreen(
    onNavigate: (AppEvent.Navigate) -> Unit,
    homeVM: HomeVM,
    mem: Boolean?,
) {
    val configuration = LocalConfiguration.current

    LaunchedEffect(key1 = true) {
        homeVM.appEvent.collect { event ->
            when (event) {
                is AppEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    if (homeVM.state.isNew) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        homeVM.hideLoading()
    } else {
        when (configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                PortraitContentHome(homeVM, mem)
            }
            else -> {
                LandscapeContentHome(homeVM, mem)
            }
        }
    }
}

@Composable
fun PortraitContentHome(homeVM: HomeVM, mem: Boolean?) {
    val activity: Activity? = (LocalContext.current as? Activity)
    var show by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.mediumPlus),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Animation()
        ButtonStartRestartContinue(homeVM, mem)
        MyVerticalSpacer(MaterialTheme.spacing.medium)
        ButtonExit(onclick = { show = true })
        MyDialog(show, { show = false }, { homeVM.onEvent(HomeEvent.OnExit(activity)) })
    }
}

@Composable
fun LandscapeContentHome(homeVM: HomeVM, mem: Boolean?) {
    val activity: Activity? = (LocalContext.current as? Activity)
    var show by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.mediumPlus),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column() {
            Animation()
        }
        Column() {
            ButtonStartRestartContinue(homeVM, mem)
            MyVerticalSpacer(MaterialTheme.spacing.medium)
            ButtonExit(onclick = { show = true })
            MyDialog(show, { show = false }, { homeVM.onEvent(HomeEvent.OnExit(activity)) })
        }
    }
}

@Composable
fun Animation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))
    LottieAnimation(
        modifier = Modifier.size(MaterialTheme.spacing.lottieAnimation),
        composition = composition
    )
}

@Composable
fun ButtonStartRestartContinue(homeVM: HomeVM, mem: Boolean?) {
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
}

@Composable
fun ButtonExit(onclick: () -> Unit) {
    MyButton(
        onclick = { onclick() },
        text = stringResource(id = R.string.home_exit),
        colors = MaterialTheme.colors.secondary,
    )
}

@Composable
fun MyDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (show) {
        AlertDialog(
            title = {
                Column {
                    Text(text = stringResource(id = R.string.home_exit),
                        style = MaterialTheme.typography.body1)
                    MyVerticalSpacer(MaterialTheme.spacing.extraSmall)
                    Divider()
                }
            },
            text = {
                Text(text = stringResource(id = R.string.home_exit_question),
                    style = MaterialTheme.typography.body2)
            },
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = stringResource(id = R.string.dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() },
                ) {
                    Text(text = stringResource(id = R.string.dialog_cancel),
                        color = MaterialTheme.colors.secondary
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.background
        )
    }
}
