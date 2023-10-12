package com.sunday.quiz1.ui.result

data class ResultState(
    var isDetailVisible: Boolean = false,
    var isCorrectVisible: Boolean = false,
    var isIncorrectVisible: Boolean = false,
    var isNotAnsweredVisible: Boolean = false,
    var totalQuestions: Int = size,
    var totalCorrect: Int = userAnswers.count { it == true },
    var totalIncorrect: Int = userAnswers.count { it == false },
    var totalNotAnswered: Int = userAnswers.count { it == null },
) {
    companion object {
        var timer: String = ""
        var size: Int = 0
        var userOptions: MutableList<String> = MutableList(size) { "" }
        var userAnswers: MutableList<Boolean?> = MutableList(size) { null }
    }
}
