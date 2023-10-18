package com.sunday.quiz1.data.repository

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.data.source.file.IFile
import com.sunday.quiz1.data.source.provider.IProvider
import com.sunday.quiz1.domain.repository.IRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val provider: IProvider,
    private val file: IFile,
) : IRepository {

    override suspend fun getQuestionsFromProvider(): Answer<List<Question>> {
        return provider.getQuestionsFromProvider()
    }

    override suspend fun getQuestionsFromFile(): Answer<List<Question>> {
        return file.getQuestions()
    }

}