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

import org.junit.jupiter.api.Test
import kotlin.test.DefaultAsserter.assertEquals

class SolverTest {

  @Test
  fun testSolver() {
    val solutions = Solver(listOf(1, 3, 4, 6), 24).solve()
    assertEquals("wrong number of solutions", solutions.size, 1)

    // solutions.forEach { println(it) }
  }
}
