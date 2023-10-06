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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.question.QuestionVM

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
        Text(
            text = "Resumen".uppercase(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = "Total de preguntas: ${questionVM.resultState.totalQuestions}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${questionVM.resultState.totalCorrect} .. preguntas correctas: ${questionVM.resultState.correctQuestions}",
            fontSize = 16.sp
        )
        Text(
            text = "${questionVM.resultState.totalIncorrect} .. preguntas incorrectas: ${questionVM.resultState.incorrectQuestions}",
            fontSize = 16.sp
        )
        Text(
            text = "${questionVM.resultState.totalNotAnswered} .. preguntas no contestadas: ${questionVM.resultState.notAnsweredQuestions}",
            fontSize = 16.sp
        )
        Button(onClick = { resultVM.onEvent(ResultEvent.OnHome) }) {
            Text(text = "Home".uppercase())
        }
    }
}