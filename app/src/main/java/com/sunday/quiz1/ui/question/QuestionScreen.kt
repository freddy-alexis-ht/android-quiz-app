package com.sunday.quiz1.ui.question

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.R

@Composable
fun QuestionScreen(
    onNavigate: (AppEvent.Navigate) -> Unit,
    questionVM: QuestionVM,
    newQuiz: Boolean?
) {
    var index = questionVM.state.index

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
        TextNumberOfQuestion(index)
        MyProgressIndicator(index)
        TextTitle()
        TextQuestion(index)
        RadioButtonGroup(questionVM, index)
        ButtonGroup(questionVM, index)
    }
}

@Composable
fun TextNumberOfQuestion(index: Int) {
    Text(text = stringResource(id = R.string.question_number,
    index+1, Question.getSize()))
}

@Composable
fun MyProgressIndicator(index: Int) {
    LinearProgressIndicator(
        progress = (index + 1) / Question.getSize().toFloat()
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
fun TextQuestion(index: Int) {
    Text(
        text = Question.getOne(index).question.uppercase(),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    )

}

@Composable
fun RadioButtonGroup(questionVM: QuestionVM, index: Int) {

    val options: List<String> = Question.getOne(index).options

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
        if (index != 0) {
            Button(onClick = { questionVM.onEvent(QuestionEvent.OnPrevious(index)) }) {
                Text(text = "Atrás".uppercase())
            }
        }
        if (index != Question.getList().size - 1) {
            Button(onClick = { questionVM.onEvent(QuestionEvent.OnNext(index)) }) {
                Text(text = "Siguiente".uppercase())
            }
        }
    }
    Row(horizontalArrangement = Arrangement.Center) {
        if (index == Question.getList().size - 1) {
            Button(onClick = { questionVM.onEvent(QuestionEvent.OnFinish(index)) }) {
                Text(text = "Finalizar".uppercase())
            }
        }
        Button(onClick = { questionVM.onEvent((QuestionEvent.OnHome)) }) {
            Text(text = "Home".uppercase())
        }
    }
}

