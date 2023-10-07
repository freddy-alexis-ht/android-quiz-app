package com.sunday.quiz1.ui.question

import com.sunday.quiz1.data.model.Question

data class QuestionState(
    var index: Int = 0,
    var optionSelected: String = "",
    var userOptions: MutableList<String> = MutableList(Question.getSize()) { "" },
    var userAnswers: MutableList<Boolean?> = MutableList(Question.getSize()) { null },
)
