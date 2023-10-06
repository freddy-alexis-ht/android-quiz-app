package com.sunday.quiz1.domain.repository

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.QuestionModel

interface IRepository {
    suspend fun getQuestions(): Answer<List<QuestionModel>>
    suspend fun getQuestion(index: Int): Answer<QuestionModel>
}