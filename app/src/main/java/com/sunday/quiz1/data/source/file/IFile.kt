package com.sunday.quiz1.data.source.file

import com.sunday.quiz1.data.Answer
import com.sunday.quiz1.data.model.Question

interface IFile {
    suspend fun getQuestions(): Answer<List<Question>>
}

