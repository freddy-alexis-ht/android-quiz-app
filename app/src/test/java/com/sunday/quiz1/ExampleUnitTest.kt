package com.sunday.quiz1

import com.sunday.quiz1.data.model.Question
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(formatTimer(121))
        println(formatTimer(12110))
        println(formatTimer(77))
    }
    fun formatTimer(ticks: Int) : String {
        val seconds = "%02d".format(ticks % 60)
        val minutes = "%02d".format((ticks / 60) % 60)
        val hours = "%02d".format((ticks / 3600) % 60)
        return if(hours=="00") "$minutes:$seconds" else "${hours}:$minutes:$seconds"
    }
}