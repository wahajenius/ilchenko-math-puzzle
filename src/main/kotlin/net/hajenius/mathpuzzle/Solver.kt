/**
 * Copyright 2023 Willem Alexander Hajenius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hajenius.mathpuzzle

import java.lang.Exception
import kotlin.math.floor

/**
 * The solver algorithm uses a depth-first search to generate all possible valid RPN expressions that satisfy
 * the puzzle constraints. Since only a fixed set of numbers and binary operators are involved, this expression
 * must always have a constant length to be valid. The expression is initially empty. Numbers are placed first,
 * then the operators. When all spots are filled an RPN calculator attempts to calculate the result.
 * If it matches the goal, the RPN is converted to infix and stored. Floating point calculation allows to weed
 * out degenerate solutions that rely on integer division rounding behavior, like (1 / 3) + (4 * 6).
 */
class Solver(private val numbers: List<Int>, private val goal: Int) {
    private val nil = ""
    private val solutions = mutableSetOf<String>()
    private val operators = listOf("+", "-", "*", "/")

    fun solve(): Set<String> {
        val totalOperators = numbers.size - 1 // A binary operator has a net result of one less operand
        val expr = MutableList(numbers.size + totalOperators) { _ -> nil }

        placeOperand(expr, numbers)
        return solutions
    }

    private fun placeOperand(expression: MutableList<String>, remaining: List<Int>) {
        if (remaining.isEmpty()) {
            placeOperator(expression)
        } else for (i in expression.indices) {
            if (expression[i] == nil) {
                expression[i] = remaining.first().toString()
                placeOperand(expression, remaining.drop(1))
                expression[i] = nil // Backtrack
            }
        }
    }

    private fun placeOperator(expression: MutableList<String>) {
        for (i in expression.indices) {
            if (expression[i] == nil) {
                for (op in operators) {
                    expression[i] = op
                    if (expression.contains(nil)) {
                        placeOperator(expression)
                    } else {
                        evaluate(expression)
                    }
                    expression[i] = nil // Backtrack
                }
            }
        }
    }

    private fun evaluate(expression: List<String>) {
        try {
            val result = calculate(expression)
            if (result == goal) {
                solutions.add(toInfix(expression))
            }
        } catch (_: Exception) {
            // Nothing to do here; expressions causing errors cannot be valid
        }
    }

    private fun calculate(expression: List<String>): Int {
        val stack = mutableListOf<Double>()
        for (token in expression) {
            if (token in operators) {
                binaryOperator(stack, token)
            } else stack.add(token.toDouble())
        }
        check(stack.size == 1)
        return stack.last().toInt()
    }

    private fun Double.isInt(): Boolean = !this.isInfinite() && floor(this) == this

    private fun binaryOperator(stack: MutableList<Double>, operator: String) {
        val y = stack.removeLast()
        val x = stack.removeLast()
        val result = when (operator) {
            "+" -> x + y
            "-" -> x - y
            "*" -> x * y
            "/" -> x / y
            else -> error("Unknown binary operator '$operator'")
        }
        check(result.isInt())
        stack.add(result)
    }

    private fun toInfix(expression: List<String>): String {
        val stack = mutableListOf<String>()
        for (token in expression) {
            if (token in operators) {
                val y = stack.removeLast()
                val x = stack.removeLast()
                stack.add("($x $token $y)")
            } else stack.add(token)
        }
        val result = stack.first()

        // The last operator will cause braces to be put around the entire expression which can be omitted
        return result.substring(1..<result.length - 1)
    }
}
