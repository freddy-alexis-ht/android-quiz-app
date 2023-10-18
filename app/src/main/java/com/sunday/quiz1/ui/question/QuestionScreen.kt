package com.sunday.quiz1.ui.question

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sunday.quiz1.R
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.ui.common.*
import com.sunday.quiz1.ui.theme.spacing
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun QuestionScreen(
    onNavigate: (AppEvent.Navigate) -> Unit,
    questionVM: QuestionVM,
    newQuiz: Boolean?,
) {
    var index = questionVM.state.index
    val questions = questionVM.questions
    val numberOfQuestions = questionVM.questions.size
    val lazyListState: LazyListState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        questionVM.appEvent.collect { event ->
            when (event) {
                is AppEvent.Navigate -> onNavigate(event)
                AppEvent.PopBackStack -> questionVM.onEvent((QuestionEvent.OnHome))
            }
        }
    }

    var lastNewQuiz: Boolean? by rememberSaveable { mutableStateOf(null) }
    if (lastNewQuiz != newQuiz) {
        lastNewQuiz = newQuiz
        LaunchedEffect(newQuiz) {
            if (newQuiz!!) {
                questionVM.clearUserOptions()
            }
        }
    }

    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            PortraitContentQuestion(questionVM, index, numberOfQuestions, questions, lazyListState)
        }
        else -> {
            LandscapeContentQuestion(questionVM, index, numberOfQuestions, questions, lazyListState)
        }
    }
}

@Composable
fun PortraitContentQuestion(
    questionVM: QuestionVM,
    index: Int,
    numberOfQuestions: Int,
    questions: List<Question>,
    lazyListState: LazyListState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(MaterialTheme.spacing.mediumPlus)
            .verticalScroll(rememberScrollState())
    ) {
        RowTimer(questionVM)
        MyVerticalSpacer(MaterialTheme.spacing.extraSmall)

        RowProgression(questionVM, index, numberOfQuestions, lazyListState)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        RowQuestionSwitch(questionVM, index, numberOfQuestions, lazyListState)
        MyVerticalSpacer(MaterialTheme.spacing.large)

        RowQuestion(index, questions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        RadioButtonOptions(questionVM, index, questions)
        MyVerticalSpacer(MaterialTheme.spacing.large)

        ButtonNav(questionVM, index, numberOfQuestions, lazyListState)
    }
}

@Composable
fun LandscapeContentQuestion(
    questionVM: QuestionVM,
    index: Int,
    numberOfQuestions: Int,
    questions: List<Question>,
    lazyListState: LazyListState,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.mediumPlus),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(end = MaterialTheme.spacing.medium)
            .verticalScroll(rememberScrollState())
        ) {
            RowQuestion(index, questions)
            MyVerticalSpacer(MaterialTheme.spacing.medium)

            RadioButtonOptions(questionVM, index, questions)
            MyVerticalSpacer(MaterialTheme.spacing.large)
        }
        VerticalDivider()
        Column(modifier = Modifier
            .weight(1f)
            .padding(start = MaterialTheme.spacing.medium)) {
            RowTimer(questionVM)
            MyVerticalSpacer(MaterialTheme.spacing.extraSmall)

            RowProgression(questionVM, index, numberOfQuestions, lazyListState)
            MyVerticalSpacer(MaterialTheme.spacing.medium)

            RowQuestionSwitch(questionVM, index, numberOfQuestions, lazyListState)
            MyVerticalSpacer(MaterialTheme.spacing.large)

            ButtonNav(questionVM, index, numberOfQuestions, lazyListState)
        }
    }
}

/* Main Composables */

@Composable
fun RowTimer(questionVM: QuestionVM) {
    var ticks = questionVM.timer.ticks
    var continueRestart = questionVM.timer.continueRestart
    LaunchedEffect(continueRestart) {
        while (true) {
            delay(1.seconds)
            questionVM.increaseTimer(ticks++)
        }
    }
    val formatTimer = questionVM.formatTimer(ticks)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTimer,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun RowProgression(
    questionVM: QuestionVM,
    index: Int,
    numberOfQuestions: Int,
    lazyListState: LazyListState,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextNumberOfQuestion(index, numberOfQuestions)
        MyHorizontalSpacer(MaterialTheme.spacing.medium)
        MyProgressIndicator(index, numberOfQuestions, Modifier.weight(1.0f, true))
        MyHorizontalSpacer(MaterialTheme.spacing.medium)
        IconButtonPrevious(questionVM, index, lazyListState)
    }
}

@Composable
fun TextNumberOfQuestion(index: Int, numberOfQuestions: Int) {
    Text(
        text = stringResource(id = R.string.question_number,
            index + 1, numberOfQuestions),
        style = MaterialTheme.typography.body2
    )
}

@Composable
fun MyProgressIndicator(index: Int, numberOfQuestions: Int, modifier: Modifier) {
    LinearProgressIndicator(
        progress = (index + 1) / numberOfQuestions.toFloat(),
        modifier = modifier.width(200.dp)
    )
}

