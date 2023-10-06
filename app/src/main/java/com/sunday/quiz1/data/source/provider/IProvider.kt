package com.sunday.quiz1.data.source.provider

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.QuestionModel

interface IProvider {
    suspend fun getQuestions(): Answer<List<QuestionModel>>
    suspend fun getQuestion(index: Int): Answer<QuestionModel>
}