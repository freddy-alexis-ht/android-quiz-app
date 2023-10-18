package com.sunday.quiz1.data.source.provider

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question

interface IProvider {
    suspend fun getQuestionsFromProvider(): Answer<List<Question>>
}