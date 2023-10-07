package com.sunday.quiz1.domain.repository

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question

interface IRepository {
    suspend fun getQuestions(): Answer<List<Question>>
    suspend fun getQuestion(index: Int): Answer<Question>
}