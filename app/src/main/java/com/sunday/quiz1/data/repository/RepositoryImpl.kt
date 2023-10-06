package com.sunday.quiz1.data.repository

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.QuestionModel
import com.sunday.quiz1.data.source.provider.IProvider
import com.sunday.quiz1.domain.repository.IRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val provider: IProvider
) : IRepository {

    override suspend fun getQuestions(): Answer<List<QuestionModel>> {
        return provider.getQuestions()
    }

    override suspend fun getQuestion(index: Int): Answer<QuestionModel> {
        return provider.getQuestion(index)
    }
}