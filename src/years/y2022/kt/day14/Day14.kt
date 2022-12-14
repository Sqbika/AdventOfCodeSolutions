package years.y2022.kt.day14

import common.Solution

class Map(
    input: List<String>
) {

    val splitter = " -> "

    val map = mutableMapOf<Int, MutableMap<Int, Int>>()

    val rockId = 1
    val sandId = 2

    val sandSpawn = Pair(500, 0)

    var maxY = -1

    init {
        input.map {
            it.split(splitter)
        }.forEach {
            it.map {
                it.split(",").map { it.toInt() }
            }.zipWithNext().forEach { pair ->
                val start = pair.first
                val end = pair.second

                for (x in toRange(start[0], end[0])) {
                    for (y in toRange(start[1], end[1])) {
                        set(x, y)
                    }
                }
            }
        }

        set(sandSpawn.first, sandSpawn.second, sandId)

        maxY = map.maxOf { it.key }
    }

    fun typeToAscii(type: Int): String = when (type) {
        rockId -> "█"
        sandId -> "░"
        else -> " "
    }

    fun set(x: Int, y: Int, type: Int = rockId) {
        var list = map.get(y)
        if (list == null) {
            map[y] = mutableMapOf()
            list = map[y]
        }

        list!![x] = type
    }

    fun get(x: Int, y: Int): Int? = map[y]?.get(x)

    fun toRange(left: Int, right: Int): IntRange {
        return if (left < right) {
            left..right
        } else {
            right..left
        }
    }

    fun yRange(): IntRange {
        val min = minOf(map.keys.minOf { it }, 0)
        val max = map.keys.maxOf { it }

        return (min..max)
    }

    fun xRange(): IntRange {
        val min = map.minOf { it.value.minOf { it.key } }
        val max = map.maxOf { it.value.maxOf { it.key } }

        return (min..max)
    }

    fun print() {
        println(
            yRange().joinToString("\n") { y ->
                val xRow = map[y] ?: return@joinToString xRange().joinToString("") { " " }

                xRange().joinToString("") { x ->
                    typeToAscii(xRow[x] ?: 0)
                }
            }
        )
    }

    fun dropSand(): Boolean {
        val x = sandSpawn.first
        var y = sandSpawn.second

        while (y <= maxY) {
            when (
                get(x, y + 1)
            ) {
                null -> {
                    y++
                    continue
                }

                rockId -> {
                    set(x, y, sandId)
                    return true
                }

                sandId -> {
                    var counter = 1

                    while (
                        !(
                            get(x - counter, y + counter) == null &&
                            get(x - counter - 1, y + counter + 1) != null
                        ) && y + counter <= maxY
                    ) {
                        counter++
                    }

                    if (
                        get(x - counter, y + counter) == null &&
                        get(x - counter - 1, y + counter + 1) != null
                    ) {
                        set(x - counter, y + counter, sandId)
                        return true
                    }

                    if (y+counter <= maxY) return false

                    counter = 1;

                    while (
                        !(
                            get(x + counter, y + counter) == null &&
                            get(x + counter + 1, y + counter + 1) != null
                        ) && y+counter <= maxY
                    ) {
                        counter++
                    }

                    if (
                        get(x + counter, y + counter) == null &&
                        get(x + counter + 1, y + counter + 1) != null
                    ) {
                        set(x + counter, y + counter, sandId)
                        return true
                    }

                    if (y+counter <= maxY) return false

                    set(x, y, sandId)
                    return true
                }
            }
        }

        return false
    }
}

class Day14 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "24" to ""
        )

    override fun part1(input: List<String>): String {
        val map = Map(input)

        var res = 0

        do {
            println("=".repeat(25))
            map.print()
            res++
        } while (map.dropSand())


        return res.toString()
    }

    override fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }
}
