package com.sunday.quiz1.ui.question

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunday.quiz1.R
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.ui.common.*
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
        modifier = Modifier.padding(20.dp)
    ) {
        RowTimer()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextNumberOfQuestion(index, numberOfQuestions)
            MyHorizontalSpacer12Dp()
            MyProgressIndicator(index, numberOfQuestions, Modifier.weight(1.0f, true))
            MyHorizontalSpacer12Dp()
            IconButtonPrevious(questionVM, index)
        }

        MyVerticalSpacer16Dp()
        QuestionSwitch(questionVM, index, numberOfQuestions)

        MyVerticalSpacer16Dp()
        TextQuestion(index, questions)
        MyVerticalSpacer16Dp()
        RadioButtonGroup(questionVM, index, questions)
        MyVerticalSpacer16Dp()
        ButtonGroup(questionVM, index)
    }
}

@Composable
fun RowTimer() {
    var ticks by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1.seconds)
            ticks++
        }
    }
    var seconds = "%02d".format(ticks % 60)
    var minutes = "%02d".format((ticks / 60) % 60)
    var hours = "%02d".format((ticks / 3600) % 60)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "${hours}:$minutes:$seconds")

    }
}

@Composable
fun TextNumberOfQuestion(index: Int, numberOfQuestions: Int) {
    Text(text = stringResource(id = R.string.question_number,
        index + 1, numberOfQuestions))
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
        shape = RoundedCornerShape(24.dp)
    ) {
        IconButton(
            onClick = { questionVM.onEvent(QuestionEvent.OnPrevious(index)) },
            modifier = Modifier.background(MaterialTheme.colors.secondary),
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
fun QuestionSwitch(questionVM: QuestionVM, index: Int, numberOfQuestions: Int) {

    var border: Int
    var answers = questionVM.state.userAnswers
    var color: Color
    Row(modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..numberOfQuestions) {

            border = if (i - 1 == index) 2 else 0
            color = if(answers[i-1] == null) Color.LightGray else Color.Blue

            MyHorizontalSpacer8Dp()
            Card(
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(width = border.dp, color = Color.Red)
            ) {
                IconButton(
                    onClick = { questionVM.onEvent(QuestionEvent.OnJumpTo(i - 1)) },
                    modifier = Modifier.background(color)
                ) {
                    Text(text = i.toString())
                }
            }
        }
        MyHorizontalSpacer8Dp()
    }
}

@Composable
fun TextQuestion(index: Int, questions: List<Question>) {
    Card(
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = questions[index].question.uppercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun RadioButtonGroup(questionVM: QuestionVM, index: Int, questions: List<Question>) {

    val options: List<String> = questions[index].options

    options.forEach {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { questionVM.onEvent(QuestionEvent.OnRbChange(it, index)) }
        ) {
            RadioButton(
                selected = questionVM.userOptions[index] == it,
                onClick = { questionVM.onEvent(QuestionEvent.OnRbChange(it, index)) }
            )
            Text(text = it)
        }
    }
}

@Composable
fun ButtonGroup(questionVM: QuestionVM, index: Int) {
    if (index != Question.getList().size - 1) {
        MyButton(
            onclick = { questionVM.onEvent(QuestionEvent.OnNext(index)) },
            text = stringResource(id = R.string.question_next).uppercase()
        )
    } else {
        MyButton(
            onclick = { questionVM.onEvent(QuestionEvent.OnFinish(index)) },
            text = stringResource(id = R.string.question_finish).uppercase()
        )
    }
    MyVerticalSpacer16Dp()
    MyButton(
        onclick = { questionVM.onEvent((QuestionEvent.OnHome)) },
        text = stringResource(id = R.string.question_home).uppercase(),
        colors = MaterialTheme.colors.surface
    )
}

