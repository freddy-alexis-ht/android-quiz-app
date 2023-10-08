package com.sunday.quiz1.data.model

class Question(
    val number: Int,
    val question: String,
    val options: List<String>,
    val result: String
) {
    companion object {
        fun getList(): List<Question> {
            return listOf(
                Question(1, "¿Cuánto es 2 + 2?", listOf("3", "4", "5"), "4" ),
                Question(2, "La sangre es de color: ___", listOf("gris", "azul", "rojo"), "rojo" ),
                Question(3, "Arriba es opuesto de: ___", listOf("abajo", "izquierda", "derecha"), "abajo" ),
                Question(4, "Domingo es de color: ___", listOf("blanco", "negro", "marrón"), "negro" ),
                Question(5, "Espacio = Velocidad x ___?", listOf("masa", "distancia", "tiempo"), "tiempo" ),
                Question(6, "Azul y Amarillo hacen: ___?", listOf("verde", "celeste", "rojo"), "verde" ),
                Question(7, "Factorial de 4 (4!) es: ___?", listOf("6", "24", "36"), "24" ),
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