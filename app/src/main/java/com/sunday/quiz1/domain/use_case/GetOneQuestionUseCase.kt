package com.sunday.quiz1.domain.use_case

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.domain.repository.IRepository
import javax.inject.Inject

class GetOneQuestionUseCase @Inject constructor(
    private val repository: IRepository
) {

    suspend operator fun invoke(index: Int): Answer<Question> {
        return repository.getQuestion(index)
    }

}