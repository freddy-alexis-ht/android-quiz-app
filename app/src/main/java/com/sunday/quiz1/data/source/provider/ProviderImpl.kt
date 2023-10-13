package com.sunday.quiz1.data.source.provider

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question
import javax.inject.Inject

class ProviderImpl @Inject constructor(): IProvider {
    override suspend fun getQuestions(): Answer<List<Question>> {
        val questions: List<Question> = Question.getList()
        return Answer.Success(data = questions)
    }
}