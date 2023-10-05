package com.sunday.quiz1.ui.question

class Question(
    val number: Int,
    val question: String,
    val options: List<String>,
    val results: List<Boolean>
) {
    companion object {
        fun getList(): List<Question> {
            return listOf(
                Question(1, "¿Cuánto es 2 + 2?", listOf("3", "4", "5"), listOf(false, true, false) ),
                Question(2, "¿La sangre es de color ___?", listOf("gris", "azul", "rojo"), listOf(false, false, true) ),
                Question(3, "¿Arriba es opuesto de ___?", listOf("abajo", "izquierda", "derecha"), listOf(true, false, false) )
            )
        }
        fun getOne(index: Int): Question {
            return getList()[index]
        }
        fun getSize(): Int {
            return getList().size
        }
    }
}