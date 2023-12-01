package years.y2022.kt.day12

import common.Solution

class Pos(
    var x: Int,
    var y: Int
) {
    companion object {
        private val heights = 'a'..'z'

        fun getHeight(char: Char): Int {
            if (char == 'S') return heights.indexOf('a')
            if (char == 'E') return heights.indexOf('z')

            return heights.indexOf(char)
        }

        fun fromInput(input: List<String>, char: Char): Pos {
            val lineContainsChar = input.find {
                it.contains(char.uppercaseChar(), false)
            }

            return Pos(
                lineContainsChar!!.indexOf(char.uppercaseChar()),
                input.indexOf(lineContainsChar)
            )
        }
    }

    override fun toString(): String {
        return "[P: $x/$y]"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other.javaClass != this.javaClass) return false

        return (other as Pos).x == x && other.y == y
    }

    override fun hashCode(): Int {
        return x.hashCode() + y.hashCode()
    }

    fun get(input: List<String>): Char? = input.getOrNull(y)?.getOrNull(x)

    private fun getNeighbourCoords() = listOf(
        Pos(x-1, y), Pos(x, y-1), Pos(x+1,y), Pos(x,y+1)
    )

    fun getSurrounding(input: List<String>): List<Pos> {
        val thisVal = getHeight(get(input) ?: throw Error("Pos.get returned null. Uhoh?"))

        return getNeighbourCoords().filter {
            val value = it.get(input)

            value != null && getHeight(value) <= thisVal+1
        }
    }
}

class Day12 : Solution() {
    override val tests: List<Pair<String, String>>
        get() = listOf(
            "31" to "29"
        )

    override fun part1(input: List<String>): String {
        val start = Pos.fromInput(input, 'S')
        val goal = Pos.fromInput(input, 'E')

        println("$start / $goal")

        val distance = findDistance(input, start, goal)

        return distance.toString()
    }

    override fun part2(input: List<String>): String {
        val starts = mutableSetOf<Pos>()
        val goal = Pos.fromInput(input, 'E')

        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, ch ->
                if (Pos.getHeight(ch) == 0) {
                    starts.add(Pos(x,y))
                }
            }
        }

        println("PosCount: ${starts.size}")

        return starts.minOf {
                findDistance(input, it, goal)
        }.toString()
    }

    fun findDistance(input: List<String>, start: Pos, goal: Pos): Int {
        val visited = mutableSetOf<Pair<Int,Int>>()
        var paths = setOf<Pos>(start)
        var distance = 0

        while(paths.isNotEmpty()) {
            distance++
            val newPaths = mutableSetOf<Pos>()

            paths.forEach { path ->
                visited.add(Pair(path.x, path.y))

                newPaths.addAll(
                    path.getSurrounding(input).filter {
                        !visited.contains(Pair(it.x, it.y))
                    }
                )
            }

            paths = newPaths

            /*
            if (isTest) {
            println("=".repeat(25))
            println(
                (input.indices).joinToString("\n") { y ->
                    (input[y].indices).joinToString("") { x ->
                        if (visited.contains(Pair(x, y))) {
                            "#"
                        } else if (newPaths.any { it.x == x && it.y == y }){
                            "@"
                        } else {
                            " "
                        }
                    }
                }
            )
            }*/

            if (newPaths.any {
                    goal.x == it.x && goal.y == it.y
                }) {
                break
            }
        }

        if (paths.isEmpty()) return Int.MAX_VALUE

        return distance
    }

}
