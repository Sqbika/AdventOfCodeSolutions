package y2022.kt.day9

import Solution
import kotlin.math.abs

class Pos(
    var x: Int = 0,
    var y: Int = 0
) {
    val posHistory = mutableSetOf<Pair<Int, Int>>()

    fun isClose(other: Pos) = abs(other.x - x) <= 1 && abs(other.y - y) <= 1

    fun calcAndMoveToClosest(other: Pos) {
        if ((abs(other.x - x) >= 2 || abs(other.y - y) >= 2) && abs(other.x - x) + abs(other.y - y) >= 3) {
            if (other.x > x) x+=1 else x-=1
            if (other.y > y) y+=1 else y-=1
        }

        while(
            abs(other.x - x) > 1 ||
            abs(other.y - y) > 1
        ) {
            if (abs(other.x - x) > 1) {
                if (other.x > x) {
                    x++
                } else {
                    x--
                }
            } else {
                if (other.y > y) {
                    y++
                } else {
                    y--
                }
            }
        }

        this.posHistory.add(Pair(x,y))
    }
}

class Day9 : Solution() {
    override val tests: List<Pair<String, String>>
        get() = listOf(
            "13" to "1",
            "" to "36"
        )

    override fun part1(input: List<String>): String {
        val tail = Pos()
        val head = Pos()

        for (line in input) {
            val parts = line.split(" ")
            val amount = parts[1].toInt()

            for (i in (0 until amount)) {
                when(parts[0]) {
                    "R" -> head.x += 1
                    "L" -> head.x -= 1
                    "D" -> head.y -= 1
                    "U" -> head.y += 1
                    else -> throw Error("Uh what? ${parts[0]}")
                }

                tail.calcAndMoveToClosest(head)

                if (isTest) {
                    println("=".repeat(25))
                    println(
                        (0..5).reversed().joinToString("\n") { y ->
                            (0..5).joinToString("") { x ->
                                if (x == head.x && y == head.y) {
                                    "H"
                                } else if (x == tail.x && y == tail.y) {
                                    "T"
                                } else {
                                    "."
                                }
                            }
                        }
                    )
                }
            }
        }

        return tail.posHistory.size.toString()
    }

    override fun part2(input: List<String>): String {
        val snake = (0..9).map{Pos()}

        for (line in input) {
            val parts = line.split(" ")
            val amount = parts[1].toInt()

            val head = snake[0]

            for (i in (0 until amount)) {
                when(parts[0]) {
                    "R" -> head.x += 1
                    "L" -> head.x -= 1
                    "D" -> head.y -= 1
                    "U" -> head.y += 1
                    else -> throw Error("Uh what? ${parts[0]}")
                }

                snake.reduce { prev, cur ->
                    cur.calcAndMoveToClosest(prev)
                    cur
                }
            }
        }

        return snake.last().posHistory.size.toString()
    }

}