package com.sunday.quiz1.ui.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.question.QuestionVM
import com.sunday.quiz1.R
import com.sunday.quiz1.ui.common.MyVerticalSpacer
import com.sunday.quiz1.ui.theme.spacing

@Composable
fun ResultScreen(
    onNavigate: (AppEvent.Navigate) -> Unit,
    resultVM: ResultVM,
    questionVM: QuestionVM
) {
    val result = questionVM.resultState

    LaunchedEffect(key1 = true) {
        resultVM.appEvent.collect { event ->
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextSummary()
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        TextTotalOfQuestions(result.totalQuestions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        RowCorrect(result.totalCorrect, result.correctQuestions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        TextCorrectAnswers(result.totalCorrect, result.correctQuestions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        TextIncorrectAnswers(result.totalIncorrect, result.incorrectQuestions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        TextNotAnswered(result.totalNotAnswered, result.notAnsweredQuestions)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        ButtonHome(onHome = { resultVM.onEvent(ResultEvent.OnHome) })
    }
}

@Composable
fun TextSummary() {
    Text(
        text = stringResource(id = R.string.result_summary),
        style = MaterialTheme.typography.h6,
    )
}

@Composable
fun TextTotalOfQuestions(totalOfQuestions: Int) {
    Text(
        text = stringResource(
            id = R.string.result_total_of_questions,
            totalOfQuestions
        ),
        style = MaterialTheme.typography.body1,
    )
}

@Composable
fun RowCorrect(totalCorrect: Int, correctQuestions: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = MaterialTheme.shapes.large
    ) {
        Row() {
            Text(
                text = stringResource(
                    id = R.string.result_correct_answers,
                    totalCorrect,
                    correctQuestions
                ),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.surface
            )
        }
        Row() {
            Text(text = "😊")
        }
//        Icon(imageVector = Icons.Default.Ha, contentDescription = null)
    }
}


@Composable
fun TextCorrectAnswers(totalCorrect: Int, correctQuestions: String) {
    Text(
        text = stringResource(
            id = R.string.result_correct_answers,
            totalCorrect,
            correctQuestions
        ),
        style = MaterialTheme.typography.body2,
    )
}

@Composable
fun TextIncorrectAnswers(totalIncorrect: Int, incorrectQuestions: String) {
    Text(
        text = stringResource(
            id = R.string.result_incorrect_answers,
            totalIncorrect,
            incorrectQuestions
        ),
        fontSize = 16.sp
    )
}

@Composable
fun TextNotAnswered(totalNotAnswered: Int, notAnsweredQuestions: String) {
    Text(
        text = stringResource(
            id = R.string.result_not_answered,
            totalNotAnswered,
            notAnsweredQuestions
        ),
        fontSize = 16.sp
    )
}

@Composable
fun ButtonHome(onHome: () -> Unit) {
    Button(onClick = onHome) {
        Text(text = stringResource(id = R.string.result_home))
    }
}