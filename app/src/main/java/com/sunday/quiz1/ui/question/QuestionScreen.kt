package com.sunday.quiz1.ui.question

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sunday.quiz1.R
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.ui.common.*
import com.sunday.quiz1.ui.theme.spacing
import kotlinx.coroutines.delay
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

    LaunchedEffect(key1 = true) {
        questionVM.appEvent.collect { event ->
            when (event) {
                is AppEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    LaunchedEffect(newQuiz) {
        if (newQuiz!!) {
            questionVM.clearUserOptions()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(MaterialTheme.spacing.mediumPlus)
    ) {
        RowTimer(questionVM)
        MyVerticalSpacer(MaterialTheme.spacing.extraSmall)

        RowProgression(questionVM, index, numberOfQuestions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        RowQuestionSwitch(questionVM, index, numberOfQuestions)
        MyVerticalSpacer(MaterialTheme.spacing.large)

        RowQuestion(index, questions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        RadioButtonOptions(questionVM, index, questions)
        MyVerticalSpacer(MaterialTheme.spacing.large)

        ButtonNav(questionVM, index)
    }
}

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
fun RowProgression(questionVM: QuestionVM, index: Int, numberOfQuestions: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextNumberOfQuestion(index, numberOfQuestions)
        MyHorizontalSpacer(MaterialTheme.spacing.medium)
        MyProgressIndicator(index, numberOfQuestions, Modifier.weight(1.0f, true))
        MyHorizontalSpacer(MaterialTheme.spacing.medium)
        IconButtonPrevious(questionVM, index)
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
fun MyProgressIndicator(index: Int, numberOfQuestions: Int, modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        progress = (index + 1) / numberOfQuestions.toFloat()
    )
}

@Composable
fun IconButtonPrevious(questionVM: QuestionVM, index: Int) {
    Card(
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = MaterialTheme.spacing.default, color = MaterialTheme.colors.primary),
    ) {
        IconButton(
            onClick = { questionVM.onEvent(QuestionEvent.OnPrevious(index)) },
            modifier = Modifier.background(
                if(index != 0) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
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


@Composable
fun RowQuestionSwitch(questionVM: QuestionVM, index: Int, numberOfQuestions: Int) {

    var answers = questionVM.state.userAnswers
    var color: Color
    Row(modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..numberOfQuestions) {
            color =
                if (i - 1 == index) MaterialTheme.colors.primary
                else if (answers[i - 1] == null) MaterialTheme.colors.secondaryVariant
                else MaterialTheme.colors.primaryVariant

            MyHorizontalSpacer(MaterialTheme.spacing.small)
            Card(
                shape = MaterialTheme.shapes.large,
                border = BorderStroke(width = MaterialTheme.spacing.simple, color = MaterialTheme.colors.primary),
                backgroundColor = color
            ) {
                IconButton(
                    onClick = { questionVM.onEvent(QuestionEvent.OnJumpTo(index,i - 1)) },
                ) {
                    Text(
                        text = i.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
        MyHorizontalSpacer(MaterialTheme.spacing.small)
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
fun ButtonNav(questionVM: QuestionVM, index: Int) {
    if (index != Question.getList().size - 1) {
        MyButton(
            onclick = { questionVM.onEvent(QuestionEvent.OnNext(index)) },
            text = stringResource(id = R.string.question_next)
        )
    } else {
        MyButton(
            onclick = { questionVM.onEvent(QuestionEvent.OnFinish(index)) },
            text = stringResource(id = R.string.question_finish)
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

