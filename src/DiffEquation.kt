package org.sample

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.ui.RefineryUtilities
import java.awt.Color
import java.awt.Dimension
import java.io.IOException
import java.util.*
import javax.swing.JFrame
import javax.swing.WindowConstants
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Класс предназначенный для решения простых дифференциальных уравнений четырьмя методами
 *
 * @param formula Правая часть дифференциального уравнения. Например: dy/dx = y-x. Здесь formula будет y-x
 * @property f Экземпляр класса Formula, предназначенного для вычисления формулы
 */

class DiffEquation(val formula: String) {

    private var f: Formula = Formula(formula)

    /**
     * Метод Рунге-Кутта третьего порядка
     *
     * @param a Левая граница отрезка
     * @param b Правая граница отрезка
     * @param h Величина шага
     * @param y0 Начальное значение y(0)
     *
     * @return Таблица решений Xi => Yi
     */
    fun thirdOrderRungeKuttaMethod(a: Double, b: Double, h: Double, y0: Double): Map<Double, Double> {
        val n = ((b - a) / h).toInt()
        val x = mutableListOf(a)
        val k1 = mutableListOf(0.0)
        val k2 = mutableListOf(0.0)
        val k3 = mutableListOf(0.0)
        val y = mutableListOf(y0)

        var resultXY = mapOf(Pair(a, y0))

        for (i in 1..n) {
            x.add( a + i * h )
            k1.add( f.f( x[i-1], y[i-1] ))
            k2.add( f.f( x[i-1] + h / 2.0, y[i-1] + h * k1[i] / 2.0 ))
            k3.add( f.f( x[i-1] + h, y[i-1] - h * k1[i] + 2 * h * k2[i] ))
            y.add( y[i-1] + h * ( k1[i] + 4 * k2[i] + k3[i] ) / 6)

            resultXY = resultXY + Pair( x[i], y[i] )
        }

        return resultXY
    }

    /**
     * Метод Рунге-Кутта четвертого порядка
     *
     * @param a Левая граница отрезка
     * @param b Правая граница отрезка
     * @param h Величина шага
     * @param y0 Начальное значение y(0)
     *
     * @return Таблица решений Xi => Yi
     */
    fun fourthOrderRungeKuttaMethod(a: Double, b: Double, h: Double, y0: Double): Map<Double, Double> {
        val n = ((b - a) / h).toInt()
        val x = mutableListOf(a)
        val k1 = mutableListOf(0.0)
        val k2 = mutableListOf(0.0)
        val k3 = mutableListOf(0.0)
        val k4 = mutableListOf(0.0)
        val y = mutableListOf(y0)

        var resultXY = mapOf(Pair(a, y0))

        for (i in 1..n) {
            x.add( a + i * h )
            k1.add( h * f.f( x[i-1], y[i-1] ))
            k2.add( h * f.f( x[i-1] + h / 2.0, y[i-1] + k1[i] / 2.0 ))
            k3.add( h * f.f( x[i-1] + h / 2, y[i-1] + k2[i] / 2 ))
            k4.add( h * f.f( x[i-1] + h, y[i-1] + k3[i] ) )
            y.add( y[i-1] + ( k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i] ) / 6)

            resultXY = resultXY + Pair( x[i], y[i] )
        }

