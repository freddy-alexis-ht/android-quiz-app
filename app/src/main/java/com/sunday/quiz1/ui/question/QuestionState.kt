package com.sunday.quiz1.ui.question

data class QuestionState(
    var size: Int = 0,
    var index: Int = 0,
    var optionSelected: String = "",
    var userOptions: MutableList<String> = MutableList(size) { "" },
    var userAnswers: MutableList<Boolean?> = MutableList(size) { null },
)
