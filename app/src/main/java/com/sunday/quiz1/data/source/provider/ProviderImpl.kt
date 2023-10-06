package com.sunday.quiz1.data.source.provider

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.QuestionModel
import javax.inject.Inject

class ProviderImpl @Inject constructor(): IProvider {
    override suspend fun getQuestions(): Answer<List<QuestionModel>> {
        val questions: List<QuestionModel> = QuestionModel.getList()
        return Answer.Success(data = questions)
    }

    override suspend fun getQuestion(index: Int): Answer<QuestionModel> {
        val question: QuestionModel = QuestionModel.getOne(index)
        return Answer.Success(data = question)
    }
}