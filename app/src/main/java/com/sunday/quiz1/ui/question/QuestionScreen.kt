package com.sunday.quiz1.ui.question

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.R
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.ui.common.MyButton
import com.sunday.quiz1.ui.common.MyHorizontalSpacer12Dp
import com.sunday.quiz1.ui.common.MyVerticalSpacer16Dp

@Composable
fun QuestionScreen(
    onNavigate: (AppEvent.Navigate) -> Unit,
    questionVM: QuestionVM,
    newQuiz: Boolean?
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
        if(newQuiz!!) {
            questionVM.clearUserOptions()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextNumberOfQuestion2(index, numberOfQuestions)
            MyHorizontalSpacer12Dp()
            MyProgressIndicator2(index, numberOfQuestions, Modifier.weight(1.0f, true))
            MyHorizontalSpacer12Dp()
//            if (index != 0) {
            Card(
                shape = RoundedCornerShape(24.dp)
            ){
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
//            }
        }
//        TextNumberOfQuestion(index, numberOfQuestions)
//        MyProgressIndicator(index, numberOfQuestions)
//        TextTitle()
        MyVerticalSpacer16Dp()
        TextQuestion(index, questions)
        MyVerticalSpacer16Dp()
        RadioButtonGroup(questionVM, index, questions)
        MyVerticalSpacer16Dp()
        ButtonGroup(questionVM, index)
    }
}

@Composable
fun TextNumberOfQuestion(index: Int, numberOfQuestions: Int) {
    Text(text = stringResource(id = R.string.question_number,
    index+1, numberOfQuestions))
}
@Composable
fun TextNumberOfQuestion2(index: Int, numberOfQuestions: Int) {
    Text(text = stringResource(id = R.string.question_number_v2,
    index+1, numberOfQuestions))
}

@Composable
fun MyProgressIndicator(index: Int, numberOfQuestions: Int) {
    LinearProgressIndicator(
        progress = (index + 1) / numberOfQuestions.toFloat()
    )
}
@Composable
fun MyProgressIndicator2(index: Int, numberOfQuestions: Int, modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        progress = (index + 1) / numberOfQuestions.toFloat()
    )
}

@Composable
fun TextTitle() {
    Text(
        text = "Pregunta".uppercase(),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(20.dp)
    )
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
    Row(horizontalArrangement = Arrangement.Center) {
//        if (index != 0) {
//            Button(onClick = { questionVM.onEvent(QuestionEvent.OnPrevious(index)) }) {
//                Text(text = stringResource(id = R.string.question_previous).uppercase())
//            }
//        }
        if (index != Question.getList().size - 1) {
            MyButton(onclick = { questionVM.onEvent(QuestionEvent.OnNext(index)) }, text = stringResource(id = R.string.question_next).uppercase())
//            Button(onClick = { questionVM.onEvent(QuestionEvent.OnNext(index)) }) {
//                Text(text = stringResource(id = R.string.question_next).uppercase())
//            }
        }
    }
    MyVerticalSpacer16Dp()
    Row(horizontalArrangement = Arrangement.Center) {
        if (index == Question.getList().size - 1) {
            MyButton(onclick = { questionVM.onEvent(QuestionEvent.OnFinish(index)) }, text = stringResource(id = R.string.question_finish).uppercase())
//            Button(onClick = { questionVM.onEvent(QuestionEvent.OnFinish(index)) }) {
//                Text(text = stringResource(id = R.string.question_finish).uppercase())
//            }
        }
        MyButton(
            onclick = { questionVM.onEvent((QuestionEvent.OnHome)) },
            text = stringResource(id = R.string.question_home).uppercase(),
            colors = MaterialTheme.colors.surface
        )
//        Button(onClick = { questionVM.onEvent((QuestionEvent.OnHome)) }) {
//            Text(text = stringResource(id = R.string.question_home).uppercase())
//        }
    }
}

