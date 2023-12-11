package years.y2023.kt.day10

import common.Solution
import common.plus

class Day10 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "4" to "",
            "4" to "",
            "8" to "",
            "26" to "32",
            "99" to "",
            "5" to "",
            "" to "4",
            "" to "8",
            "" to "10"
        )

    fun runMap(input: List<String>, part2: Boolean): Pair<String, MutableSet<Pair<Int, Int>>> = input
        .joinToString(".").let { map ->
            val length = input[0].length + 1

            fun encode(idx: Int) = Pair(idx / length, idx % length)
            fun decode(pair: Pair<Int, Int>) = pair.first * length + pair.second

            val startPoint = encode(map.indexOf('S'))

            var routes = startPoint.run {
                val result = mutableSetOf<Pair<Int, Int>>()

                val up = map.getOrNull(decode(plus(-1, 0)))
                val down = map.getOrNull(decode(plus(1, 0)))
                val left = map.getOrNull(decode(plus(0, -1)))
                val right = map.getOrNull(decode(plus(0, 1)))

                if (up == '|' || up == 'F' || up == '7') result.add(plus(-1, 0))
                if (down == '|' || down == 'J' || up == 'L') result.add(plus(1, 0))
                if (left == '-' || left == 'F' || left == 'L') result.add(plus(0, -1))
                if (right == '-' || right == 'J' || up == '7') result.add(plus(0, 1))

                result.toSet()
            }

            val replaceStartWith = startPoint.run {
                val up = map.getOrNull(decode(plus(-1, 0)))
                val down = map.getOrNull(decode(plus(1, 0)))
                val left = map.getOrNull(decode(plus(0, -1)))
                val right = map.getOrNull(decode(plus(0, 1)))

                when {
                    up == '|' || up == 'F' || up == '7' -> when {
                        down == '|' || down == 'L' || down == 'J' -> '|'
                        left == 'L' || left == 'F' || left == '-' -> 'J'
                        right == 'J' || right == '7' || right == '-' -> 'L'
                        else -> throw Error("no $up")
                    }
                    down == '|' || down == 'L' || down == 'J' -> when {
                        left == 'L' || left == 'F' || left == '-' -> '7'
                        right == 'J' || right == '7' || right == '-' -> 'F'
                        else -> throw Error("no $down")
                    }
                    (left == 'L' || left == 'F' || left == '-') && (right == 'J' || right == '7' || right == '-') -> '-'
                    else -> throw Error("nothing works?")
                }
            }

            var steps = 0

            val seen = routes.toMutableSet()
            seen.add(startPoint)

            while (true) {
                steps++
                val routeMaps = mutableSetOf<Pair<Int, Int>>()

                for (route in routes) {
                    routeMaps.addAll(
                        when (map.getOrNull(decode(route))) {
                            '|' -> listOf(route.plus(1, 0), route.plus(-1, 0))
                            '-' -> listOf(route.plus(0, 1), route.plus(0, -1))
                            'L' -> listOf(route.plus(-1, 0), route.plus(0, 1))
                            'J' -> listOf(route.plus(-1, 0), route.plus(0, -1))
                            '7' -> listOf(route.plus(1, 0), route.plus(0, -1))
                            'F' -> listOf(route.plus(1, 0), route.plus(0, 1))
                            else -> listOf()
                        }
                    )
                }

                val removed = routeMaps.filter { !seen.contains(it) }

                seen.addAll(removed)
                if (removed.isEmpty()) {
                    break
                } else {
                    routes = removed.toSet()
                }
            }

            if (part2) {
                val visitedMap = ".".repeat(input.size * length).toMutableList()

                fun translateChar(char: Char): Char = when (char) {
                    'S' -> translateChar(replaceStartWith)
                    '|' -> '┃'
                    '-' -> '━'
                    'L' -> '┗'
                    'J' -> '┛'
                    '7' -> '┓'
                    'F' -> '┏'
                    else -> '.'
                }

                seen.forEach {
                    visitedMap[decode(it)] = translateChar(map[decode(it)])
                }
                return Pair(visitedMap.joinToString(""), seen)
            }

            return Pair(steps.toString(), seen)
        }

    override fun part1(input: List<String>): String = runMap(input, false).first

    override fun part2(input: List<String>): String = runMap(input, true).let { (it, seen) ->
        val length = input[0].length +1
        val map = it.toMutableList()

        var inside = false
        for (idx in map.indices) {
            if (idx % length == 0) inside = false

            if (map[idx] == '┃' || map[idx] == '┛' || map[idx] == '┗') {
                inside = !inside
                continue
            }

            if (map[idx] == '.') {
                if (inside) {
                    map[idx] = '@'
                } else {
                    map[idx] = '░'
                }
            }
        }

        println("=============")

        println(
            map.chunked(length).joinToString("\n") {it.joinToString("")}
        )



        return map.count { it == '@' }.toString()
    }

}