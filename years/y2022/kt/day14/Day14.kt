package years.y2022.kt.day14

import common.Solution

enum class ScanStatus {
    IsPlaced,
    IsRock,
    IsSand,
    IsEmpty,
    Panic
}

class Map(
    input: List<String>
) {

    val splitter = " -> "

    val map = mutableMapOf<Int, MutableMap<Int, Int>>()

    val rockId = 1
    val sandId = 2
    val shouldHaveBeenSandId = 3

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
        shouldHaveBeenSandId -> "X"
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

    fun getFancy(x: Int, y: Int): ScanStatus = when(get(x,y)) {
        null -> ScanStatus.IsEmpty
        rockId -> ScanStatus.IsRock
        sandId -> ScanStatus.IsSand
        else -> throw Error("This when status is not processed")
    }

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
                    if (handleSand(x,y) == false) {
                        set(x, y, sandId)
                    }
                    return true
                }
            }
        }

        return false
    }

    private fun handleSand(x: Int, y: Int, addY: boolean = true): ScanStatus {
        val it = getFancy(x,y)

        return when (it) {
            ScanStatus.IsEmpty -> when (getFancy(x,y+1)) {
                ScanStatus.IsEmpty -> getFancy(x, y+1)
            }
            ScanStatus.IsRock ->
        }
    }
}

class Day14 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "24" to ""
        )

    val sandCheck = listOf(
        Pair(500, 8),
        Pair(499, 8),
        Pair(501, 8),
        Pair(500, 7),
        Pair(498, 8),
        Pair(499, 7),
        Pair(501, 7),
        Pair(500, 6),
        Pair(497, 8),
        Pair(498, 7)
    )

    override fun part1(input: List<String>): String {
        val map = Map(input)

        var res = 0

        while(true) {
            val result = map.dropSand()
            println("=".repeat(25))
            if (isTest && false) {
                val check = sandCheck.getOrNull(res)
                if (check != null && map.get(check.first, check.second) != 2) {
                    map.set(check.first, check.second, 3)
                    map.print()
                    throw Error("$check is not sand!")
                }
            }
            map.print()
            res++

            if (!result) break
        }

        res++


        return res.toString()
    }

    override fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }
}
