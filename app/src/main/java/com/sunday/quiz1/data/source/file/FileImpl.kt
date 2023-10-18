package com.sunday.quiz1.data.source.file

import android.content.Context
import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question
import javax.inject.Inject

class FileImpl @Inject constructor(
    private val context: Context
) : IFile {

    override suspend fun getQuestions(): Answer<List<Question>> {
//        gets all text
//        val use: String = context.assets.open("39-preguntas.txt").bufferedReader()
//            .use { it.readText() }
        val list: List<String> = context.assets.open("39-preguntas.txt")
            .bufferedReader()
            .useLines { it.toList() }

        var questions: List<Question> = listOf()
        var question = ""
        var options: List<String> = listOf()
        var result = ""

        list.forEach {
            if(it.contains(" . ")) {
                question = it.substring(it.indexOf(" . ")+3)
            } else if(it.contains("» ")) {
                options = options + it.substring(2)
            } else if(it.contains("RESPUESTA: ")){
                result = it.substring(11)
                questions = questions + Question(1, question, options, result)
                question = ""
                options = emptyList()
                result = ""
            }
        }

        return Answer.Success(data = questions)
    }
}
