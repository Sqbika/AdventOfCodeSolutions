package y2021.kt.day4

import Solution
import transpose

class Day4: Solution() {

    override fun part1(input: List<String>): String {
        val bingo = Bingo(input)
        while (!bingo.solvePart1(bingo.numbers.removeFirst()) && bingo.numbers.isNotEmpty()) {}
        return bingo.result.toString()
    }

    override fun part2(input: List<String>): String {
        val bingo = Bingo(input)
        while (!bingo.solvePart2(bingo.numbers.removeFirst()) && bingo.numbers.isNotEmpty()) {}
        return bingo.result.toString()
    }
}

class Bingo(
    input: List<String>
) {
    val numbers: MutableList<Int>

    val boards: MutableList<MutableList<List<String>>> = mutableListOf()

    var result: Int = Int.MIN_VALUE

    init {
        numbers = input[0].split(",").map{it.toInt()}.toMutableList()
        input.drop(1).chunked(6).map {
            boards.add(
                it.drop(1).map {
                    it.trim().replace(Regex("\\s+"), ",").split(",")
                }.toMutableList()
            )
        }
    }

    fun updateBoards(n:Int) {
        boards.forEach { board ->
            board.replaceAll { row ->
                row.map { col ->
                    if (col == n.toString()) "X" else col
                }
            }
        }
    }

    fun solvePart1(n:Int): Boolean {
        this.updateBoards(n)

        return hasWinningBoard(n)
    }

    fun solvePart2(n:Int): Boolean {
        this.updateBoards(n)

        if (boards.size == 1) {
            result = sumBoard(boards[0]) * n
            return true
        }

        boards.removeIf {isBoardWinning(it)}

        return false
    }

    fun sumBoard(board: MutableList<List<String>>) = board.sumOf { row ->
        row.sumOf {
            if (it === "X") 0 else it.toInt()
        }
    }


    fun printBoards() {
        println(
            this.boards.joinToString("\n\n") {
                it.joinToString("\n") {
                    it.joinToString("\t")
                }
            }
        )
    }

    private fun isBoardWinning(board: MutableList<List<String>>): Boolean {
        return listOf(board, board.transpose()).any {
            it.any { it.all { it == "X" } }
        }
    }

    private fun hasWinningBoard(lastDraw: Int): Boolean {
        boards.forEach { board ->
            val areYouWinningSon = this.isBoardWinning(board)

            if (areYouWinningSon) {
                result = sumBoard(board) * lastDraw
                return true
            }
        }

        return false
    }
}
