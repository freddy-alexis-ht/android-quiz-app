package com.sunday.quiz1.ui.result

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.question.QuestionVM
import com.sunday.quiz1.R
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.ui.common.MyHorizontalSpacer
import com.sunday.quiz1.ui.common.MyVerticalSpacer
import com.sunday.quiz1.ui.theme.spacing

@Composable
fun ResultScreen(
    onNavigate: (AppEvent.Navigate) -> Unit,
    resultVM: ResultVM,
    questionVM: QuestionVM,
) {
    val result = questionVM.resultState
    val questions = questionVM.questions

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

        TextTitle(text = stringResource(id = R.string.result_summary))
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        CardResults(result.totalQuestions,
            result.totalCorrect,
            result.totalIncorrect,
            result.totalNotAnswered)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        ButtonHome(onHome = { resultVM.onEvent(ResultEvent.OnHome) })
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        Divider(color = MaterialTheme.colors.secondaryVariant, thickness = 1.dp)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        TextTitle(text = stringResource(id = R.string.result_detail))
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        LazyColumn {
            itemsIndexed(questions) {
                index, question ->
                CardDetails(index, question)
            }
        }
        
//        CardDetails()
//        TextCorrectAnswers(result.totalCorrect, result.correctQuestions)
//        TextIncorrectAnswers(result.totalIncorrect, result.incorrectQuestions)
//        TextNotAnswered(result.totalNotAnswered, result.notAnsweredQuestions)

    }
}

@Composable
fun TextTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.secondaryVariant
    )
}

@Composable
fun CardResults(
    totalOfQuestions: Int,
    totalCorrect: Int,
    totalIncorrect: Int,
    totalNotAnswered: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextTotalOfQuestions(totalOfQuestions)
                }
                RowResult(
                    text = stringResource(id = R.string.result_correct_answers),
                    total = totalCorrect,
                    emoji = stringResource(id = R.string.result_correct_emoji)
                )
                RowResult(
                    text = stringResource(id = R.string.result_incorrect_answers),
                    total = totalIncorrect,
                    emoji = stringResource(id = R.string.result_incorrect_emoji)
                )
                RowResult(
                    text = stringResource(id = R.string.result_not_answered),
                    total = totalNotAnswered,
                    emoji = stringResource(id = R.string.result_not_answered_emoji)
                )
            }
        }
    }
}

@Composable
fun CardDetails(index: Int, question: Question) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Column() {
                DetailQuestion(index, question.question)
                DetailOptions(question.options)
            }
        }
    }
}

@Composable
fun DetailQuestion(index: Int, question: String) {
    Row {
        Text(text = index.toString())
        Text(text = question, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Filled.Check, contentDescription = "Correct")
    }
}

@Composable
fun DetailOptions(options: List<String>) {
    options.forEach {
        Row() {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "Correct")
            Text(text = it, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun TextTotalOfQuestions(totalOfQuestions: Int) {
    Text(
        text = stringResource(
            id = R.string.result_total_of_questions, totalOfQuestions
        ),
        style = MaterialTheme.typography.body1,
    )
}

@Composable
fun RowResult(text: String, total: Int, emoji: String) {
    Row() {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body2
        )
        Text(text = "$total",
            style = MaterialTheme.typography.body2)
        MyHorizontalSpacer(MaterialTheme.spacing.medium)
        Text(text = emoji)
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