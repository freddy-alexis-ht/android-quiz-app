package com.sunday.quiz1.ui.result

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
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
    val result = resultVM.resultState
    val isDetailVisible = resultVM.resultState.isDetailVisible

    val userAnswers: MutableList<Boolean?> = ResultState.userAnswers
    val userOptions: MutableList<String> = ResultState.userOptions
    val questions = questionVM.questions

    LaunchedEffect(key1 = true) {
        resultVM.appEvent.collect { event ->
            when (event) {
                is AppEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    LaunchedEffect(key1 = true) {
        resultVM.updateResultState(ResultState())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.mediumPlus),
        verticalArrangement = Arrangement.Top,
    ) {

        RowResults(
            text = stringResource(id = R.string.result_summary),
            onHome = { resultVM.onEvent(ResultEvent.OnHome) }
        )
        MyVerticalSpacer(MaterialTheme.spacing.medium)

        CardResults(result.totalQuestions,
            result.totalCorrect,
            result.totalIncorrect,
            result.totalNotAnswered)
        MyVerticalSpacer(MaterialTheme.spacing.mediumPlus)

        Divider(color = MaterialTheme.colors.secondaryVariant, thickness = MaterialTheme.spacing.simple)
        MyVerticalSpacer(MaterialTheme.spacing.small)

        RowDetails(text = stringResource(id = R.string.result_detail), isDetailVisible, resultVM)
        MyVerticalSpacer(MaterialTheme.spacing.small)

        LazyColumn {
            itemsIndexed(questions) { index, question ->
                val isCardVisible =
                    if (userAnswers[index] == true) resultVM.resultState.isCorrectVisible
                    else if (userAnswers[index] == false) resultVM.resultState.isIncorrectVisible
                    else resultVM.resultState.isNotAnsweredVisible

                if (isCardVisible) CardDetails(index,
                    question,
                    userAnswers[index],
                    userOptions[index])
            }
        }
    }
}

@Composable
fun RowResults(text: String, onHome: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f)) {
            TextTitle(text)
        }
        Row {
            Button(onClick = { onHome() }, shape = MaterialTheme.shapes.medium,
            ) {
                Text(text = stringResource(id = R.string.result_home),
                )
            }
        }
    }
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
            .height(IntrinsicSize.Min)
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
                MyVerticalSpacer(MaterialTheme.spacing.extraSmall)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    TextTotalOfQuestions(totalOfQuestions)
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = "Tiempo: ${ResultState.timer}")
                }
                RowResults(
                    text = stringResource(id = R.string.result_correct_answers),
                    total = totalCorrect,
                    emoji = stringResource(id = R.string.result_correct_emojis)
                )
                RowResults(
                    text = stringResource(id = R.string.result_incorrect_answers),
                    total = totalIncorrect,
                    emoji = stringResource(id = R.string.result_incorrect_emojis)
                )
                RowResults(
                    text = stringResource(id = R.string.result_not_answered_questions),
                    total = totalNotAnswered,
                    emoji = stringResource(id = R.string.result_not_answered_emojis)
                )
                MyVerticalSpacer(MaterialTheme.spacing.extraSmall)
            }
        }
    }
}

@Composable
fun RowDetails(text: String, isDetailVisible: Boolean, resultVM: ResultVM) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            TextTitle(text)
            TextButton(onClick = { resultVM.onEvent(ResultEvent.OnViewHide) }) {
                Text(
                    text = stringResource(
                        id = if (isDetailVisible) R.string.result_hide else R.string.result_view
                    ),
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            ButtonFilter(true,
                { resultVM.onEvent(ResultEvent.OnClickFilter(true)) },
                isDetailVisible,
                resultVM.resultState.isCorrectVisible,
                stringResource(R.string.result_correct_emoji))
            ButtonFilter(false,
                { resultVM.onEvent(ResultEvent.OnClickFilter(false)) },
                isDetailVisible,
                resultVM.resultState.isIncorrectVisible,
                stringResource(R.string.result_incorrect_emoji))
            ButtonFilter(null,
                { resultVM.onEvent(ResultEvent.OnClickFilter(null)) },
                isDetailVisible,
                resultVM.resultState.isNotAnsweredVisible,
                stringResource(R.string.result_not_answered_emoji))
        }
    }
}

@Composable
fun ButtonFilter(
    answerType: Boolean?,
    onClick: (Boolean?) -> Unit,
    isVisible: Boolean,
    isFiltered: Boolean,
    emoji: String,
) {
    Button(
        onClick = { onClick(answerType) },
        modifier = Modifier.size(MaterialTheme.spacing.large),
        contentPadding = PaddingValues(MaterialTheme.spacing.default),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = ButtonDefaults.elevation(MaterialTheme.spacing.default),
        enabled = isVisible
    ) {
        if (!isVisible) Text(text = emoji)
        else
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = emoji)
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (!isFiltered) MaterialTheme.colors.secondaryVariant.copy(alpha = 0.5f)
                        else Color.Transparent
                    )
                )
            }
    }
}

@Composable
fun CardDetails(
    index: Int,
    question: Question,
    userAnswer: Boolean?,
    userOption: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            contentAlignment = Alignment.CenterStart) {
            Column() {
                MyVerticalSpacer(MaterialTheme.spacing.extraSmall)
                DetailQuestion(index, question.question, userAnswer)
                DetailOptions(question.options, question.result, userOption)
                MyVerticalSpacer(MaterialTheme.spacing.extraSmall)
            }
        }
    }
    MyVerticalSpacer(MaterialTheme.spacing.small)
}

/**/
@Composable
fun TextTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.secondaryVariant
    )
}

@Composable
fun DetailQuestion(index: Int, question: String, userAnswer: Boolean?) {
    Row {
        Text(text = stringResource(id = R.string.result_question_number, index + 1))
        MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
        Text(text = question, modifier = Modifier.weight(1f))
        Text(text = stringResource(id =
            when (userAnswer) {
                true -> R.string.result_correct_emoji
                false -> R.string.result_incorrect_emoji
                else -> R.string.result_not_answered_emoji
            }
        ))
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
                            contentDescription = stringResource(id = R.string.result_correct),
                            tint = MaterialTheme.colors.surface
                        )
                        MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
                        TextOption(it)
                    }
                }
            } else if (it == userOption) {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.mediumPlus),
                    backgroundColor = MaterialTheme.colors.error.copy(alpha = 0.2f)) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.Filled.HighlightOff,
                            contentDescription = stringResource(id = R.string.result_incorrect),
                            tint = MaterialTheme.colors.error
                        )
                        MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
                        TextOption(it)
                    }
                }
            } else {
                Icon(imageVector = Icons.Filled.RadioButtonUnchecked,
                    contentDescription = stringResource(id = R.string.result_not_answered)
                )
                MyHorizontalSpacer(MaterialTheme.spacing.extraSmall)
                TextOption(it)
            }
        }
    }
}

@Composable
fun TextOption(option: String) {
    Text(text = option, style = MaterialTheme.typography.body2)
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
fun RowResults(text: String, total: Int, emoji: String) {
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
