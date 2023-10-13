package com.sunday.quiz1.ui.home

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    mem: Boolean?,
) {
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
            var show by rememberSaveable { mutableStateOf(false) }
            MyButton(
                onclick = { show = true },
                text = stringResource(id = R.string.home_exit),
                colors = MaterialTheme.colors.secondary,
            )
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
                    Text(text = stringResource(id = R.string.home_exit_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() },
                ) {
                    Text(text = stringResource(id = R.string.home_exit_cancel),
                        color = MaterialTheme.colors.secondary
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.background
        )
    }
}


@Preview(name = "Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true,
    showBackground = true)
@Preview(name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true)
@Composable
fun StartPreview() {
    Quiz1Theme {
        Surface() {
            HomeScreen(onNavigate = {}, homeVM = HomeVM(), mem = true)
        }
    }
}

@Preview(name = "Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true,
    showBackground = true)
@Preview(name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true)
@Composable
fun RestartPreview() {
    Quiz1Theme {
        Surface() {
            HomeScreen(onNavigate = {}, homeVM = HomeVM(), mem = false)
        }
    }
}