        return resultXY
    }

    /**
     * Простой метод Эйлера
     *
     * @param a Левая граница отрезка
     * @param b Правая граница отрезка
     * @param h Величина шага
     * @param y0 Начальное значение y(0)
     *
     * @return Таблица решений Xi => Yi
     */
    fun eulerMethod (a: Double, b: Double, h: Double, y0: Double): Map<Double, Double> {
        val n = ((b - a) / h).toInt()
        val x = mutableListOf(a)
        val y = mutableListOf(y0)

        var resultXY = mapOf(Pair(a, y0))

        for (i in 1..n) {
            x.add( a + i * h )
            y.add( y[i-1] + h * f.f(x[i-1], y[i-1]))

            resultXY = resultXY + Pair( x[i], y[i] )
        }
        return resultXY
    }

    /**
     * Усовершенствованный метод Эйлера
     *
     * @param a Левая граница отрезка
     * @param b Правая граница отрезка
     * @param h Величина шага
     * @param y0 Начальное значение y(0)
     *
     * @return Таблица решений Xi => Yi
     */
    fun improvedEulerMethod (a: Double, b: Double, h: Double, y0: Double): Map<Double, Double> {
        val n = ((b - a) / h).toInt()
        val x = mutableListOf(a)
        val y = mutableListOf(y0)

        var resultXY = mapOf(Pair(a, y0))

        for (i in 1..n) {
            x.add( a + i * h )
            y.add( y[i-1] + h * f.f(x[i-1] + h / 2, y[i-1] + h / 2 * f.f(x[i-1], y[i-1])))

            resultXY = resultXY + Pair( x[i], y[i] )
        }
        return resultXY
    }

    fun showThirdOrderRungeKuttaMethod (a: Double, b: Double, h: Double, y0: Double, solution: String? = null) {
        val result = thirdOrderRungeKuttaMethod(a, b, h, y0)
        ScatterPlot("График функции", result, "Метод Рунге-Кутта третьего порядка", formula, solution).showWindow()
    }

    fun showFourthOrderRungeKuttaMethod (a: Double, b: Double, h: Double, y0: Double, solution: String? = null) {
        val result = fourthOrderRungeKuttaMethod(a, b, h, y0)
        ScatterPlot("", result, "Метод Рунге-Кутта четвертого порядка", formula, solution).showWindow()
    }

    fun showEulerMethod (a: Double, b: Double, h: Double, y0: Double, solution: String? = null) {
        val result = eulerMethod(a, b, h, y0)
        ScatterPlot("", result, "Простой метод Эйлера", formula, solution).showWindow()
    }

    fun showImprovedEulerMethod (a: Double, b: Double, h: Double, y0: Double, solution: String? = null) {
        val result = improvedEulerMethod(a, b, h, y0)
        ScatterPlot("", result, "Усовершенствованный метод Эйлера", formula, solution).showWindow()
    }

    /**
     * Функция для обновления формулы в экземпляре класса
     *
     * @param formula Правая часть дифференциального уравнения. Например: dy/dx = y-x. Здесь formula будет y-x
     */
    fun replaceFormula(formula: String) {
        f = Formula(formula)
    }


    /**
     * Класс предназначенный для преобразования формулы из строки в обратную польскую запись и вычисления ее.
     *
     * @param formula Формула в виде строки
     * @property postfix Формула в обратной польской записи в виде списка
     * @property flag Флаг, указывающий на корректность переданной формулы
     * @property errorMessage Текст сообщения в случае некорректности формулы
     *
     */
    private class Formula(formula: String) {

        private var postfix = listOf<String>()
        private var flag = true
        private var errorMessage = ""

        init {
            if (formula.isEmpty()) {
                flag = false
                errorMessage = "Формула введена некорректно"
            } else {
                val parser = ExpressionParser
                postfix = parser.parse(formula)
                if (!parser.flag) {
                    flag = parser.flag
                    errorMessage = parser.message
                }
            }

            if (!flag) {
                throw IOException(errorMessage)
            }
        }

        /**
         * Функция для вычисления формулы
         *
         * @param x Переменная x
         * @param y Переменная y
         * @return Результат вычисления функции в точке (x;y)
         *
         */
        fun f(x: Double, y: Double): Double {
            val stack = Stack<Double>()
            for (cur in postfix) {
                when (cur) {
                    "sqrt" -> stack.push(sqrt(stack.pop()))
                    "exp" -> stack.push(exp(stack.pop()))
                    "+" -> stack.push(stack.pop() + stack.pop())
                    "-" -> {
                        val b = stack.pop()
                        val a = stack.pop()
                        stack.push(a - b)
                    }
                    "*" -> stack.push(stack.pop() * stack.pop())
                    "/" -> {
                        val b = stack.pop()
                        val a = stack.pop()
                        stack.push(a / b)
                    }
                    "^" -> {
                        val b = stack.pop()
                        val a = stack.pop()
                        stack.push(a.pow(b))
                    }
                    "u-" -> stack.push(-stack.pop())
                    "x" -> stack.push(x)
                    "y" -> stack.push(y)
                    else -> stack.push(cur.toDouble())
                }
            }
            return stack.pop()
        }

        /**
         * Класс, предназначенный для преобразования формулы в обратную польскую запись     *
         */
        internal object ExpressionParser {
            private const val operators = "+-*/^"
            private const val delimiters = "() $operators"
            var flag = true
            var message = ""

            /**
             * Проверка, является ли переданный токен разделителем
             */
            private fun isDelimiter(token: String): Boolean {
                if (token.length != 1) return false
                for (element in delimiters) {
                    if (token[0] == element) return true
                }
                return false
            }

            /**
             * Проверка, является ли переданный токен оператором
             */
            private fun isOperator(token: String): Boolean {
                if (token == "u-") return true
                for (element in operators) {
                    if (token[0] == element) return true
                }
                return false
            }

            /**
             * Проверка, является ли переданный токен функцией (sqrt)
             */
            private fun isFunction(token: String): Boolean {
                return token in listOf("sqrt", "exp")
            }

            /**
             * Вычисляется приоритет операции
             */
            private fun priority(token: String): Int {
                if (token == "(") return 1
                if (token == "+" || token == "-") return 2
                return if (token == "*" || token == "/") 3 else 4
            }

            /**
             * Функция для преобразования инфиксной записи в постфиксную
             *
             * @param infix Инфиксная запись в виде строки
             * @return Постфиксная запись в виде списка
             */
            fun parse(infix: String?): List<String> {
                val postfix = mutableListOf<String>()
                val stack = Stack<String>()
                val tokenizer = StringTokenizer(infix, delimiters, true)
                var prev = ""
                var curr: String
                while (tokenizer.hasMoreTokens()) {
                    curr = tokenizer.nextToken()
                    if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                        message = "Некорректная формула"//throw IOException("Некорректная формула")
                        flag = false
                        return postfix
                    }
                    if (curr == " ") continue
                    if (isFunction(curr)) stack.push(curr) else if (isDelimiter(curr)) {
                        if (curr == "(") stack.push(curr) else if (curr == ")") {
                            while (stack.peek() != "(") {
                                postfix.add(stack.pop())
                                if (stack.isEmpty()) {
                                    message = "Скобки не согласованы"
                                    flag = false
                                    return postfix
                                }
                            }
                            stack.pop()
                            if (!stack.isEmpty() && isFunction(stack.peek())) {
                                postfix.add(stack.pop())
                            }
                        } else {
                            if (curr == "-" && (prev == "" || isDelimiter(prev) && prev != ")")) {
                                // унарный минус
                                curr = "u-"
                            } else {
                                while (!stack.isEmpty() && priority(curr) <= priority(
                                                stack.peek()
                                        )
                                ) {
                                    postfix.add(stack.pop())
                                }
                            }
                            stack.push(curr)
                        }
                    } else {
                        postfix.add(curr)
                    }
                    prev = curr
                }
                while (!stack.isEmpty()) {
                    if (isOperator(stack.peek())) postfix.add(stack.pop()) else {
                        message = "Скобки не согласованы"
                        flag = false
                        return postfix
                    }
                }
                return postfix
            }
        }
    }

    private class ScatterPlot(title: String?, val data: Map<Double, Double>, val method: String, val f: String, val solution: String? = null) : JFrame(title) {
        private fun createDataset(): XYDataset {
            val dataset = XYSeriesCollection()

            //Boys (Age,weight) series
            val graph = XYSeries("dy/dx = $f")
            for (i in data) {
                graph.add(i.key, i.value)
            }
            dataset.addSeries(graph)

            if (solution != null) {
                val f = Formula(solution)
                val gr = XYSeries("y = $solution")
                for (i in data) {
                    gr.add(i.key, f.f(i.key, 0.0))
                }
                dataset.addSeries(gr)
            }

            return dataset
        }

        init {

            // Create dataset
            val dataset: XYDataset = createDataset()

            // Create chart
            val chart: JFreeChart = ChartFactory.createXYLineChart(
                    method,
                    "X-Axis",
                    "Y-Axis",
                    dataset)


            val panel = ChartPanel(chart)
            contentPane = panel
        }

        fun showWindow() {
            this.setSize(1000, 500)
            this.setLocationRelativeTo(null)
            this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            this.isVisible = true
        }
    }
}
