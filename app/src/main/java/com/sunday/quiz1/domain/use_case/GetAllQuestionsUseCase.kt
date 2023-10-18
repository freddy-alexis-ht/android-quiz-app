package com.sunday.quiz1.domain.use_case

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.domain.repository.IRepository
import javax.inject.Inject

class GetAllQuestionsUseCase @Inject constructor(
    private val repository: IRepository
) {

    suspend operator fun invoke(): Answer<List<Question>> {
        return repository.getQuestionsFromFile()
//        return repository.getQuestionsFromProvider()
    }

}