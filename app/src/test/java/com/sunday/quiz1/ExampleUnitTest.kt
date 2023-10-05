package com.sunday.quiz1

import com.sunday.quiz1.ui.question.Question
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val list = MutableList(Question.getList().size) { null }
        println(list) // [A, B, C]

        list.clear()
        println(list) // []
    }
}