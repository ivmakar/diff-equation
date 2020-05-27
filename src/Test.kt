package org.sample

import java.util.*

fun main() {

    val equation = DiffEquation("2*y")

    equation.showEulerMethod(0.0,10.0,0.1,1.0, "exp(2*x)+1")
    equation.showImprovedEulerMethod(0.0,10.0,0.1,1.0, "exp(2*x)+1")
    equation.showThirdOrderRungeKuttaMethod(0.0,10.0,0.1,1.0, "exp(2*x)+1")
    equation.showFourthOrderRungeKuttaMethod(0.0,10.0,0.1,1.0, "exp(2*x)+1")

//    equation.replaceFormula("1/y")
//
//    equation.showImprovedEulerMethod(0.0,10.0,1.0,1.0, "sqrt(2*x+1)")
//    equation.showEulerMethod(0.0,10.0,1.0,1.0, "sqrt(2*x+1)")
//    equation.showThirdOrderRungeKuttaMethod(0.0,10.0,1.0,1.0, "sqrt(2*x+1)")
//    equation.showFourthOrderRungeKuttaMethod(0.0,10.0,1.0,1.0, "sqrt(2*x+1)")

    /*val a = 0.0
    val b = 10.0

    var num = "Точек "
    var time = "Время "

    for (n in 1000..10000 step 1000) {
        val h = (b - a) / n
        var result = equation.eulerMethod(a, b, h, 1.0)
    }

    println("Метод Эйлера:")
    for (n in 1000..10000 step 1000) {
        val h = (b - a) / n
        val startTime = Date()
        var result = equation.eulerMethod(a, b, h, 1.0)
        val endTime = Date()
        num += "\t$n"
        time += if ((endTime.time - startTime.time).toString().length > 3) "\t${endTime.time - startTime.time}" else "\t${endTime.time - startTime.time}\t"
    }
    println(num)
    println(time)

    num = "Точек "
    time = "Время "

    println("Усовершенствованный метод Эйлера:")
    for (n in 1000..10000 step 1000) {
        val h = (b - a) / n
        val startTime = Date()
        var result = equation.improvedEulerMethod(a, b, h, 1.0)
        val endTime = Date()
        num += "\t$n"
        time += if ((endTime.time - startTime.time).toString().length > 3) "\t${endTime.time - startTime.time}" else "\t${endTime.time - startTime.time}\t"
    }
    println(num)
    println(time)

    num = "Точек "
    time = "Время "

    println("Метод Рунге-Кутта третьего порядка:")
    for (n in 1000..10000 step 1000) {
        val h = (b - a) / n
        val startTime = Date()
        var result = equation.thirdOrderRungeKuttaMethod(a, b, h, 1.0)
        val endTime = Date()
        num += "\t$n"
        time += if ((endTime.time - startTime.time).toString().length > 3) "\t${endTime.time - startTime.time}" else "\t${endTime.time - startTime.time}\t"
    }
    println(num)
    println(time)

    num = "Точек "
    time = "Время "

    println("Метод Рунге-Кутта четвертого порядка:")
    for (n in 1000..10000 step 1000) {
        val h = (b - a) / n
        val startTime = Date()
        var result = equation.fourthOrderRungeKuttaMethod(a, b, h, 1.0)
        val endTime = Date()
        num += "\t$n"
        time += if ((endTime.time - startTime.time).toString().length > 3) "\t${endTime.time - startTime.time}" else "\t${endTime.time - startTime.time}\t"
    }
    println(num)
    println(time)*/
}