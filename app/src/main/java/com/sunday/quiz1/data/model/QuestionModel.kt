package com.sunday.quiz1.data.model

class QuestionModel(
    val number: Int,
    val question: String,
    val options: List<String>,
    val results: List<Boolean>,
) {
    companion object {
        fun getList(): List<QuestionModel> {
            return listOf(
                QuestionModel(1, "¿Cuánto es 2 + 2?", listOf("3", "4", "5"), listOf(false, true, false)),
                QuestionModel(2,
                    "¿La sangre es de color ___?",
                    listOf("gris", "azul", "rojo"),
                    listOf(false, false, true)),
                QuestionModel(3,
                    "¿Arriba es opuesto de ___?",
                    listOf("abajo", "izquierda", "derecha"),
                    listOf(true, false, false))
            )
        }

        fun getOne(index: Int): QuestionModel {
            return getList()[index]
        }

        fun getSize(): Int {
            return getList().size
        }
    }
}