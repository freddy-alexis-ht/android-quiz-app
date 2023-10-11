package com.sunday.quiz1.ui.result

data class ResultState(
    var totalQuestions: Int = size,
    var totalCorrect: Int = userAnswers.count { it == true },
    var totalIncorrect: Int = userAnswers.count { it == false },
    var totalNotAnswered: Int = userAnswers.count { it == null },
) {
    companion object {
        var size: Int = 0
        var userOptions: MutableList<String> = MutableList(size) { "" }
        var userAnswers: MutableList<Boolean?> = MutableList(size) { null }
    }
}
