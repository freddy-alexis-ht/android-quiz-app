package com.sunday.quiz1.data.source.provider

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question

interface IProvider {
    suspend fun getQuestions(): Answer<List<Question>>
    suspend fun getQuestion(index: Int): Answer<Question>
}