@Composable
fun IconButtonPrevious(
    questionVM: QuestionVM,
    index: Int,
    lazyListState: LazyListState,
) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = MaterialTheme.spacing.default,
            color = MaterialTheme.colors.primary),
    ) {
        IconButton(
            onClick = {
                questionVM.onEvent(QuestionEvent.OnPrevious(index))
                coroutineScope.launch {
                    lazyListState.animateScrollAndCentralizeItem(index - 1)
                }
            },
            modifier = Modifier.background(
                if (index != 0) MaterialTheme.colors.secondary
                else MaterialTheme.colors.surface
            ),
            enabled = index != 0,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.question_previous),
            )
        }
    }
}

suspend fun LazyListState.animateScrollAndCentralizeItem(index: Int) {
    val itemInfo = this.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    if (itemInfo != null) {
        val center = layoutInfo.viewportEndOffset / 2
        val childCenter = itemInfo.offset + itemInfo.size / 2
        animateScrollBy((childCenter - center).toFloat())
    } else {
        animateScrollToItem(index)
    }
}

@Composable
fun RowQuestionSwitch(
    questionVM: QuestionVM,
    index: Int,
    numberOfQuestions: Int,
    lazyListState: LazyListState,
) {
    var answers = questionVM.state.userAnswers
    var color: Color
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small,
            Alignment.CenterHorizontally),
    ) {
        items(numberOfQuestions) { i ->
            color =
                if (i == index) MaterialTheme.colors.primary
                else if (answers[i] == null) MaterialTheme.colors.secondaryVariant
                else MaterialTheme.colors.primaryVariant

            Card(
                shape = MaterialTheme.shapes.large,
                border = BorderStroke(width = MaterialTheme.spacing.simple,
                    color = MaterialTheme.colors.primary),
                backgroundColor = color,
                modifier = Modifier.width(MaterialTheme.spacing.large)
            ) {
                IconButton(
                    onClick = {
                        questionVM.onEvent(QuestionEvent.OnJumpTo(index, i))
                        coroutineScope.launch {
                            lazyListState.animateScrollAndCentralizeItem(i)
                        }
                    },
                ) {
                    Text(
                        text = (i + 1).toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Composable
fun RowQuestion(index: Int, questions: List<Question>) {
    Card(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = questions[index].question,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
        )
    }
}

@Composable
fun RadioButtonOptions(questionVM: QuestionVM, index: Int, questions: List<Question>) {

    val options: List<String> = questions[index].options

    options.forEach {
        var selected = questionVM.userOptions[index] == it
        var color =
            if (selected) MaterialTheme.colors.primaryVariant
            else MaterialTheme.colors.secondaryVariant
        Card(
            backgroundColor = color,
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { questionVM.onEvent(QuestionEvent.OnRbChange(it, index)) }
            ) {
                RadioButton(
                    selected = selected,
                    onClick = { questionVM.onEvent(QuestionEvent.OnRbChange(it, index)) }
                )
                Text(text = it, style = MaterialTheme.typography.body2)
            }
        }
        if (it != options.last()) MyVerticalSpacer(MaterialTheme.spacing.small)
    }
}

@Composable
fun ButtonNav(questionVM: QuestionVM, index: Int, numberOfQuestions: Int, lazyListState: LazyListState) {
    val coroutineScope = rememberCoroutineScope()
    if (index != numberOfQuestions - 1) {
        MyButton(
            onclick = {
                questionVM.onEvent(QuestionEvent.OnNext(index))
                coroutineScope.launch {
                    lazyListState.animateScrollAndCentralizeItem(index + 1)
                }
            },
            text = stringResource(id = R.string.question_next)
        )
    } else {
        var show by rememberSaveable { mutableStateOf(false) }
        MyButton(
            onclick = {
                questionVM.validateUserOption(index)
                val nullAnswers = questionVM.state.userAnswers.count { it == null }
                if (nullAnswers == 0) questionVM.onEvent(QuestionEvent.OnFinish(index))
                else show = true
            },
            text = stringResource(id = R.string.question_finish)
        )
        MyDialog(
            show = show,
            nullAnswers = questionVM.state.userAnswers.count { it == null },
            onDismiss = { show = false },
            onConfirm = { questionVM.onEvent(QuestionEvent.OnFinish(index)) }
        )
    }
    MyVerticalSpacer(MaterialTheme.spacing.medium)
    MyButton(
        onclick = { questionVM.onEvent((QuestionEvent.OnHome)) },
        text = stringResource(id = R.string.question_home)
                + stringResource(id = R.string.question_home_pause_clock),
        colors = MaterialTheme.colors.secondary
    )
}

@Composable
fun MyDialog(
    show: Boolean,
    nullAnswers: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (show) {
        AlertDialog(
            title = {
                Column {
                    Text(stringResource(id = R.string.question_finish),
                        style = MaterialTheme.typography.body1)
                    MyVerticalSpacer(MaterialTheme.spacing.extraSmall)
                    Divider()
                }
            },
            text = {
                Text(text = stringResource(id = R.string.question_finish_question, nullAnswers),
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
