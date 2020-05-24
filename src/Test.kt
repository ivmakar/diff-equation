package org.sample

fun main() {

    val equation = DiffEquation("y-x")
    println(equation.eulerMethod(0.0, 1.0, 0.1, 1.0))
    println(equation.improvedEulerMethod(0.0, 1.0, 0.1, 1.0))
    println(equation.thirdOrderRungeKuttaMethod(0.0, 1.0, 0.1, 1.0))
    println(equation.fourthOrderRungeKuttaMethod(0.0, 1.0, 0.1, 1.0))

    equation.replaceFormula("3*y+x")

    println(equation.eulerMethod(0.0, 1.0, 0.1, 1.0))
    println(equation.improvedEulerMethod(0.0, 1.0, 0.1, 1.0))
    println(equation.thirdOrderRungeKuttaMethod(0.0, 1.0, 0.1, 1.0))
    println(equation.fourthOrderRungeKuttaMethod(0.0, 1.0, 0.1, 1.0))
}