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

/*
 * https://github.com/ilchen/WeekendPuzzle
 *
 * From: Ilchenko, A.V. (Andrei)
 * Sent: Friday, February 5, 2016 6:29 PM
 * To: ML-OIB CH&PS CB-Portal; ML-OIB CH&PS Id&AccessMgt
 * Subject: Combinatorial search programming puzzle for the weekend
 *
 * Particularly interesting for those who pick up programming in Scala:
 *
 * Given the numbers: 1,3,4,6
 * Make the number: 24
 * You must use each number (1,3,4,6) once and once only.
 * You can use the following operators: + - / * ()
 *
 * The one with the shortest and most elegant solution wins a nice deck of cards with software development wisdoms.
 * Andrei ILchenko
 */
fun main() {
    val solver = Solver(listOf(1, 3, 4, 6), 24)
    val solutions = solver.solve()
    println("Found ${solutions.size} solution${if (solutions.size == 1) "" else "s"}:")
    solutions.forEach { println(it) }
    println("${solver.successfulCalculations} calculations")
}
