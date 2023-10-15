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
                Question(4, "Espacio = Velocidad x ___", listOf("masa", "distancia", "tiempo"), "tiempo" ),
                Question(5, "Azul y Amarillo hacen: ___", listOf("verde", "celeste", "rojo"), "verde" ),
                Question(6, "Factorial de 4 es: 4! = ___", listOf("6", "24", "36"), "24" ),

                Question(7, "¿Cuál es la raíz cuadrada de 9?", listOf("3", "0", "4"), "3" ),
                Question(8, "El 20% de 200 es: ___", listOf("20", "40", "50"), "40" ),
                Question(9, "3 + 4 x 5 = ___", listOf("35", "32", "23"), "23" ),
                Question(10, "Un lustro tiene ___ años", listOf("5", "50", "500"), "5" ),
                Question(11, "Si: '3a=30', entonces: a= ___", listOf("3", "10", "30"), "10" ),
                Question(12, "Domingo es de color: ___", listOf("blanco", "negro", "marrón"), "negro" ),
            )
        }
    }
}