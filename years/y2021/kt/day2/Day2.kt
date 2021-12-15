package y2021.kt.day2

import Solution

class Day2: Solution() {


    override fun part1(input: List<String>): String {
        return input.fold(arrayOf(0, 0)) { pair, str ->
                val (dir, amount) = str.split(" ");
                when (dir) {
                    "forward" -> pair[0] += amount.toInt()
                    "backward" -> pair[0] -= amount.toInt()
                    "up" -> pair[1] -= amount.toInt()
                    "down" -> pair[1] += amount.toInt()
                }
                pair
            }.reduce { a, b -> a * b }
                .toString()
    }

    override fun part2(input: List<String>): String {
        return input.fold(arrayOf(0, 0, 0)) { pair, str ->
                val (dir, amount) = str.split(" ");
                when (dir) {
                    "forward" -> {
                        pair[0] += amount.toInt()
                        pair[1] += amount.toInt() * pair[2]
                    }
                    "backward" -> {
                        pair[0] -= amount.toInt()
                        pair[1] -= amount.toInt() * pair[2]
                    }
                    "up" -> pair[2] -= amount.toInt()
                    "down" -> pair[2] += amount.toInt()
                }
                pair
            }.slice(0..1).reduce { a, b -> a * b }
            .toString()
    }
}
