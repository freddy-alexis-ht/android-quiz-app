package com.sunday.quiz1.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import com.sunday.quiz1.ui.question.QuestionVM
import com.sunday.quiz1.R
import com.sunday.quiz1.ui.common.MyVerticalSpacer16Dp

/*
* Habiendo presionado 'Finalizar' en la última pregunta, se recibe List<Boolean>
*   P.e. {true, true, false}
*   Total de preguntas = tamaño de la lista
*   Total de preguntas correctas = conteo de los 'true'
*   Total de preguntas incorrectas = conteo de los 'false' (o resta de lo anterior)
*   Preguntas correctas = recorrer y mostrar índice+1 de los 'true'
*   Preguntas incorrectas = recorrer y mostrar índice+1 de los 'false'
* Al presionar 'Home', ya que isNew=false muestra: botones: Iniciar, Salir
* */

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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextSummary()
        TextTotalOfQuestions(result.totalQuestions)
        MyVerticalSpacer16Dp()
        TextCorrectAnswers(result.totalCorrect, result.correctQuestions)
        MyVerticalSpacer16Dp()
        TextIncorrectAnswers(result.totalIncorrect, result.incorrectQuestions)
        MyVerticalSpacer16Dp()
        TextNotAnswered(result.totalNotAnswered, result.notAnsweredQuestions)
        MyVerticalSpacer16Dp()
        ButtonHome(onHome = { resultVM.onEvent(ResultEvent.OnHome) })
//        Button(onClick = { resultVM.onEvent(ResultEvent.OnHome) }) {
//            Text(text = "Home".uppercase())
//        }
    }
}

@Composable
fun TextSummary() {
    Text(
        text = stringResource(id = R.string.result_summary).uppercase(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 40.dp)
    )
}

@Composable
fun TextTotalOfQuestions(totalOfQuestions: Int) {
    Text(
        text = stringResource(
            id = R.string.result_total_of_questions,
            totalOfQuestions
        ),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextCorrectAnswers(totalCorrect: Int, correctQuestions: String) {
    Text(
        text = stringResource(
            id = R.string.result_correct_answers,
            totalCorrect,
            correctQuestions
        ),
        fontSize = 16.sp
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
        Text(text = stringResource(id = R.string.result_home).uppercase())
    }
}