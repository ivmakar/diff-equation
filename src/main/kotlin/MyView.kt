
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import tornadofx.*
import java.util.*

class MyView: View() {

    val equation = DiffEquation("2*y")

    var eq1 by singleAssign<TextField>()
    var eq2 by singleAssign<TextField>()
    var a by singleAssign<TextField>()
    var b by singleAssign<TextField>()
    var h by singleAssign<TextField>()
    var y0 by singleAssign<TextField>()

    override val root = borderpane {
        center {
            vbox {
                hbox {
                    label("dy/dx = ")
                    eq1 = textfield("2*y") {
                        promptText = "Правая часть уравнения"
                    }
                    label("y = ")
                    eq2 = textfield("exp(2*x)+1") {
                        promptText = "Правая часть уравнения"
                    }
                }
                hbox {
                    label("a = ")
                    a = textfield { filterInput { it.controlNewText.isFloat() } }
                    label("b = ")
                    b = textfield { filterInput { it.controlNewText.isFloat() } }
                    label("h = ")
                    h = textfield { filterInput { it.controlNewText.isFloat() } }
                    label("y0 = ")
                    y0 = textfield { filterInput { it.controlNewText.isFloat() } }
                }
                hbox {
                    button("Метод Эйлера"){
                        action {
                            if (
                                eq1.text.isEmpty() ||
                                a.text.isEmpty() ||
                                b.text.isEmpty() ||
                                h.text.isEmpty() ||
                                y0.text.isEmpty()
                            ) {
                                return@action
                            }
                            equation.replaceFormula(eq1.text)
                            if (eq2.text.isNotEmpty()) {
                                equation.showEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                            } else {
                                equation.showEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                            }
                        }
                    }
                    button("Усовершенствованный метод Эйлера"){
                        action {
                            if (
                                eq1.text.isEmpty() ||
                                a.text.isEmpty() ||
                                b.text.isEmpty() ||
                                h.text.isEmpty() ||
                                y0.text.isEmpty()
                            ) {
                                return@action
                            }
                            equation.replaceFormula(eq1.text)
                            if (eq2.text.isNotEmpty()) {
                                equation.showImprovedEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                            } else {
                                equation.showImprovedEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                            }
                        }
                    }
                }
                hbox {
                    button("Метод Рунге Кутта третьего порядка"){
                        action {
                            if (
                                eq1.text.isEmpty() ||
                                a.text.isEmpty() ||
                                b.text.isEmpty() ||
                                h.text.isEmpty() ||
                                y0.text.isEmpty()
                            ) {
                                return@action
                            }
                            equation.replaceFormula(eq1.text)
                            if (eq2.text.isNotEmpty()) {
                                equation.showThirdOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                            } else {
                                equation.showThirdOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                            }
                        }
                    }
                    button("Метод Рунге Кутта четвертого порядка"){
                        action {
                            if (
                                eq1.text.isEmpty() ||
                                a.text.isEmpty() ||
                                b.text.isEmpty() ||
                                h.text.isEmpty() ||
                                y0.text.isEmpty()
                            ) {
                                return@action
                            }
                            equation.replaceFormula(eq1.text)
                            if (eq2.text.isNotEmpty()) {
                                equation.showFourthOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                            } else {
                                equation.showFourthOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                            }
                        }
                    }
                }
                button("Сразу все методы!"){
                    action {
                        if (
                            eq1.text.isEmpty() ||
                            a.text.isEmpty() ||
                            b.text.isEmpty() ||
                            h.text.isEmpty() ||
                            y0.text.isEmpty()
                        ) {
                            return@action
                        }
                        equation.replaceFormula(eq1.text)
                        if (eq2.text.isNotEmpty()) {
                            equation.showEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                            equation.showImprovedEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                            equation.showThirdOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                            equation.showFourthOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble(), eq2.text)
                        } else {
                            equation.showEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                            equation.showImprovedEulerMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                            equation.showThirdOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                            equation.showFourthOrderRungeKuttaMethod(a.text.toDouble(), b.text.toDouble(), h.text.toDouble(), y0.text.toDouble())
                        }
                    }
                }

                button("Рассчитать скорость") {
                    action {
                        if (
                            eq1.text.isEmpty() ||
                            a.text.isEmpty() ||
                            b.text.isEmpty() ||
                            y0.text.isEmpty()
                        ) {
                            return@action
                        }
                        equation.replaceFormula(eq1.text)
                        val a = a.text.toDouble()
                        val b = b.text.toDouble()
                        val y0 = y0.text.toDouble()

                        var num = "Точек "
                        var time = "Время "

                        for (n in 1000..10000 step 1000) {
                            val h = (b - a) / n
                            var result = equation.eulerMethod(a, b, h, y0)
                        }

                        println("Метод Эйлера:")
                        for (n in 1000..10000 step 1000) {
                            val h = (b - a) / n
                            val startTime = Date()
                            var result = equation.eulerMethod(a, b, h, y0)
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
                            var result = equation.improvedEulerMethod(a, b, h, y0)
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
                            var result = equation.thirdOrderRungeKuttaMethod(a, b, h, y0)
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
                            var result = equation.fourthOrderRungeKuttaMethod(a, b, h, y0)
                            val endTime = Date()
                            num += "\t$n"
                            time += if ((endTime.time - startTime.time).toString().length > 3) "\t${endTime.time - startTime.time}" else "\t${endTime.time - startTime.time}\t"
                        }
                        println(num)
                        println(time)
                    }
                }
            }
        }
    }
}