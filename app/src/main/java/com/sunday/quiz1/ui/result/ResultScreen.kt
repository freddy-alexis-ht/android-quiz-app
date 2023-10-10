package com.sunday.quiz1.ui.result

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.question.QuestionVM
import com.sunday.quiz1.R
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.ui.common.MyButton
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
    val userAnswers: MutableList<Boolean?> = questionVM.resultState.userAnswers
    val userOptions: MutableList<String> = questionVM.resultState.userOptions
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
        verticalArrangement = Arrangement.Top,
    ) {

        RowResult(text = stringResource(id = R.string.result_summary), resultVM)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        CardResults(result.totalQuestions,
            result.totalCorrect,
            result.totalIncorrect,
            result.totalNotAnswered)
        MyVerticalSpacer(MaterialTheme.spacing.mediumPlus)

        Divider(color = MaterialTheme.colors.secondaryVariant, thickness = 1.dp)
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        TextTitle(text = stringResource(id = R.string.result_detail))
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        LazyColumn {
            itemsIndexed(questions) { index, question ->
                CardDetails(index, question, userAnswers, userOptions)
                MyVerticalSpacer(MaterialTheme.spacing.small)
            }
        }
    }
}

@Composable
fun RowResult(text: String, resultVM: ResultVM) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f)) {
            TextTitle(text)
        }
        Row {
            MyButton(
                onclick = { resultVM.onEvent(ResultEvent.OnHome) },
                text = stringResource(id = R.string.result_home),
                modifier = Modifier.fillMaxWidth(0.2f)
            )
        }
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
fun CardDetails(
    index: Int,
    question: Question,
    userAnswers: MutableList<Boolean?>,
    userOptions: MutableList<String>,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            contentAlignment = Alignment.CenterStart) {
            Column() {
                DetailQuestion(index, question.question, userAnswers)
                DetailOptions(question.options, question.result, userOptions[index])
            }
        }
    }
}

@Composable
fun DetailQuestion(index: Int, question: String, userAnswers: MutableList<Boolean?>) {
    Row {
        Text(text = stringResource(id = R.string.result_detail_question_number, index + 1))
        MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
        Text(text = question, modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id =
            if (userAnswers[index] == true) R.string.result_detail_correct_emoji
            else if (userAnswers[index] == false) R.string.result_detail_incorrect_emoji
            else R.string.result_detail_not_answered_emoji
            )
        )
    }
}

@Composable
fun DetailOptions(options: List<String>, result: String, userOption: String) {
    options.forEach {
        Row() {
            if (it == result) {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.mediumPlus),
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.2f)) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircleOutline,
                            contentDescription = stringResource(id = R.string.result_detail_correct),
                            tint = MaterialTheme.colors.surface
                        )
                        MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
                        Text(text = it, modifier = Modifier.weight(1f), style = MaterialTheme.typography.body2)
                    }
                }
            }
            else if (it == userOption) {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.mediumPlus),
                    backgroundColor = MaterialTheme.colors.error.copy(alpha = 0.2f)) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.Filled.HighlightOff,
                            contentDescription = stringResource(id = R.string.result_detail_incorrect),
                            tint = MaterialTheme.colors.error
                        )
                        MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
                        Text(text = it, modifier = Modifier.weight(1f), style = MaterialTheme.typography.body2)
                    }
                }
            }
            else {
                Icon(imageVector = Icons.Filled.RadioButtonUnchecked,
                    contentDescription = stringResource(id = R.string.result_detail_not_answered)
                )
                MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
                Text(text = it, modifier = Modifier.weight(1f), style = MaterialTheme.typography.body2)
            }
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
