import java.io.IOException
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.pow


class Formula(formula: String) {

    var postfix = listOf<String>()
    var flag = true
    var errorMessage = ""

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
    }

    fun f(x: Double, y: Double): Double {
        if (!flag) {
            throw IOException(errorMessage)
        }
        val stack = Stack<Double>()
        for (cur in postfix) {
            when (cur) {
                "sqrt" -> stack.push(Math.sqrt(stack.pop()))
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

    internal object ExpressionParser {
        private const val operators = "+-*/^"
        private const val delimiters = "() $operators"
        var flag = true
        var message = ""

        private fun isDelimiter(token: String): Boolean {
            if (token.length != 1) return false
            for (element in delimiters) {
                if (token[0] == element) return true
            }
            return false
        }

        private fun isOperator(token: String): Boolean {
            if (token == "u-") return true
            for (element in operators) {
                if (token[0] == element) return true
            }
            return false
        }

        private fun isFunction(token: String): Boolean {
            return token in listOf("sqrt")
        }

        private fun priority(token: String): Int {
            if (token == "(") return 1
            if (token == "+" || token == "-") return 2
            return if (token == "*" || token == "/") 3 else 4
        }

        private fun isVariable(token: String): Boolean {
            return token in listOf("x", "y")
        }

        fun parse(infix: String?): List<String> {
            val postfix = mutableListOf<String>()
            val stack = Stack<String>()
            val tokenizer = StringTokenizer(infix, delimiters, true)
            var prev = ""
            var curr = ""
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
