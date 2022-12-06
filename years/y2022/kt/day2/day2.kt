package y2022.kt.day2

import Solution

class Day2 : Solution() {
    override val tests: List<Pair<String, String>>
        get() = listOf(
            "15" to "0"
        )



    override fun part1(input: List<String>): String {
        var score = 0

        val points = mapOf<String,Int>(
            "X" to 1,
            "Y" to 2,
            "Z" to 3
        )

        val winTree = mapOf(
            "A" to mapOf(
                "X" to 3,
                "Y" to 6,
                "Z" to 0
            ),
            "B" to mapOf(
                "X" to 0,
                "Y" to 3,
                "Z" to 6
            ),
            "C" to mapOf(
                "X" to 6,
                "Y" to 0,
                "Z" to 3
            )
        )


        return input.fold(0) { acc, match ->
            val parts = match.split(" ")

            points[parts[1]]!! + acc + winTree[parts[0]]!![parts[1]]!!
        }.toString()
    }

    override fun part2(input: List<String>): String {
        /*
        X: lose
        Y: draw
        Z: win
         */
        /*
        A: rock, 1
        B: paper, 2
        Z: scissor, 3
         */

        val winTree = mapOf(
            "A" to mapOf( //rock
                "X" to 0+3, //scissor
                "Y" to 3+1, //rock
                "Z" to 6+2 //paper
            ),
            "B" to mapOf( //paper
                "X" to 0+1, //rock
                "Y" to 3+2, //paper
                "Z" to 6+3 //scissor
            ),
            "C" to mapOf( //scissor
                "X" to 0+2, //paper
                "Y" to 3+3, //scissor
                "Z" to 6+1 //rock
            )
        )

        return input.fold(0) { acc, match ->
            val parts = match.split(" ")

            acc + winTree[parts[0]]!![parts[1]]!!
        }.toString()
    }

}
